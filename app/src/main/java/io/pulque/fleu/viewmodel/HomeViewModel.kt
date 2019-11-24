package io.pulque.fleu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.pulque.fleu.data.DataResponse
import io.pulque.fleu.data.model.presentation.UserInfo
import io.pulque.fleu.repository.user.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * @author savirdev on 2019-11-24
 */

class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : FleuViewModel(){

    private val userInfoMutableLiveData = MutableLiveData<UserInfo>()
    val userInfo : LiveData<UserInfo>
        get() = userInfoMutableLiveData

    fun getUserInfo(){

        viewModelScope.launch {
            when(val response = userRepository.getUserInfo()){
                is DataResponse.Success ->{
                    userInfoMutableLiveData.postValue(response.data)
                }

                is DataResponse.Error -> {
                    notifyErrorMutableLiveData.postValue(response.error)
                }
            }
        }

    }
}