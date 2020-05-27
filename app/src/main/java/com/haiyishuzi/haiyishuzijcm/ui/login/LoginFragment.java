package com.haiyishuzi.haiyishuzijcm.ui.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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
import com.haiyishuzi.haiyishuzijcm.callBack.TransparentBarAble;
import com.haiyishuzi.haiyishuzijcm.databinding.LoginFragmentBinding;
import com.haiyishuzi.haiyishuzijcm.di.Injectable;
import com.haiyishuzi.haiyishuzijcm.ui.common.NavigationController;
import com.haiyishuzi.haiyishuzijcm.util.AutoClearedValue;
import com.haiyishuzi.haiyishuzijcm.vo.Login;
import com.haiyishuzi.haiyishuzijcm.vo.Status;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import timber.log.Timber;

public class LoginFragment extends Fragment implements Injectable, TransparentBarAble {

    private static final String LOGIN = "LOGIN";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    NavigationController navigationController;
    @VisibleForTesting
    AutoClearedValue<LoginFragmentBinding> binding;
    private LoginViewModel mViewModel;

    public static LoginFragment newInstance(Login login) {
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        if (null == login){
            login = new Login();
        }
        bundle.putSerializable(LOGIN,login);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LoginFragmentBinding loginFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        binding = new AutoClearedValue<LoginFragmentBinding>(this,loginFragmentBinding);
        return loginFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,viewModelFactory).get(LoginViewModel.class);
        Login login = (Login) getArguments().getSerializable(LOGIN);
        binding.get().setLogin(login);
        mViewModel.getUser().observe(this,userResource->{
//不支持离线登录，所以将data清空
            userResource.setData(null);
            binding.get().setLoginResource(userResource);
            if (Status.ERROR == userResource.getStatus()) {
                Snackbar.make(binding.get().loginBtn, userResource.getMessage(), Snackbar.LENGTH_LONG).show();
            }
            //跳转
            if (Status.SUCCESS == userResource.getStatus())
                navigationController.navigateToIndex();
        });
        if (null != login && login.isAutoLogin()){
            mViewModel.setLogin(login);
        }
        binding.get().ivPasswordVisible.setOnCheckedChangeListener((buttonView,isChecked) ->{
            if (isChecked){
                binding.get().userpassEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                binding.get().userpassEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        binding.get().forgetpass.setOnClickListener(v -> {
            Timber.e("wpl","忘记密码");
            connect();
        });
        binding.get().userregister.setOnClickListener(v -> {
            Timber.e("wpl","用户注册");
        });
        binding.get().loginBtn.setOnClickListener(v -> {
            mViewModel.setLogin(login);
        });
        binding.get().setRetryCallback(()->mViewModel.retry());
    }

    private void connect() {

        EchoWebSocketListener listener = new EchoWebSocketListener();
        Request request = new Request.Builder()
                .url("ws://192.168.3.238:9090/haiyishuziPush/webSocket")
                .build();
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(9 * 10, TimeUnit.SECONDS).build();
        client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    private final class EchoWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {

            webSocket.send("hello world");
            webSocket.send("welcome");
            webSocket.send(ByteString.decodeHex("adef"));
            webSocket.close(1000, "再见");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            Timber.e("onMessage: " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Timber.e("onMessage byteString: " + bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            Timber.e("onClosing: " + code + "/" + reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            Timber.e("onClosed: " + code + "/" + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Timber.e("onFailure: " + t.getMessage());
        }
    }


}
