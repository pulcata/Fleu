package io.pulque.fleu.data.model.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/*
 * @author savirdev on 2019-11-24
 */

@Parcelize
data class Place(

    val nickname: String,
    val lat: Double,
    val lon: Double

): Parcelable