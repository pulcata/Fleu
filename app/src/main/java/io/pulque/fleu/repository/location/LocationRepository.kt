package io.pulque.fleu.repository.location

import android.location.Address

interface LocationRepository{

    suspend fun fetchAddresses(query: String) : List<Address>?
}
