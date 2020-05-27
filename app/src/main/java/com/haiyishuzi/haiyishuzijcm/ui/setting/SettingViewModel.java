package com.haiyishuzi.haiyishuzijcm.ui.setting;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.haiyishuzi.haiyishuzijcm.repository.UserRepository;
import com.haiyishuzi.haiyishuzijcm.util.AbsentLiveData;
import com.haiyishuzi.haiyishuzijcm.util.Utils;

import javax.inject.Inject;

public class SettingViewModel extends ViewModel {

    @VisibleForTesting
    final MutableLiveData<Long> quitTime = new MutableLiveData<>();
    private final LiveData<Boolean> result;

    @VisibleForTesting
    final MutableLiveData<Long> switchTime = new MutableLiveData<>();
    private final LiveData<Boolean> switchResult;

    @Inject
    public SettingViewModel(UserRepository userRepository){
        result = Transformations.switchMap(quitTime, quitTime -> {
            if (quitTime == null) {
                return AbsentLiveData.create();
            } else {
                return userRepository.switchUser();
            }
        });

        switchResult = Transformations.switchMap(switchTime, switchTime -> {
            if (switchTime == null) {
                return AbsentLiveData.create();
            } else {
                return userRepository.switchUser();
            }
        });
    }
    @VisibleForTesting
    public void setQuitTime(Long quitTime) {
        if (Utils.instance().equals(this.quitTime.getValue(), quitTime)) {
            return;
        }
        this.quitTime.setValue(quitTime);
    }

    @VisibleForTesting
    public void setSwitchTime(Long switchTime) {
        if (Utils.instance().equals(this.switchTime.getValue(), switchTime)) {
            return;
        }
        this.switchTime.setValue(switchTime);
    }

    public LiveData<Boolean> getResult() {
        return result;
    }

    public LiveData<Boolean> getSwitchResult() {
        return switchResult;
    }
}
