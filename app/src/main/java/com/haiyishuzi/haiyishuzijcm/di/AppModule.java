package com.haiyishuzi.haiyishuzijcm.di;

import android.app.Application;

import androidx.room.Room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.haiyishuzi.haiyishuzijcm.MainConstant;
import com.haiyishuzi.haiyishuzijcm.api.HaiyishuziService;
import com.haiyishuzi.haiyishuzijcm.db.HaiyishuziDb;
import com.haiyishuzi.haiyishuzijcm.db.LoginDao;
import com.haiyishuzi.haiyishuzijcm.db.UserDao;
import com.haiyishuzi.haiyishuzijcm.liveData.LocationLiveData;
import com.haiyishuzi.haiyishuzijcm.util.LiveDataCallAdapterFactory;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {

    @Singleton
    @Provides
    HaiyishuziService provideService(Application app, ObjectMapper objectMapper) {
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(app.getBaseContext()));
        final OkHttpClient okHttpClient;
        okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//为OkHttp对象设置SocketFactory用于双向认证
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .addInterceptor(new RequestReviseInterceptor())
                .cookieJar(cookieJar)
                .connectTimeout(60, TimeUnit.SECONDS)//设置网络请求超时时间
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
       .build();
        return new Retrofit.Builder()
                .baseUrl(MainConstant.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))//可以使用自定义的转化器实现加解密
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(HaiyishuziService.class);
    }

    @Singleton
    @Provides
    HaiyishuziDb provideDb(Application app) {
        //MIGRATION_1_2随着版本的升级改变具体sql语句实现
        return Room.databaseBuilder(app, HaiyishuziDb.class, "haiyishuzi.db").fallbackToDestructiveMigration().addMigrations(HaiyishuziDb.MIGRATION_1_2).build();
    }

    @Singleton
    @Provides
    UserDao provideUserDao(HaiyishuziDb db) {
        return db.userDao();
    }

    @Singleton
    @Provides
    LoginDao provideLoginDao(HaiyishuziDb db){
        return db.loginDao();
    }

    /**
     * 唯一的json解析器
     *
     * @return
     */
    @Singleton
    @Provides
    ObjectMapper provideObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //属性为 空（“”） 或者为 NULL 都不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //忽略掉服务端返回而客户端实体中没有的数据
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    /**
     * 提供全局的map，供查询用fragment存取查询条件使用
     * @return
     */
    @Singleton
    @Provides
    Map<String,Object> provideMap(){
        return new HashMap<>();
    }

    //Singleton只是保证在一个Component中是单例
    @Singleton
    @Provides
    LocationLiveData provideLocationLiveData(Application app) {
        return LocationLiveData.get(app);
    }


    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    //忽略认证
    private static class SSLSocketClient {
        //获取这个SSLSocketFactory
        public static SSLSocketFactory getSSLSocketFactory() {
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, getTrustManager(), new SecureRandom());
                return sslContext.getSocketFactory();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //获取TrustManager
        private static TrustManager[] getTrustManager() {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };
            return trustAllCerts;
        }
    }

}
