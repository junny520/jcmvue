package com.haiyishuzi.haiyishuzijcm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.haiyishuzi.haiyishuzijcm.vo.HomeMenu;

import java.util.List;

/**
 * Created by wpl on 2017/11/2.
 * 主页
 */
@Dao
public interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HomeMenu homeMenu);

    @Query("SELECT * FROM HomeMenu order by sort")
    LiveData<List<HomeMenu>> findAll();

    @Query("SELECT * FROM HomeMenu WHERE sort=:sort")
    HomeMenu findBySort(int sort);

    @Update
    void update(HomeMenu homeMenu);
}
