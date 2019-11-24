package io.pulque.fleu.model.remote

import com.google.gson.annotations.SerializedName

/*
 * @author savirdev on 2019-11-24
 */

data class UserInfoResponse(

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("picture")
    val picture: String,

    @SerializedName("username")
    val username: String
)