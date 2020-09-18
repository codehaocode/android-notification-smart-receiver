package com.github.codehaocode.notificationreceiverapp.dependencyinjection.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.codehaocode.notificationreceiverapp.dependencyinjection.viewmodel.ViewModelFactory;
import com.github.codehaocode.notificationreceiverapp.dependencyinjection.viewmodel.ViewModelKey;
import com.github.codehaocode.notificationreceiverapp.presentation.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
