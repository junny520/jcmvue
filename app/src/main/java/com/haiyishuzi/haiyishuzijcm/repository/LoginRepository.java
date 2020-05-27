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

import androidx.lifecycle.LiveData;

import com.haiyishuzi.haiyishuzijcm.AppExecutors;
import com.haiyishuzi.haiyishuzijcm.api.HaiyishuziService;
import com.haiyishuzi.haiyishuzijcm.db.UserDao;
import com.haiyishuzi.haiyishuzijcm.vo.Login;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Repository that handles User objects.
 */
@Singleton
public class LoginRepository {

    private final UserDao userDao;
    private final HaiyishuziService haiyishuziService;
    private final AppExecutors appExecutors;

    @Inject
    LoginRepository(AppExecutors appExecutors, UserDao userDao, HaiyishuziService haiyishuziService) {
        this.userDao = userDao;
        this.haiyishuziService = haiyishuziService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Login> getLastLogin() {
        return userDao.findLoginByOnline();
    }
}
