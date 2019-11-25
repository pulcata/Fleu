package io.pulque.fleu.viewmodel

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pulque.fleu.data.DataResponse
import io.pulque.fleu.data.model.presentation.Place
import io.pulque.fleu.data.model.presentation.UserInfo
import io.pulque.fleu.repository.location.LocationRepository
import io.pulque.fleu.repository.place.PlaceRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaceAddressViewModel @Inject constructor(private val repository: LocationRepository, private val placeRepository: PlaceRepository) : FleuViewModel(){

    private val _addressList = MutableLiveData<List<Address>?>()
    val addressList : LiveData<List<Address>?>
        get() = _addressList

    private val _savePlace = MutableLiveData<UserInfo>()
    val savePlace : LiveData<UserInfo>
        get() = _savePlace

    fun fetchAddress(query: String){

        viewModelScope.launch {
            _addressList.postValue(repository.fetchAddresses(query))
        }
    }

    fun saveAddress(nickname: String, lat: Double, lon: Double){

        viewModelScope.launch {
            when(val response = placeRepository.savePlace(nickname, lat, lon)){
                is DataResponse.Success ->{
                    _savePlace.postValue(response.data)
                }
                is DataResponse.Error -> {
                    notifyErrorMutableLiveData.postValue(response.error)
                }
            }
        }
    }
}
