package com.haiyishuzi.haiyishuzijcm.di;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * header拦截
 * @author wpl on 2019-05-29.
 */
public class RequestReviseInterceptor implements Interceptor {

    //没操作
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }

//    private TokenInfo tokenInfo;
//
//    public RequestReviseInterceptor(TokenInfo tokenInfo){
//        this.tokenInfo = tokenInfo;
//    }
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request originalRequest = chain.request();
//        //先更改base_url
//        Request.Builder newBuilder = originalRequest.newBuilder();
//        String headerBaseUrl = originalRequest.header("Domain-Name");
//        //此处后期扩展可以根据headerBaseUrl的值作为键获取值作为baseurl
//        if(!TextUtils.isEmpty(headerBaseUrl) && tokenInfo.getPrefixUrls() != null && tokenInfo.getPrefixUrls().size()>0){
//            HttpUrl domainUrl = HttpUrl.parse(tokenInfo.getPrefixUrls().get(0));
////            HttpUrl domainUrl = HttpUrl.parse("https://npcapi.yzdhxp.com");
//            HttpUrl httpUrl = originalRequest.url().newBuilder().scheme(domainUrl.scheme())
//                    .host(domainUrl.host())
//                    .port(domainUrl.port())
//                    .build();
//            newBuilder.url(httpUrl);
//        }
//        if(!TextUtils.isEmpty(tokenInfo.getUserToken())){
//            long timeMillis = System.currentTimeMillis();
//            String string = UUID.randomUUID().toString();
//            String before=timeMillis+string;
//            String hmac = HMAC_SHA1.genHMAC(before, tokenInfo.getTokenSign());//HAMC_SHA1 加密  基于base64
//            newBuilder
//                    .addHeader("npc-token", tokenInfo.getUserToken())
//                    .addHeader("npc-signature",hmac)
//                    .addHeader("npc-noncestr",string)
//                    .addHeader("npc-timestamp",timeMillis+"")
//                    .build();
//        }
//        return chain.proceed(newBuilder.build());
//    }
}
