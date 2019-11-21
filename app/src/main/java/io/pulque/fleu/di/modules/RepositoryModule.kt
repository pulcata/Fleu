package io.pulque.fleu.di.modules

import android.location.Geocoder
import dagger.Module
import dagger.Provides
import io.pulque.fleu.repository.LocationRepository
import io.pulque.fleu.repository.location.LocationRepositoryImpl

@Module
class RepositoryModule {

    @Provides
    fun providesLocationRepository(geocoder: Geocoder) : LocationRepository = LocationRepositoryImpl(geocoder)
}
