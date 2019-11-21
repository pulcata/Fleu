package io.pulque.fleu.repository

import android.location.Address

interface LocationRepository{

    suspend fun fetchAddresses(query: String) : List<Address>?
}
