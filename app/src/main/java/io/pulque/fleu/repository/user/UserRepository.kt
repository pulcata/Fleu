package io.pulque.fleu.repository.user

import io.pulque.fleu.data.DataResponse
import io.pulque.fleu.data.model.presentation.UserInfo

/*
 * @author savirdev on 2019-11-24
 */

interface UserRepository {

    suspend fun getUserInfo() : DataResponse<UserInfo>
}