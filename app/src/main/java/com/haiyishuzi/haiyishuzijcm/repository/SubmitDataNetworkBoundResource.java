package com.haiyishuzi.haiyishuzijcm.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.haiyishuzi.haiyishuzijcm.AppExecutors;
import com.haiyishuzi.haiyishuzijcm.api.ApiResponse;
import com.haiyishuzi.haiyishuzijcm.api.BaseResponse;
import com.haiyishuzi.haiyishuzijcm.api.ResponseCodeEnum;
import com.haiyishuzi.haiyishuzijcm.util.Utils;
import com.haiyishuzi.haiyishuzijcm.vo.Resource;


/**
 * 处理数据提交
 */
public abstract class SubmitDataNetworkBoundResource {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<Boolean>> result = new MediatorLiveData<>();

    @MainThread
    SubmitDataNetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        setValue(Resource.loading(null));
        if(shouldSubmit2Network()){
            submit2Network();
        }else{
            appExecutors.diskIO().execute(()->{
                if(shouldSubmit2Db()){
                    if(saveData(false)>0){
                        appExecutors.mainThread().execute(()->{
                            setValue(Resource.success(true));
                        });
                    }else{
                        appExecutors.mainThread().execute(()->{
                            setValue(Resource.error("数据库存储数据失败",null));
                        });
                    }
                }else{
                    appExecutors.mainThread().execute(()->{
                        setValue(Resource.error("已存在该条记录，不能重复保存！",null));
                    });
                }
            });
        }
    }

    @MainThread
    private void setValue(Resource<Boolean> newValue) {
        if (!Utils.instance().equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void submit2Network() {
        LiveData<ApiResponse<BaseResponse<Boolean>>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(apiResponse, response -> {
            //noinspection ConstantConditions
            //增加自己的判断逻辑,将自定义错误加入
            BaseResponse<Boolean> baseResponse = processResponse(response);
            if(baseResponse == null || baseResponse.getCode() != ResponseCodeEnum.SUCCESS){
                setValue(Resource.error(baseResponse==null?"网络请求失败，存入本地数据库":baseResponse.getMessage(),null));
                appExecutors.diskIO().execute(()->{
                    saveData(false);
                });
                return;
            }
            if (response.isSuccessful()) {
                setValue(Resource.success(baseResponse.getData()));
                appExecutors.diskIO().execute(()->{
                    saveData(true);
                });
            } else {
                setValue(Resource.error(response.errorMessage, null));
                //是否要存入本地？本来就是错误的数据没必要存入本地，存入了本地还是提交不到服务器
            }
        });
    }

    public LiveData<Resource<Boolean>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected BaseResponse<Boolean> processResponse(ApiResponse<BaseResponse<Boolean>> response) {
        return response.body;
    }


    //是否提交到服务器
    @MainThread
    protected abstract boolean shouldSubmit2Network();

    //离线时是否提交到数据库（主要是判断是否覆盖）
    @WorkerThread
    protected abstract boolean shouldSubmit2Db();

    //离线时插入数据库
    @WorkerThread
    protected abstract Long saveData(boolean dataSynchronized);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<BaseResponse<Boolean>>> createCall();
}
