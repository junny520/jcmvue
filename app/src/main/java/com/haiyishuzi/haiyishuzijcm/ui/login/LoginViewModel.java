package com.haiyishuzi.haiyishuzijcm.ui.login;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.haiyishuzi.haiyishuzijcm.repository.UserRepository;
import com.haiyishuzi.haiyishuzijcm.util.AbsentLiveData;
import com.haiyishuzi.haiyishuzijcm.util.Utils;
import com.haiyishuzi.haiyishuzijcm.vo.Login;
import com.haiyishuzi.haiyishuzijcm.vo.Resource;
import com.haiyishuzi.haiyishuzijcm.vo.User;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    @VisibleForTesting
    final MutableLiveData<Login> login = new MutableLiveData<>();
    private final LiveData<Resource<User>> user;

    @Inject
    public LoginViewModel(UserRepository userRepository){
        user = Transformations.switchMap(login, login -> {
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return userRepository.loadUser(login);
            }
        });
    }

    @VisibleForTesting
    public void setLogin(Login login) {
        if (Utils.instance().equals(this.login.getValue(), login)) {
            return;
        }
        this.login.setValue(login);
    }

    @VisibleForTesting
    public void retry() {
        if (this.login.getValue() != null) {
            this.login.setValue(this.login.getValue());
        }
    }

    public LiveData<Resource<User>> getUser() {
        return user;
    }
}
