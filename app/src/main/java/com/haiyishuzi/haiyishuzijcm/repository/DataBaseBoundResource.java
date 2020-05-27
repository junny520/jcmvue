package com.haiyishuzi.haiyishuzijcm.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.haiyishuzi.haiyishuzijcm.AppExecutors;
import com.haiyishuzi.haiyishuzijcm.util.Utils;
import com.haiyishuzi.haiyishuzijcm.vo.Resource;


/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * 单纯从数据库中获取数据
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class DataBaseBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    DataBaseBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource,data ->{
            result.removeSource(dbSource);
            if(shouldInit(data)){
                fetchFromInit(dbSource);
            }else{
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Utils.instance().equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromInit(final LiveData<ResultType> dbSource) {
        LiveData<RequestType> data = initData();
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(data, response -> {
            result.removeSource(data);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            appExecutors.diskIO().execute(() -> {
                saveCallResult(response);
                appExecutors.mainThread().execute(() ->
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb(),
                                newData -> setValue(Resource.success(newData)))
                );
            });
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    /**
     * 是否需要初始化数据，为的是没有数据的往数据库中插入一下数据
     * @return
     */
    @MainThread
    protected abstract boolean shouldInit(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<RequestType> initData();

}
