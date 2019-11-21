package io.pulque.fleu.di.modules

import android.app.Application
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class LocationModule{

    @Provides
    fun providesLocationProviderClient(application: Application) = LocationServices.getFusedLocationProviderClient(application)

    @Provides
    fun providesGeocoder(application: Application) = Geocoder(application, Locale.getDefault())

    @Provides
    fun providesGeofenceClient(application: Application) = LocationServices.getGeofencingClient(application)

}
