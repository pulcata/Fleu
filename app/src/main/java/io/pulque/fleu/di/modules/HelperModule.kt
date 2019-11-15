package io.pulque.fleu.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides
import io.pulque.fleu.helpers.GeofencesHelper

@Module
class HelperModule{

    @Provides
    fun provideGeofenceHelper(application: Application) = GeofencesHelper(application)
}
