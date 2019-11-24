package io.pulque.fleu.parsers

import io.pulque.fleu.model.presentation.UserInfo
import io.pulque.fleu.model.remote.UserInfoResponse

/*
 * @author savirdev on 2019-11-24
 */

fun UserInfoResponse.toUserInfo() = UserInfo(name, email, picture, username)