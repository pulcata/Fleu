package io.pulque.fleu.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.pulque.fleu.data.model.presentation.UserInfo
import io.pulque.fleu.data.DataResponse
import io.pulque.fleu.repository.login.LoginRepository
import io.pulque.fleu.utils.errors.FleuDataError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * @author savirdev on 2019-11-24
 */

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel(){

    private val userInfoMutableLiveData = MutableLiveData<UserInfo>()
    val userInfo : LiveData<UserInfo>
        get() = userInfoMutableLiveData

    private val notifyErrorMutableLiveData = MutableLiveData<FleuDataError>()
    val error : LiveData<FleuDataError>
        get() = notifyErrorMutableLiveData

    private fun validateRegister(token: String){
        viewModelScope.launch {
            when(val response = loginRepository.verifyRegister(token)){
                is DataResponse.Success -> {
                    loginRepository.saveToken(token)
                    userInfoMutableLiveData.postValue(response.data)
                }
                is DataResponse.Error -> {
                    notifyErrorMutableLiveData.postValue(response.error)
                }
            }
        }
    }

    fun retrieveGoogleTokenFromCurrentSession(firebaseUser: FirebaseUser?, task: Task<AuthResult>){

        viewModelScope.launch(Dispatchers.IO) {
            if (task.isSuccessful) {
                firebaseUser?.getIdToken(false)?.addOnSuccessListener {
                    it.token?.let { token ->
                        validateRegister(token)
                    } ?: notifyErrorMutableLiveData.postValue(FleuDataError.GoogleLoginError())
                }
            } else {
                notifyErrorMutableLiveData.postValue(FleuDataError.UserNotFound())
            }
        }
    }
}