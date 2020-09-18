package com.github.codehaocode.notificationreceiverapp.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.codehaocode.notificationreceiverapp.Application;
import com.github.codehaocode.notificationreceiverapp.model.NotificationModel;
import com.github.codehaocode.notificationreceiverapp.model.repository.NotificationsRepository;
import com.github.codehaocode.notificationreceiverapp.presentation.filter.FilterMode;
import com.github.codehaocode.notificationreceiverapp.presentation.filter.period.FilterPeriodImpl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

import static com.github.codehaocode.notificationreceiverapp.presentation.filter.FilterMode.ALL;
import static com.github.codehaocode.notificationreceiverapp.presentation.filter.FilterMode.DAY;
import static com.github.codehaocode.notificationreceiverapp.presentation.filter.FilterMode.HOUR;
import static com.github.codehaocode.notificationreceiverapp.presentation.filter.FilterMode.MONTH;

public class MainViewModel extends ViewModel {

    private NotificationsRepository notificationsRepository;
    private boolean serviceState = true;

    private CompositeDisposable disposable;
    private Subject<FilterMode> subject;

    private MutableLiveData<FilterMode> filteringMode = new MutableLiveData<>();
    private MutableLiveData<List<NotificationModel>> notifications = new MutableLiveData<>();
    private MutableLiveData<Boolean> serviceActive = new MutableLiveData<>();

    @Inject
    public MainViewModel(@NonNull NotificationsRepository notificationsRepository,
                         @NonNull CompositeDisposable disposable,
                         @NonNull Subject<FilterMode> subject) {
        this.notificationsRepository = notificationsRepository;
        this.disposable = disposable;
        this.subject = subject;
        initNotificationFiltering();
    }

    @Inject
    public void setServiceActive(boolean serviceState) {
        this.serviceState = serviceState;
        serviceActive.setValue(serviceState);
    }

    @Inject
    public void setFilteringMode(@NonNull FilterMode filterMode) {
        subject.onNext(filterMode);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    public void filterAll() {
        subject.onNext(ALL);
    }

    public void filterForHour() {
        subject.onNext(HOUR);
    }

    public void filterForDay() {
        subject.onNext(DAY);
    }

    public void filterForMonth() {
        subject.onNext(MONTH);
    }

    public void toggleService() {
        serviceState = !serviceState;
        serviceActive.setValue(serviceState);
        Application.setForegroundServiceRunning(serviceState);
    }

    public LiveData<List<NotificationModel>> getNotifications() {
        return notifications;
    }

    public LiveData<Boolean> getServiceActiveState() {
        return serviceActive;
    }

    public LiveData<FilterMode> getFilteringMode() {
        return filteringMode;
    }

    private void initNotificationFiltering() {
        disposable.add(
                subject.subscribeOn(Schedulers.io())
                        .doOnNext(filterMode -> this.filteringMode.postValue(filterMode))
                        .flatMap(this::switchSource)
                        .map(notifications -> {
                            Collections.sort(notifications, (notificationModel, t1) -> t1.getCalendar().compareTo(notificationModel.getCalendar()));
                            return notifications;
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((notificationsList -> notifications.setValue(notificationsList)))
        );
    }

    private Observable<List<NotificationModel>> switchSource(FilterMode mode) {
        switch (mode) {
            case HOUR:
                return notificationsRepository.getNotifications(new FilterPeriodImpl(HOUR)).toObservable();
            case DAY:
                return notificationsRepository.getNotifications(new FilterPeriodImpl(DAY)).toObservable();
            case MONTH:
                return notificationsRepository.getNotifications(new FilterPeriodImpl(MONTH)).toObservable();
        }
        return notificationsRepository.getNotifications(new FilterPeriodImpl(ALL)).toObservable();
    }
}
