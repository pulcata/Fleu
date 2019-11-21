package io.pulque.fleu.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.pulque.fleu.MainActivity
import io.pulque.fleu.ui.PickAddressActivity
import io.pulque.fleu.ui.PlaceAddressActivity
import io.pulque.fleu.ui.PlacesActivity
import io.pulque.fleu.ui.ProfileActivity

@Module
abstract class ActivityModule{

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesPickAddressActivity() : PickAddressActivity

    @ContributesAndroidInjector
    abstract fun contributesPlacesActivity() : PlacesActivity

    @ContributesAndroidInjector
    abstract fun contributesProfileActivity() : ProfileActivity

    @ContributesAndroidInjector
    abstract fun contributesPlaceAddressActivity() : PlaceAddressActivity
}
