package io.pulque.fleu.repository.location

import android.location.Address
import android.location.Geocoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

private const val MAX_SEARCH_RESULTS_ADDRESS = 5

class LocationRepositoryImpl @Inject constructor(private val geocoder: Geocoder) :
    LocationRepository {

    override suspend fun fetchAddresses(query: String): List<Address>? = withContext(Dispatchers.IO) {
        try{
            return@withContext geocoder.getFromLocationName(query, MAX_SEARCH_RESULTS_ADDRESS)
        }catch (e: IOException){
            Timber.e(e)
            return@withContext null
        }
    }

}
