package io.pulque.fleu.datasource.remote.net

import io.pulque.fleu.model.remote.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface FleuApi{

    @GET("user/verify/register")
    suspend fun verifyRegister(@Header("authorization") authorization : String) : Response<UserInfoResponse>
}
