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

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.haiyishuzi.haiyishuzijcm.AppExecutors;
import com.haiyishuzi.haiyishuzijcm.R;
import com.haiyishuzi.haiyishuzijcm.db.HaiyishuziDb;
import com.haiyishuzi.haiyishuzijcm.vo.HomeMenu;
import com.haiyishuzi.haiyishuzijcm.vo.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

/**
 * Repository that handles HomeMenu objects.
 * 直接本地组装，未从网络获取
 */
@Singleton
public class HomeRepository {

    private final HaiyishuziDb haiyishuziDb;
    private final AppExecutors appExecutors;

    @Inject
    HomeRepository(AppExecutors appExecutors, HaiyishuziDb haiyishuziDb) {
        this.haiyishuziDb = haiyishuziDb;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<HomeMenu>>> loadMenu() {
        return new DataBaseBoundResource<List<HomeMenu>, List<HomeMenu>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<HomeMenu> item) {
                for (HomeMenu homeMenu : item) {
                    haiyishuziDb.homeDao().insert(homeMenu);
                }
            }

            /**
             * 是否需要初始化数据，为的是没有数据的往数据库中插入一下数据
             *
             * @param data
             * @return
             */
            @Override
            protected boolean shouldInit(@Nullable List<HomeMenu> data) {
                return data == null || data.size() == 0;
            }


            @NonNull
            @Override
            protected LiveData<List<HomeMenu>> loadFromDb() {
                return haiyishuziDb.homeDao().findAll();
            }

            @NonNull
            @Override
            protected LiveData<List<HomeMenu>> initData() {
                //删除某功能需要对本地数据库进行操作
                List<HomeMenu> list = new ArrayList<>();
                HomeMenu pullMenu = new HomeMenu("com.haiyishuzi.haiyishuzijcm.ui.WebFragment", R.drawable.dakaqiandao, "H5下拉刷新", 0, new String[]{ACCESS_FINE_LOCATION});
                Bundle pullBundle = new Bundle();
                //测试远程h5js注入
                pullBundle.putString("LAUNCH_URL", "http://192.168.3.67:8080/bjyzd");
                pullBundle.putBoolean("NATIVE_JS",true);
                pullBundle.putString("configName","config_wpl");
                //定义在cordovaLib中的颜色，h5沉浸式原生层的解决方案，此处将statsBar设置成了该颜色。也可不设置此值，在h5页面中留出statsBar的高度实现沉浸式
                pullBundle.putInt("statsBarColor",R.color.ios_style_statsbar_color);
                pullMenu.setExtra(pullBundle);
                list.add(pullMenu);
                HomeMenu iosMenu = new HomeMenu("com.haiyishuzi.cordovayzd.JcmCordovaActivity", R.drawable.fangwuguanli, "IOS风格", 1, null);
                Bundle iosBundle = new Bundle();
                iosBundle.putString("configName","config_ios");
                //定义在cordovaLib中的颜色，h5沉浸式原生层的解决方案，此处将statsBar设置成了该颜色。也可不设置此值，在h5页面中留出statsBar的高度实现沉浸式
                iosBundle.putInt("statsBarColor",R.color.ios_style_statsbar_color);
                iosMenu.setExtra(iosBundle);
                list.add(iosMenu);
                HomeMenu androidMenu = new HomeMenu("com.haiyishuzi.haiyishuzijcm.ui.WebFragment", R.drawable.renkouguanli, "android风格", 2, null);
                Bundle androidBundle = new Bundle();
                androidBundle.putString("configName","config_android");
                //定义在cordovaLib中的颜色，h5沉浸式原生层的解决方案，此处将statsBar设置成了该颜色。也可不设置此值，在h5页面中留出statsBar的高度实现沉浸式
                androidBundle.putInt("statsBarColor",R.color.red);
                androidMenu.setExtra(androidBundle);
                list.add(androidMenu);
                HomeMenu systemMenu = new HomeMenu("com.haiyishuzi.haiyishuzijcm.ui.WebFragment", R.drawable.xiekongzhianyaosuguanli, "系统风格", 3, null);
                systemMenu.setExtra(androidBundle);
                list.add(systemMenu);
                //添加自己的测试
//                list.add(new HomeMenu("com.haiyishuzi.haiyishuzijcm.wpgl.JkttListFragment", R.drawable.wupinguanli, "物品管理", 4, null));
//                list.add(new HomeMenu("com.haiyisoft.villagepolice.ui.ssp.SspFragment", R.drawable.suishoupai, "随手拍", 5, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}));
//                list.add(new HomeMenu("com.haiyisoft.villagepolice.ui.sqmy.SqmyListFragment", R.drawable.sheqingminyiguanli, "社情民意", 6, null));
//                list.add(new HomeMenu("com.haiyisoft.villagepolice.ui.ccgl.CcglListFragment", R.drawable.caichanguanli, "财产管理", 7, null));
//                list.add(new HomeMenu("com.haiyisoft.villagepolice.ui.cxgl.CxglActivity", R.drawable.xiansuoguanli, "违法线索", 8, null));
//                list.add(new HomeMenu("com.haiyisoft.villagepolice.ui.mdjf.MdjfListFragment", R.drawable.jiufenguanli, "纠纷管理", 9, null));
//                list.add(new HomeMenu("com.haiyisoft.villagepolice.ui.qfqz.QfqzListFragment", R.drawable.ic_qunfangqunzhiguanli, "群防群治", 10, null));
//                list.add(new HomeMenu("com.haiyisoft.villagepolice.ui.wmfw.WmfwListFragment", R.drawable.ic_weiminruwuguanli, "为民服务", 11, null));

                //测试组件
                HomeMenu compontentsTestMenu = new HomeMenu("com.haiyishuzi.haiyishuzijcm.ui.examples.ExamplesFragment", R.drawable.menu_example, "测试组件", 4, new String[]{CAMERA});
                list.add(compontentsTestMenu);

                MediatorLiveData<List<HomeMenu>> result = new MediatorLiveData<>();//R.mipmap.onestand_icon
                result.setValue(list);
                return result;
            }
        }.asLiveData();

    }

    public void swip(int from, int to) {
        appExecutors.diskIO().execute(() -> {
            HomeMenu fromMenu = haiyishuziDb.homeDao().findBySort(from);
            fromMenu.setSort(to);
            haiyishuziDb.homeDao().insert(fromMenu);
            if (from > to) {
                //从后往前移动
                //前面的依次往后移一位
                for (int i = to; i < from; i++) {
                    HomeMenu menu = haiyishuziDb.homeDao().findBySort(i);
                    menu.setSort(i + 1);
                    haiyishuziDb.homeDao().insert(menu);
                }
            } else {
                //从前往后移动
                //后面的依次往前移一位
                for (int i = from + 1; i <= to; i++) {
                    HomeMenu menu = haiyishuziDb.homeDao().findBySort(i);
                    menu.setSort(i - 1);
                    haiyishuziDb.homeDao().insert(menu);
                }
            }

        });
    }


}
