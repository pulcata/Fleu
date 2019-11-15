package io.pulque.fleu

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.pulque.fleu.di.components.DaggerAppComponent

class FleuApplication : DaggerApplication(){

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}
