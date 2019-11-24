package io.pulque.fleu.data.mappers

import io.pulque.fleu.data.model.presentation.UserInfo
import io.pulque.fleu.data.model.remote.UserInfoResponse

/*
 * @author savirdev on 2019-11-24
 */

fun UserInfoResponse.toUserInfo() = UserInfo(name, email, picture, username, places.map { it.toPlace() })