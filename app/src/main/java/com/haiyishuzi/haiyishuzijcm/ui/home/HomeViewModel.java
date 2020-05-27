package com.haiyishuzi.haiyishuzijcm.ui.home;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.haiyishuzi.haiyishuzijcm.repository.HomeRepository;
import com.haiyishuzi.haiyishuzijcm.util.AbsentLiveData;
import com.haiyishuzi.haiyishuzijcm.vo.HomeMenu;
import com.haiyishuzi.haiyishuzijcm.vo.Resource;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> query = new MutableLiveData<>();
    private final LiveData<Resource<List<HomeMenu>>> results;
    private HomeRepository homeRepository;

    @Inject
    public HomeViewModel(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
        results = Transformations.switchMap(query, load -> {
            if (load.isEmpty()) {
                return AbsentLiveData.create();
            }
            return homeRepository.loadMenu();
        });
    }

    @VisibleForTesting
    public LiveData<Resource<List<HomeMenu>>> getResults() {
        return results;
    }

    public void load() {
        query.setValue("load");
    }

    public void swip(int from, int to) {
        homeRepository.swip(from, to);
    }
}
