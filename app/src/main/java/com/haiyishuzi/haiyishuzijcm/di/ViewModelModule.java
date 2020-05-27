package com.haiyishuzi.haiyishuzijcm.di;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.haiyishuzi.haiyishuzijcm.ui.anim.AnimViewModel;
import com.haiyishuzi.haiyishuzijcm.ui.forms.FormsViewModel;
import com.haiyishuzi.haiyishuzijcm.ui.home.HomeViewModel;
import com.haiyishuzi.haiyishuzijcm.ui.index.IndexViewModel;
import com.haiyishuzi.haiyishuzijcm.ui.load.LoadViewModel;
import com.haiyishuzi.haiyishuzijcm.ui.login.LoginViewModel;
import com.haiyishuzi.haiyishuzijcm.ui.setting.SettingViewModel;
import com.haiyishuzi.haiyishuzijcm.viewmodel.HaiyishuziViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(HaiyishuziViewModelFactory factory);


    @Binds
    @IntoMap
    @ViewModelKey(LoadViewModel.class)
    abstract ViewModel bindLoadViewModel(LoadViewModel loadViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(IndexViewModel.class)
    abstract ViewModel bindIndexViewModel(IndexViewModel indexViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AnimViewModel.class)
    abstract ViewModel bindAnimViewModel(AnimViewModel animViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(FormsViewModel.class)
    abstract ViewModel bindFormsViewModel(FormsViewModel formsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel.class)
    abstract ViewModel bindSettingViewModel(SettingViewModel settingViewModel);

}
