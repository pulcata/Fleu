package io.pulque.fleu.data.remote.net

import io.pulque.fleu.data.model.remote.request.PlaceRequest
import io.pulque.fleu.data.model.remote.response.PlaceResponse
import io.pulque.fleu.data.model.remote.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FleuApi{

    @GET("user/register")
    suspend fun verifyRegister(@Header("authorization") authorization : String) : Response<UserInfoResponse>

    @GET("user/info")
    suspend fun getUserInfo(@Header("authorization") authorization : String) : Response<UserInfoResponse>

    @POST("place")
    suspend fun savePlace(@Header("authorization") authorization : String, @Body placeRequest: PlaceRequest) : Response<UserInfoResponse>
}
