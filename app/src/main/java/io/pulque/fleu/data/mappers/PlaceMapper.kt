package io.pulque.fleu.data.mappers

import io.pulque.fleu.data.model.presentation.Place
import io.pulque.fleu.data.model.remote.PlaceResponse

/*
 * @author savirdev on 2019-11-24
 */

fun PlaceResponse.toPlace() = Place(nickname = nickname, lat = lat, lon = lon, createdAt = createdAt)