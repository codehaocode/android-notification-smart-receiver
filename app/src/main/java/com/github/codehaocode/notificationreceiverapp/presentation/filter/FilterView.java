package com.github.codehaocode.notificationreceiverapp.presentation.filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.github.codehaocode.notificationreceiverapp.R;

public class FilterView extends PopupWindow {

    private FilterSelectedListener listener;

    @SuppressLint("InflateParams")
    public FilterView(@NonNull FilterSelectedListener listener,
                      @NonNull LayoutInflater inflater) {
        super(inflater.inflate(R.layout.filter_view, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        this.listener = listener;
        initListener();
    }

    public void show(View view) {
        int offset = dpToPx(32f, view.getContext());
        showAtLocation(view, Gravity.TOP | Gravity.END, offset, offset * 2);
    }

    public void setFilteringMode(com.github.codehaocode.notificationreceiverapp.presentation.filter.FilterMode filteringMode) {
        Log.d("TEST_TEST", filteringMode.toString());
        switch (filteringMode) {
            case ALL:
                ((RadioButton) getContentView().findViewById(R.id.filterAll)).setChecked(true);
                break;
            case HOUR:
                ((RadioButton) getContentView().findViewById(R.id.filterHour)).setChecked(true);
                break;
            case DAY:
                ((RadioButton) getContentView().findViewById(R.id.filterDay)).setChecked(true);
                break;
            case MONTH:
                ((RadioButton) getContentView().findViewById(R.id.filterMonth)).setChecked(true);
                break;
        }
    }

    private void initListener() {
        ((RadioGroup) getContentView().findViewById(R.id.filterGroup)).setOnCheckedChangeListener((radioGroup, id) -> {
            switch (id) {
                case R.id.filterAll:
                    if (listener == null) return;
                    listener.onFilterAll();
                    break;
                case R.id.filterHour:
                    if (listener == null) return;
                    listener.onFilterHour();
                    break;
                case R.id.filterDay:
                    if (listener == null) return;
                    listener.onFilterDay();
                    break;
                case R.id.filterMonth:
                    if (listener == null) return;
                    listener.onFilterMonth();
                    break;
                default:
                    break;
            }
            dismiss();
        });
    }

    private int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public interface FilterSelectedListener {
        void onFilterAll();

        void onFilterHour();

        void onFilterDay();

        void onFilterMonth();
    }
}