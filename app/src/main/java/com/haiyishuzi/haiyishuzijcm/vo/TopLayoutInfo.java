package com.haiyishuzi.haiyishuzijcm.vo;

/**
 * @author wpl on 2018/7/25.
 * 充当actinbar的头部布局中的信息
 */
public class TopLayoutInfo {
    private boolean back;
    private String title;

    public TopLayoutInfo(){}
    public TopLayoutInfo(boolean back, String title){
        this.back = back;
        this.title = title;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
