package io.pulque.fleu.data.remote.net

import io.pulque.fleu.data.model.remote.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface FleuApi{

    @GET("user/register")
    suspend fun verifyRegister(@Header("authorization") authorization : String) : Response<UserInfoResponse>

    @GET("user/info")
    suspend fun getUserInfo(@Header("authorization") authorization : String) : Response<UserInfoResponse>
}
