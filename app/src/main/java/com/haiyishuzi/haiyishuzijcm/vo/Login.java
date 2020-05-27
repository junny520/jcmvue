package com.haiyishuzi.haiyishuzijcm.vo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;

import com.haiyishuzi.haiyishuzijcm.util.Utils;

import java.io.Serializable;

/**
 * @author wpl on 2018/7/24.
 * 为登录页做缓存使用，服务端不存在
 */
@Entity(primaryKeys = "loginCode")
public class Login  implements Serializable {
    @NonNull
    private String loginCode;
    @Nullable
    private String password;
//    记住密码
    private Boolean rememberPassword = false;
//    自动登录
    private boolean autoLogin = false;
//    最后一次登录时间
    @NonNull
    private long lastLoginTime;

    @NonNull
    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(@NonNull String loginCode) {
        this.loginCode = loginCode;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    public Boolean getRememberPassword() {
        return rememberPassword;
    }

    public void setRememberPassword(Boolean rememberPassword) {
        this.rememberPassword = rememberPassword;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    @NonNull
    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(@NonNull long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Login))
            return false;
        Login login = (Login) obj;
        return Utils.instance().equals(loginCode,login.getLoginCode()) && Utils.instance().equals(password,login.getPassword());
    }
}
