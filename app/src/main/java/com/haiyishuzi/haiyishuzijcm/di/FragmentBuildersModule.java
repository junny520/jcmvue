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

package com.haiyishuzi.haiyishuzijcm.di;

import com.haiyishuzi.haiyishuzijcm.ui.WebFragment;
import com.haiyishuzi.haiyishuzijcm.ui.WebFragment4service;
import com.haiyishuzi.haiyishuzijcm.ui.anim.AnimFragment;
import com.haiyishuzi.haiyishuzijcm.ui.forms.FormsFragment;
import com.haiyishuzi.haiyishuzijcm.ui.home.HomeFragment;
import com.haiyishuzi.haiyishuzijcm.ui.index.IndexFragment;
import com.haiyishuzi.haiyishuzijcm.ui.load.LoadFragment;
import com.haiyishuzi.haiyishuzijcm.ui.login.LoginFragment;
import com.haiyishuzi.haiyishuzijcm.ui.setting.SettingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {


    @ContributesAndroidInjector
    abstract LoadFragment contributeLoadFragment();

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract IndexFragment contributeIndexFragment();

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract AnimFragment contributeAnimFragment();

    @ContributesAndroidInjector
    abstract FormsFragment contributeFormsFragment();
    //
//    @ContributesAndroidInjector
//    abstract SelectDialogFragment contributeSelectDialogFragment();
//
    @ContributesAndroidInjector
    abstract SettingFragment contributeSettingFragment();
//
//    @ContributesAndroidInjector
//    abstract SelectDateDialogFragment contributeSelectDateDialogFragment();


    @ContributesAndroidInjector
    abstract WebFragment contributeWebFragment();

    @ContributesAndroidInjector
    abstract WebFragment4service contributeWebFragment4service();


}
