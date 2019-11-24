package io.pulque.fleu.utils.errors

/*
 * @author savirdev on 2019-11-24
 */

sealed class FleuDataError : Throwable(){

    //UnknownError
    class UnknownError : FleuDataError()

    //Server
    class BadRequestError : FleuDataError()
    class RequestNotSuccesful : FleuDataError()

    //User
    class UserNotFound : FleuDataError()

    //Google login
    class GoogleLoginError : FleuDataError()
}