package com.haiyishuzi.haiyishuzijcm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.haiyishuzi.haiyishuzijcm.vo.Login;
import com.haiyishuzi.haiyishuzijcm.vo.User;


/**
 * Interface for database access for User related operations.
 */
@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(User userLogin);

    @Query("SELECT * FROM User WHERE logincode = :logincode")
    LiveData<User> findByLogin(String logincode);

    @Query("SELECT * FROM User WHERE online = '1'")
    LiveData<User> findByOnline();

    @Query("SELECT * FROM User WHERE online = '1'")
    User findByLOnline();

    @Query("SELECT * FROM User WHERE loginCode = :loginCode")
    User findByLoginCode(String loginCode);

    @Update
    void update(User user);

    @Query("SELECT * FROM User WHERE online = '1' and password = :password")
    LiveData<User> findByOnlineAndPassword(String password);

    @Query("SELECT * FROM Login WHERE loginCode = (SELECT loginCode FROM User WHERE online = '1')")
    LiveData<Login> findLoginByOnline();

    @Query("SELECT * FROM Login WHERE loginCode = (SELECT loginCode FROM User WHERE online = '1')")
    Login findLoginByOnlineIo();
}
