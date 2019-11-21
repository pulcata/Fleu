package io.pulque.fleu.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.pulque.fleu.di.ViewModelFactory
import io.pulque.fleu.di.annotations.ViewModelKey
import io.pulque.fleu.viewmodel.PlaceAddressViewModel

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(PlaceAddressViewModel::class)
    abstract fun bindOrdersTabViewModel(ordersTabViewModel: PlaceAddressViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(sampleViewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
