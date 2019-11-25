package io.pulque.fleu.data.model.remote.request

import com.google.gson.annotations.SerializedName

/*
 * @author savirdev on 2019-11-24
 */

data class PlaceRequest(

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double
)