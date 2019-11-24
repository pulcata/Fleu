package io.pulque.fleu.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.pulque.fleu.di.ViewModelFactory
import io.pulque.fleu.di.annotations.ViewModelKey
import io.pulque.fleu.viewmodel.HomeViewModel
import io.pulque.fleu.viewmodel.LoginViewModel
import io.pulque.fleu.viewmodel.PlaceAddressViewModel

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(PlaceAddressViewModel::class)
    abstract fun bindOrdersTabViewModel(placeAddressViewModel: PlaceAddressViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(sampleViewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
