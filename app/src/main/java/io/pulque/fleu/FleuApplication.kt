package io.pulque.fleu

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.pulque.fleu.di.components.DaggerAppComponent
import timber.log.Timber

class FleuApplication : DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}
