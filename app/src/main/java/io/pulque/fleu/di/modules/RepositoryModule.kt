package io.pulque.fleu.di.modules

import android.location.Geocoder
import dagger.Module
import dagger.Provides
import io.pulque.fleu.data.local.sharedpreference.FleuSharedPreferences
import io.pulque.fleu.data.remote.net.FleuApi
import io.pulque.fleu.repository.location.LocationRepository
import io.pulque.fleu.repository.location.LocationRepositoryImpl
import io.pulque.fleu.repository.login.LoginRepository
import io.pulque.fleu.repository.login.LoginRepositoryImpl
import io.pulque.fleu.repository.user.UserRepository
import io.pulque.fleu.repository.user.UserRespositoryImpl

@Module
class RepositoryModule {

    @Provides
    fun providesLocationRepository(geocoder: Geocoder) : LocationRepository = LocationRepositoryImpl(geocoder)

    @Provides
    fun providesLoginRepository(fleuApi: FleuApi, fleuSharedPreferences: FleuSharedPreferences) : LoginRepository = LoginRepositoryImpl(fleuApi, fleuSharedPreferences)

    @Provides
    fun providesUserRepository(fleuApi: FleuApi, fleuSharedPreferences: FleuSharedPreferences) : UserRepository = UserRespositoryImpl(fleuApi, fleuSharedPreferences)
}
