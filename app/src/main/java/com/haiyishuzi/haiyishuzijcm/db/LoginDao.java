package com.haiyishuzi.haiyishuzijcm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.haiyishuzi.haiyishuzijcm.vo.Login;


/**
 * Interface for database access for Login related operations.
 */
@Dao
public interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Login userLogin);

    @Query("SELECT * FROM Login WHERE loginCode = :loginCode")
    LiveData<Login> findByLoginCode(String loginCode);
}
