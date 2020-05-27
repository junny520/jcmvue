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

package com.haiyishuzi.haiyishuzijcm.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.haiyishuzi.haiyishuzijcm.vo.HomeMenu;
import com.haiyishuzi.haiyishuzijcm.vo.Login;
import com.haiyishuzi.haiyishuzijcm.vo.User;


/**
 * Main database description.
 */
@Database(entities = {Login.class,User.class, HomeMenu.class}, version = 1)

@TypeConverters({HaiyishuziTypeConverters.class})
public abstract class HaiyishuziDb extends RoomDatabase {

    abstract public LoginDao loginDao();
    abstract public UserDao userDao();
    abstract public HomeDao homeDao();

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            //在此书写数据库版本升级的语句
        }
    };
}
