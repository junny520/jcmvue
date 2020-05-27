package com.haiyishuzi.haiyishuzijcm.ui.load;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.haiyishuzi.haiyishuzijcm.repository.LoginRepository;
import com.haiyishuzi.haiyishuzijcm.util.AbsentLiveData;
import com.haiyishuzi.haiyishuzijcm.util.Utils;
import com.haiyishuzi.haiyishuzijcm.vo.Login;

import javax.inject.Inject;

public class LoadViewModel extends ViewModel {
    @VisibleForTesting
    final MutableLiveData<Long> queryTime = new MutableLiveData<>();
    private final LiveData<Login> login;
    @Inject
    public LoadViewModel(LoginRepository loginRepository) {
        login = Transformations.switchMap(queryTime, login -> {
            if (login == null) {
                return AbsentLiveData.create();
            } else {
                return loginRepository.getLastLogin();
            }
        });
    }

    @VisibleForTesting
    public void setQueryTime(Long queryTime) {
        if (Utils.instance().equals(this.queryTime.getValue(), queryTime)) {
            return;
        }
        this.queryTime.setValue(queryTime);
    }

    public LiveData<Login> getLogin() {
        return login;
    }
}
