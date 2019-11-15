package io.pulque.fleu.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.pulque.fleu.MainActivity

@Module
abstract class ActivityModule{

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}
