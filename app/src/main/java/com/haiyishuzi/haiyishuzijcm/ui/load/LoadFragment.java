package com.haiyishuzi.haiyishuzijcm.ui.load;

import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.haiyishuzi.haiyishuzijcm.callBack.TransparentBarAble;
import com.haiyishuzi.haiyishuzijcm.databinding.LoadFragmentBinding;
import com.haiyishuzi.haiyishuzijcm.di.Injectable;
import com.haiyishuzi.haiyishuzijcm.ui.common.NavigationController;
import com.haiyishuzi.haiyishuzijcm.util.AutoClearedValue;
import com.haiyishuzi.haiyishuzijcm.vo.HomeMenu;
import com.haiyishuzi.haiyishuzijcm.vo.Login;

import javax.inject.Inject;

public class LoadFragment extends Fragment implements Injectable, TransparentBarAble {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NavigationController navigationController;
    @VisibleForTesting
    AutoClearedValue<LoadFragmentBinding> binding;
    private LoadViewModel mViewModel;
    androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private MyTimer timer;
    private Login login;

    public static LoadFragment newInstance() {
        return new LoadFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LoadFragmentBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.load_fragment,
                container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(LoadViewModel.class);
        mViewModel.getLogin().observe(this,login1 -> {
            login = login1;
        });
        mViewModel.setQueryTime(System.currentTimeMillis());
        timer = new MyTimer(5000, 1000);
        timer.start();
    }

//模拟耗时操作，在实际应用中初始化加载耗时动作如ocr初始化等
    class MyTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * Callback fired on regular interval.
         *
         * @param millisUntilFinished The amount of time until finished.
         */
        @Override
        public void onTick(long millisUntilFinished) {
            if (millisUntilFinished / 1000 == 1L) {
                if (timer != null) {
                    timer.cancel();
                }
//                navigationController.navigateToLogin(login);
//                navigationController.navigateToIndex();


                HomeMenu androidMenu = new HomeMenu("com.haiyishuzi.haiyishuzijcm.ui.WebFragment", R.drawable.renkouguanli, "android风格", 2, null);
                Bundle androidBundle = new Bundle();
                androidBundle.putString("configName","config_jingzong");
                //定义在cordovaLib中的颜色，h5沉浸式原生层的解决方案，此处将statsBar设置成了该颜色。也可不设置此值，在h5页面中留出statsBar的高度实现沉浸式
                androidBundle.putInt("statsBarColor",R.color.red);
                androidMenu.setExtra(androidBundle);
                navigationController.navigateToMenu(androidMenu);//TODO
            }
        }

        /**
         * Callback fired when the time is up.
         */
        @Override
        public void onFinish() {

        }
    }

}
