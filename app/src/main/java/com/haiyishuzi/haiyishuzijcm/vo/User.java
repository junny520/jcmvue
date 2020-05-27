package com.haiyishuzi.haiyishuzijcm.vo;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import java.io.Serializable;

@Entity(primaryKeys = "loginCode")
public class User implements Serializable {
      private Integer userId;
      @NonNull
      private String loginCode;

      private String userName;

      private String password;

      private Byte useFlag;
      //是否在线，判断数据库中的当前用户
      private boolean online;

      public Integer getUserId() {
            return userId;
      }

      public void setUserId(Integer userId) {
            this.userId = userId;
      }

      public String getLoginCode() {
            return loginCode;
      }

      public void setLoginCode(@NonNull String loginCode) {
            this.loginCode = loginCode;
      }

      public String getUserName() {
            return userName;
      }

      public void setUserName(String userName) {
            this.userName = userName;
      }

      public String getPassword() {
            return password;
      }

      public void setPassword(String password) {
            this.password = password;
      }

      public Byte getUseFlag() {
            return useFlag;
      }

      public void setUseFlag(Byte useFlag) {
            this.useFlag = useFlag;
      }

      public boolean isOnline() {
            return online;
      }

      public void setOnline(boolean online) {
            this.online = online;
      }
}