package com.haiyishuzi.haiyishuzijcm.api;

//response返回的状态码
public enum ResponseCodeEnum {

    /**
     * 响应code
     * 1成功
     * 0失败
     * 9未登录
     */
    SUCCESS(1),FAIL(0),NO_LOGIN(9);

    private int code;
    ResponseCodeEnum(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
