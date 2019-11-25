package io.pulque.fleu.repository.place

import io.pulque.fleu.data.DataResponse
import io.pulque.fleu.data.model.presentation.UserInfo

/*
 * @author savirdev on 2019-11-24
 */

interface PlaceRepository{

    suspend fun savePlace(nickname: String, lat: Double, lon: Double) : DataResponse<UserInfo>
}