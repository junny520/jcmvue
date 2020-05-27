package com.haiyishuzi.haiyishuzijcm.vo;

/**
 * FileName:HeadStates.java
 * 类的详细说明
 *  Nav导航条：头部各控件状态的信息，控制是否显示
 * @author lavine
 * @Date 2019-07-26 10:42
 * @version:1.0.0
 */
public class HeadStates {
    //最左边的返回按钮图标资源id
    private int back;
    //标题
    private String title;
    //右边第一个图标资源文件
    private int rightOne;
    //右边第二个图标资源文件
    private int rightTwo;
    //右边第三个图标资源文件
    private int rightThree;

    public HeadStates(){}

    public HeadStates(int back, String title, int rightOne, int rightTwo, int rightThree){
        this.back = back;
        this.title = title;
        this.rightOne = rightOne;
        this.rightTwo = rightTwo;
        this.rightThree = rightThree;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRightOne() {
        return rightOne;
    }

    public void setRightOne(int rightOne) {
        this.rightOne = rightOne;
    }

    public int getRightTwo() {
        return rightTwo;
    }

    public void setRightTwo(int rightTwo) {
        this.rightTwo = rightTwo;
    }

    public int getRightThree() {
        return rightThree;
    }

    public void setRightThree(int rightThree) {
        this.rightThree = rightThree;
    }
}


