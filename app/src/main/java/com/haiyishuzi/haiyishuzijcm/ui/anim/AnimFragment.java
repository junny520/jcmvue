package com.haiyishuzi.haiyishuzijcm.ui.anim;

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

import com.haiyishuzi.haiyishuzijcm.R;
import com.haiyishuzi.haiyishuzijcm.binding.FragmentDataBindingComponent;
import com.haiyishuzi.haiyishuzijcm.callBack.UserFragmentLabel;
import com.haiyishuzi.haiyishuzijcm.databinding.AnimFragmentBinding;
import com.haiyishuzi.haiyishuzijcm.di.Injectable;
import com.haiyishuzi.haiyishuzijcm.ui.common.NavigationController;
import com.haiyishuzi.haiyishuzijcm.util.AutoClearedValue;
import com.haiyishuzi.haiyishuzijcm.vo.HeadStates;
import com.haiyishuzi.haiyishuzijcm.vo.TopLayoutInfo;

import javax.inject.Inject;

public class AnimFragment extends Fragment implements Injectable, UserFragmentLabel {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NavigationController navigationController;
    @VisibleForTesting
    AutoClearedValue<AnimFragmentBinding> binding;

    private AnimViewModel mViewModel;

    public static AnimFragment newInstance() {
        return new AnimFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        AnimFragmentBinding animFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.anim_fragment, container, false,new FragmentDataBindingComponent(this));
        binding = new AutoClearedValue<AnimFragmentBinding>(this,animFragmentBinding);
        return animFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //设置头部
        HeadStates headStates = new HeadStates(0, "动画", 0,0, 0);
        binding.get().setHeadStates(headStates);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(AnimViewModel.class);
        // TODO: Use the ViewModel


    }

}
