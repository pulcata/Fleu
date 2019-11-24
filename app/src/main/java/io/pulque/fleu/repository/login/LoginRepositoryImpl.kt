package io.pulque.fleu.repository.login

import io.pulque.fleu.datasource.DataResponse
import io.pulque.fleu.datasource.local.sharedpreference.FleuSharedPreferences
import io.pulque.fleu.datasource.remote.net.FleuApi
import io.pulque.fleu.parsers.toUserInfo
import io.pulque.fleu.utils.errors.FleuDataError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * @author savirdev on 2019-11-24
 */

class LoginRepositoryImpl @Inject constructor(private val fleuApi: FleuApi, private val fleuSharedPreferences: FleuSharedPreferences) : LoginRepository{

    override suspend fun verifyRegister(token: String) = withContext(Dispatchers.IO){
        val response = fleuApi.verifyRegister(token)

        if (response.isSuccessful){
            response.body()?.let {
                return@withContext DataResponse.Success(it.toUserInfo())
            } ?: return@withContext DataResponse.Error()
        }else{
            return@withContext DataResponse.Error(FleuDataError.RequestNotSuccesful())
        }
    }

    override suspend fun saveToken(token: String) {
        fleuSharedPreferences.token = token
    }

}