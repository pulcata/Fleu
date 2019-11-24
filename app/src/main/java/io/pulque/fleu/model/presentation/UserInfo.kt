package io.pulque.fleu.model.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
 * @author savirdev on 2019-11-24
 */

@Parcelize
data class UserInfo(
    val name: String,
    val email: String,
    val picture: String,
    val username: String
) : Parcelable