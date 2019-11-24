package io.pulque.fleu.datasource

import io.pulque.fleu.utils.errors.FleuDataError

/*
 * @author savirdev on 2019-11-24
 */

sealed class DataResponse<out T> {

    class Success<out T>(val data: T) : DataResponse<T>()
    class Error(val error: FleuDataError = FleuDataError.UnknownError()) : DataResponse<Nothing>()

}