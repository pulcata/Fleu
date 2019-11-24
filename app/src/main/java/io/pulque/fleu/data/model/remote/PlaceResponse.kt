package io.pulque.fleu.data.model.remote

import com.google.gson.annotations.SerializedName
import java.util.*

/*
 * @author savirdev on 2019-11-24
 */

data class PlaceResponse(

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double,

    @SerializedName("createdAt")
    val createdAt: Date
)