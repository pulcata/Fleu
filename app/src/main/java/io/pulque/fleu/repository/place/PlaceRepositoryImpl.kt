package io.pulque.fleu.repository.place

import io.pulque.fleu.data.DataResponse
import io.pulque.fleu.data.local.sharedpreference.FleuSharedPreferences
import io.pulque.fleu.data.mappers.toUserInfo
import io.pulque.fleu.data.model.remote.request.PlaceRequest
import io.pulque.fleu.data.remote.net.FleuApi
import io.pulque.fleu.utils.errors.FleuDataError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
 * @author savirdev on 2019-11-24
 */

class PlaceRepositoryImpl @Inject constructor(
    private val fleuApi: FleuApi,
    private val fleuSharedPreferences: FleuSharedPreferences
) : PlaceRepository {

    override suspend fun savePlace(
        nickname: String,
        lat: Double,
        lon: Double
    ) = withContext(Dispatchers.IO) {
        val response =
            fleuApi.savePlace(fleuSharedPreferences.token, PlaceRequest(nickname, lat, lon))

        if (!response.isSuccessful) {
            return@withContext DataResponse.Error(FleuDataError.PlaceNotAddedError())
        }

        response.body()?.let {
            return@withContext DataResponse.Success(it.toUserInfo())
        }?: return@withContext DataResponse.Error(FleuDataError.RequestNotSuccesful())
    }
}