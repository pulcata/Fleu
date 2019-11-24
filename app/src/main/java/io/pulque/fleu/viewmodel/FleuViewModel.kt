package io.pulque.fleu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.pulque.fleu.utils.errors.FleuDataError

/*
 * @author savirdev on 2019-11-24
 */

abstract class FleuViewModel : ViewModel(){

    protected val notifyErrorMutableLiveData = MutableLiveData<FleuDataError>()
    val error : LiveData<FleuDataError>
        get() = notifyErrorMutableLiveData
}