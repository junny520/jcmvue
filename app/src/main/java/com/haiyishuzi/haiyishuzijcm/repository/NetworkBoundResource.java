package com.haiyishuzi.haiyishuzijcm.repository;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * 判断是否从网络还是数据库获取数据，如果从网络获取数据，成功之后保存到数据库
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {
      private final AppExecutors appExecutors;

      private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

      @MainThread
      NetworkBoundResource(AppExecutors appExecutors) {
            this.appExecutors = appExecutors;
            result.setValue(Resource.loading(null));
            LiveData<ResultType> dbSource = loadFromDb();
            result.addSource(dbSource, data -> {
                  result.removeSource(dbSource);
                  if (shouldFetch(data)) {
                        fetchFromNetwork(dbSource);
                  } else {
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

      private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
            LiveData<ApiResponse<RequestType>> apiResponse = createCall();
            // we re-attach dbSource as a new source, it will dispatch its latest value quickly
            result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
            result.addSource(apiResponse, response -> {
                  result.removeSource(dbSource);
                  //noinspection ConstantConditions
                  //增加自己的判断逻辑,将自定义错误加入
                  BaseResponse baseResponse = (BaseResponse) response.body;
                  if (baseResponse == null || baseResponse.getCode() != ResponseCodeEnum.SUCCESS) {
                        onFetchFailed();
                        result.addSource(dbSource,
                                  newData -> setValue(Resource.error(baseResponse == null ? "网络请求失败" : baseResponse.getMessage(), newData)));
                        return;
                  }
                  if (response.isSuccessful()) {
                        appExecutors.diskIO().execute(() -> {
                              saveCallResult(processResponse(response));
                              appExecutors.mainThread().execute(() ->
                                      // we specially request a new live data,
                                      // otherwise we will get immediately last cached value,
                                      // which may not be updated with latest results received from network.
                                      result.addSource(loadFromDb(),
                                              newData -> setValue(Resource.success(newData)))
                              );
                        });
                  } else {
                        onFetchFailed();
                        result.addSource(dbSource,
                                  newData -> setValue(Resource.error(response.errorMessage, newData)));
                  }
            });
      }

      protected void onFetchFailed() {
      }

      public LiveData<Resource<ResultType>> asLiveData() {
            return result;
      }

      @WorkerThread
      protected RequestType processResponse(ApiResponse<RequestType> response) {
            return response.body;
      }

      // 当要把网络数据存储到数据库中时调用
      @WorkerThread
      protected abstract void saveCallResult(@NonNull RequestType item);

      // 决定是否去网络获取数据
      @MainThread
      protected abstract boolean shouldFetch(@Nullable ResultType data);

      // 用于从数据库中获取缓存数据
      @NonNull
      @MainThread
      protected abstract LiveData<ResultType> loadFromDb();

      // 创建网络数据请求
      @NonNull
      @MainThread
      protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
