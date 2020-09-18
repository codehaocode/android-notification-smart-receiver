package com.github.codehaocode.notificationreceiverapp.dependencyinjection.modules;

import com.github.codehaocode.notificationreceiverapp.presentation.MainActivity;
import com.github.codehaocode.notificationreceiverapp.services.AppForegroundService;
import com.github.codehaocode.notificationreceiverapp.services.NotificationJobScheduler;
import com.github.codehaocode.notificationreceiverapp.services.NotificationProcessorService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ComponentsModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract NotificationProcessorService contributeForegroundService();

    @ContributesAndroidInjector
    abstract AppForegroundService contributeAppForegroundService();

    @ContributesAndroidInjector
    abstract NotificationJobScheduler contributeNotificationJobScheduler();
}
