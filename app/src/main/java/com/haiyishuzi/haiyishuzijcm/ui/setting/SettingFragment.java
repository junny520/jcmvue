package com.haiyishuzi.haiyishuzijcm.ui.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.haiyishuzi.haiyishuzijcm.R;
import com.haiyishuzi.haiyishuzijcm.binding.FragmentDataBindingComponent;
import com.haiyishuzi.haiyishuzijcm.callBack.UserFragmentLabel;
import com.haiyishuzi.haiyishuzijcm.databinding.SettingFragmentBinding;
import com.haiyishuzi.haiyishuzijcm.di.Injectable;
import com.haiyishuzi.haiyishuzijcm.ui.common.NavigationController;
import com.haiyishuzi.haiyishuzijcm.util.AutoClearedValue;
import com.haiyishuzi.haiyishuzijcm.vo.HeadStates;
import com.haiyishuzi.haiyishuzijcm.vo.TopLayoutInfo;

import javax.inject.Inject;

public class SettingFragment extends Fragment implements Injectable, UserFragmentLabel {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NavigationController navigationController;
    @VisibleForTesting
    AutoClearedValue<SettingFragmentBinding> binding;
    private SettingViewModel mViewModel;
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        SettingFragmentBinding settingFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.setting_fragment, container, false,new FragmentDataBindingComponent(this));
        binding = new AutoClearedValue<SettingFragmentBinding>(this,settingFragmentBinding);
        return settingFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(SettingViewModel.class);
        //设置头部
        HeadStates headStates = new HeadStates(0, "个人设置", 0,0, 0);
        binding.get().setHeadStates(headStates);
        binding.get().editPasswordCv.setOnClickListener(v->{
            Snackbar.make(binding.get().editPasswordCv,"...",Snackbar.LENGTH_SHORT).show();
        });
        mViewModel.getResult().observe(this,result->{
            if(result){
                getActivity().finish();
            }else{
                Snackbar.make(binding.get().quitCv,"退出应用失败",Snackbar.LENGTH_SHORT).show();
            }
        });
        mViewModel.getSwitchResult().observe(this,result->{
            if(result){
                navigationController.navigateToLogin(null);
            }else{
                Snackbar.make(binding.get().switchUserCv,"切换用户失败",Snackbar.LENGTH_SHORT).show();
            }
        });
        binding.get().switchUserCv.setOnClickListener(v->{
            mViewModel.setSwitchTime(System.currentTimeMillis());
        });
        binding.get().quitCv.setOnClickListener(v->{
            mViewModel.setQuitTime(System.currentTimeMillis());
        });
    }

}
