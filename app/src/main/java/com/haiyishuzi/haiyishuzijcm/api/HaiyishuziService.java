package com.haiyishuzi.haiyishuzijcm.api;

import androidx.lifecycle.LiveData;

import com.haiyishuzi.haiyishuzijcm.vo.User;

import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * REST API access points
 */

public interface HaiyishuziService {

    @POST("loginApp")
    LiveData<ApiResponse<BaseResponse<User>>> getUser(@Query("username") String logincode, @Query("password") String password);

    //修改密码
    @POST("userInfo/resetPassword.do")
    LiveData<ApiResponse<BaseResponse<Boolean>>> resetPassword(@Query("oldTicket") String oldTicket, @Query("newTicket") String newTicket);


//    //登录接口
//    @POST(MainConstant.LOGIN_URL)
//    LiveData<ApiResponse<BaseResponse<LoginResponseBody>>> login(@Body RequestBody<LoginRequestBody> body);


    //警员信息接口
//    @POST("/yzd/police/list")
//    @Headers({"Domain-Name: baseUrl"})
//    LiveData<ApiResponse<BaseResponse<PoliceResponseBody>>> policeInfo(@Body RequestBody<PoliceRequestBody> body);


}
