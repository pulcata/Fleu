package io.pulque.fleu.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.pulque.fleu.FleuApplication
import io.pulque.fleu.di.modules.*
import javax.inject.Singleton

@Singleton
@Component(

    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        RetrofitModule::class,
        HelperModule::class,
        LocationModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        FirebaseModule::class
    ]
)

interface AppComponent : AndroidInjector<FleuApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance

        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
