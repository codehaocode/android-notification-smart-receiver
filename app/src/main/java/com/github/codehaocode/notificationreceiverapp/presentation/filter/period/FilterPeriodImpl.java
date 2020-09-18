package com.github.codehaocode.notificationreceiverapp.presentation.filter.period;

import com.github.codehaocode.notificationreceiverapp.presentation.filter.FilterMode;

import java.util.Calendar;

public class FilterPeriodImpl implements FilterPeriod {

    private FilterMode filterMode;

    public FilterPeriodImpl(FilterMode filterMode) {
        this.filterMode = filterMode;
    }

    @Override
    public long getStartPeriod() {
        return calculateStartPeriod();
    }

    private long calculateStartPeriod() {
        Calendar calendar = Calendar.getInstance();
        switch (filterMode) {
            case HOUR:
                calendar.add(Calendar.HOUR, -1);
                return calendar.getTimeInMillis();
            case DAY:
                calendar.add(Calendar.DATE, -1);
                return calendar.getTimeInMillis();
            case MONTH:
                calendar.add(Calendar.MONTH, -1);
                return calendar.getTimeInMillis();
        }
        return 0;
    }
}
