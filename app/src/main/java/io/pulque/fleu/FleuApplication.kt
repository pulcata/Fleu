package io.pulque.fleu

import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
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

        FirebaseApp.initializeApp(this)

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

}
