package io.pulque.fleu.di.modules

import android.location.Geocoder
import dagger.Module
import dagger.Provides
import io.pulque.fleu.datasource.remote.net.FleuApi
import io.pulque.fleu.repository.location.LocationRepository
import io.pulque.fleu.repository.location.LocationRepositoryImpl
import io.pulque.fleu.repository.login.LoginRepository
import io.pulque.fleu.repository.login.LoginRepositoryImpl

@Module
class RepositoryModule {

    @Provides
    fun providesLocationRepository(geocoder: Geocoder) : LocationRepository = LocationRepositoryImpl(geocoder)

    @Provides
    fun providesLoginRepository(fleuApi: FleuApi) : LoginRepository = LoginRepositoryImpl(fleuApi)
}
