package com.github.codehaocode.notificationreceiverapp.presentation;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.codehaocode.notificationreceiverapp.Application;
import com.github.codehaocode.notificationreceiverapp.R;
import com.github.codehaocode.notificationreceiverapp.databinding.ActivityMainBinding;
import com.github.codehaocode.notificationreceiverapp.model.NotificationModel;
import com.github.codehaocode.notificationreceiverapp.presentation.filter.FilterView;
import com.github.codehaocode.notificationreceiverapp.services.AppForegroundService;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements FilterView.FilterSelectedListener {

    private ActivityMainBinding binding;
    private ViewModelProvider.Factory factory;
    private com.github.codehaocode.notificationreceiverapp.presentation.MainViewModel viewModel;
    private FilterView filterView;
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        initFilterView();
        initRecyclerView();
        viewModel = new ViewModelProvider(getViewModelStore(), factory).get(com.github.codehaocode.notificationreceiverapp.presentation.MainViewModel.class);
        initObservers();
        viewModel.setServiceActive(true);
        startService();
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorGray));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.filterMenu) {
            setFilterViewVisibility(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFilterAll() {
        viewModel.filterAll();
    }

    @Override
    public void onFilterHour() {
        viewModel.filterForHour();
    }

    @Override
    public void onFilterDay() {
        viewModel.filterForDay();
    }

    @Override
    public void onFilterMonth() {
        viewModel.filterForMonth();
    }

    @Inject
    public void setFactory(ViewModelProvider.Factory factory) {
        this.factory = factory;
    }

    private void initFilterView() {
        filterView = new FilterView(this, getLayoutInflater());
        filterView.setOnDismissListener(() -> setFilterViewVisibility(false));
        binding.rootView.setForeground(getDrawable(R.drawable.window_dim));
        binding.rootView.getForeground().setAlpha(0);
    }

    private void initRecyclerView() {
        binding.rvList.setHasFixedSize(true);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvList.setAdapter(new com.github.codehaocode.notificationreceiverapp.presentation.NotificationsListAdapter());
    }


    private void initObservers() {
        viewModel.getNotifications().observe(this, this::updateAdapter);
        viewModel.getFilteringMode().observe(this, filterView::setFilteringMode);
    }

    private void updateAdapter(List<NotificationModel> notifications) {
        binding.noNotificationGroup.setVisibility(notifications.isEmpty() ? View.VISIBLE : View.GONE);
        binding.rvList.setVisibility(notifications.isEmpty() ? View.GONE : View.VISIBLE);
        getAdapter().setItems(notifications);
        getAdapter().notifyDataSetChanged();
    }


    private void setFilterViewVisibility(boolean isVisible) {
        if (isVisible) {
            filterView.show(findViewById(R.id.filterMenu));
            binding.rootView.getForeground().setAlpha(100);
        } else {
            filterView.dismiss();
            binding.rootView.getForeground().setAlpha(0);
        }
    }

    private com.github.codehaocode.notificationreceiverapp.presentation.NotificationsListAdapter getAdapter() {
        return (com.github.codehaocode.notificationreceiverapp.presentation.NotificationsListAdapter) binding.rvList.getAdapter();
    }

    private void startService() {
        Intent serviceIntent = new Intent(this, AppForegroundService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void stopService() {
        Intent serviceIntent = new Intent(MainActivity.this, AppForegroundService.class);
        stopService(serviceIntent);
    }

    private AlertDialog getNotificationListenerDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.notification_listener_service)
                .setMessage(R.string.notification_listener_service_expplanation)
                .setPositiveButton(getString(R.string.yes), (dialog, id) -> startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS)))
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.dismiss());
        return dialogBuilder.create();
    }
}
