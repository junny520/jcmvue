/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haiyishuzi.haiyishuzijcm.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.haiyishuzi.haiyishuzijcm.AppExecutors;
import com.haiyishuzi.haiyishuzijcm.api.ApiResponse;
import com.haiyishuzi.haiyishuzijcm.api.BaseResponse;
import com.haiyishuzi.haiyishuzijcm.api.HaiyishuziService;
import com.haiyishuzi.haiyishuzijcm.db.LoginDao;
import com.haiyishuzi.haiyishuzijcm.db.UserDao;
import com.haiyishuzi.haiyishuzijcm.vo.Login;
import com.haiyishuzi.haiyishuzijcm.vo.Resource;
import com.haiyishuzi.haiyishuzijcm.vo.User;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that handles User objects.
 */
@Singleton
public class UserRepository {

    private final UserDao userDao;
    private final HaiyishuziService haiyishuziService;
    private final AppExecutors appExecutors;
    private final LoginDao loginDao;

    @Inject
    UserRepository(AppExecutors appExecutors, UserDao userDao, LoginDao loginDao, HaiyishuziService haiyishuziService) {
        this.userDao = userDao;
        this.haiyishuziService = haiyishuziService;
        this.appExecutors = appExecutors;
        this.loginDao = loginDao;
    }

    public LiveData<Resource<User>> loadUser(Login login) {
        return new NetworkBoundResource<User, BaseResponse<User>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull BaseResponse<User> item) {

                //更新上一次登录用户为离线状态
                User onlineAc = userDao.findByLOnline();
                if(null != onlineAc){
                    onlineAc.setOnline(false);
                    userDao.insert(onlineAc);
                }
                item.getData().setOnline(true);
                userDao.insert(item.getData());
                //更新Login
                login.setLastLoginTime(System.currentTimeMillis());
                if (!login.isAutoLogin() && !login.getRememberPassword()){
                    login.setPassword("");
                }
                loginDao.insert(login);
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.findByLogin(login.getLoginCode());
            }

            protected void onFetchFailed() {
                appExecutors.diskIO().execute(()->{
                    //更新上一次登录用户为离线状态
                    User onlineAc = userDao.findByLOnline();
                    if(null != onlineAc){
                        onlineAc.setOnline(false);
                        userDao.insert(onlineAc);
                    }
                    //查询出当前用户
                    User user = userDao.findByLoginCode(login.getLoginCode());
                    //将当前用户置换成在线状态
                    if(user != null){
                        user.setOnline(true);
                        userDao.update(user);
                    }
                });
            }
            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<User>>> createCall() {
                return haiyishuziService.getUser(login.getLoginCode(), login.getPassword());
            }
        }.asLiveData();

    }

    public LiveData<Resource<User>> searchOnlieAccount() {
        return new NetworkBoundResource<User, BaseResponse<User>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull BaseResponse<User> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.findByOnline();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<User>>> createCall() {
                return null;
            }
        }.asLiveData();
    }

    public LiveData<Resource<User>> checkUserPassword(String password) {
        return new NetworkBoundResource<User, BaseResponse<User>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull BaseResponse<User> item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return false;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return userDao.findByOnlineAndPassword(password);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<User>>> createCall() {
                return null;
            }
        }.asLiveData();
    }

    public LiveData<Resource<Boolean>> updateAccount(String oldPassword, User user) {
        return new SubmitDataNetworkBoundResource(appExecutors){

            @Override
            protected boolean shouldSubmit2Network() {
                return true;
            }

            @Override
            protected boolean shouldSubmit2Db() {
                return true;
            }

            @Override
            protected Long saveData(boolean dataSynchronized) {
                //user.setPassword(HMAC_SHA1.encode(user.getPassword()));

                return userDao.insert(user);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BaseResponse<Boolean>>> createCall() {
                return haiyishuziService.resetPassword(oldPassword, user.getPassword());
            }
        }.asLiveData();
    }

    public User getData() {
        return userDao.findByLOnline();
    }

    public LiveData<Boolean> switchUser(){
        LiveData<Boolean> resut = new MutableLiveData<>();
        appExecutors.diskIO().execute(()->{
            Login login = userDao.findLoginByOnlineIo();
            login.setAutoLogin(false);
            login.setPassword("");
            login.setRememberPassword(false);
            //修改login的记住密码和自动登录
            loginDao.insert(login);
            appExecutors.mainThread().execute(()->{
                ((MutableLiveData<Boolean>) resut).setValue(true);
            });
        });
        return resut;
    }

}
