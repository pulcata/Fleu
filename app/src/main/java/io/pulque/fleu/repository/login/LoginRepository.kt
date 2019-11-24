package io.pulque.fleu.repository.login

import io.pulque.fleu.model.presentation.UserInfo
import io.pulque.fleu.datasource.DataResponse

/*
 * @author savirdev on 2019-11-24
 */

interface LoginRepository{

    suspend fun verifyRegister(token: String) : DataResponse<UserInfo>

    suspend fun saveToken(token: String)
}