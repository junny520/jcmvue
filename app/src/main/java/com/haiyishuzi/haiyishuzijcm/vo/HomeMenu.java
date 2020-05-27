package com.haiyishuzi.haiyishuzijcm.vo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.haiyishuzi.haiyishuzijcm.util.Utils;

/**
 * Created by wpl on 2017/11/2.
 * 首页上的grid菜单项
 */

@Entity(primaryKeys = "title")
public class HomeMenu {

    @NonNull
    private String className;
    @NonNull
    private int iconId;
    @NonNull
    private String title;
    @NonNull
    private int sort;
    //打开该模块所需要的权限
    public String[] permission;
    //拓展
    private Bundle extra;

    public HomeMenu(){}
    @Ignore
    public HomeMenu(@NonNull String className, @NonNull int iconId, @NonNull String title, @NonNull int sort, String[] permission) {
        this.className = className;
        this.iconId = iconId;
        this.title = title;
        this.sort = sort;
        this.permission = permission;
    }

    @NonNull
    public String getClassName() {
        return className;
    }

    public void setClassName(@NonNull String className) {
        this.className = className;
    }

    @NonNull
    public int getIconId() {
        return iconId;
    }

    public void setIconId(@NonNull int iconId) {
        this.iconId = iconId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public int getSort() {
        return sort;
    }

    public void setSort(@NonNull int sort) {
        this.sort = sort;
    }

    public String[] getPermission() {
        return permission;
    }

    public void setPermission(String[] permission) {
        this.permission = permission;
    }

    public Bundle getExtra() {
        return extra;
    }

    public void setExtra(Bundle extra) {
        this.extra = extra;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof HomeMenu))
            return false;
        HomeMenu homeMenu = (HomeMenu) obj;
        return Utils.instance().equals(className,homeMenu.className) && Utils.instance().equals(iconId,homeMenu.iconId)
                && Utils.instance().equals(title,homeMenu.title) && Utils.instance().equals(sort,homeMenu.sort);
    }
}
