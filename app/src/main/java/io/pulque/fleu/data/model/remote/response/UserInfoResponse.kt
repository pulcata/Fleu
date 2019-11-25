package io.pulque.fleu.data.model.remote.response

import com.google.gson.annotations.SerializedName
import io.pulque.fleu.data.model.remote.response.PlaceResponse

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
    val username: String,

    @SerializedName("places")
    val places: List<PlaceResponse>
)