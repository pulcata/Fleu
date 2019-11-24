package io.pulque.fleu.repository.user

import io.pulque.fleu.data.DataResponse
import io.pulque.fleu.data.local.sharedpreference.FleuSharedPreferences
import io.pulque.fleu.data.remote.net.FleuApi
import io.pulque.fleu.data.mappers.toUserInfo
import io.pulque.fleu.utils.errors.FleuDataError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * @author savirdev on 2019-11-24
 */

class UserRespositoryImpl @Inject constructor(private val fleuApi: FleuApi, private val fleuSharedPreferences: FleuSharedPreferences) : UserRepository{

    override suspend fun getUserInfo() = withContext(Dispatchers.IO){
        val response = fleuApi.getUserInfo(fleuSharedPreferences.token)

        if (!response.isSuccessful){
            return@withContext DataResponse.Error(FleuDataError.RequestNotSuccesful())
        }

        response.body()?.let {
            return@withContext DataResponse.Success(it.toUserInfo())
        } ?: return@withContext DataResponse.Error(FleuDataError.UnknownError())
    }
}