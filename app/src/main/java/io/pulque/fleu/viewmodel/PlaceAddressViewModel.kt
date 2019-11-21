package io.pulque.fleu.viewmodel

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.pulque.fleu.repository.LocationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaceAddressViewModel @Inject constructor(private val repository: LocationRepository) : ViewModel(){

    private val _addressList = MutableLiveData<List<Address>?>()
    val addressList : LiveData<List<Address>?>
        get() = _addressList

    fun fetchAddress(query: String){

        viewModelScope.launch {
            _addressList.postValue(repository.fetchAddresses(query))
        }
    }
}
