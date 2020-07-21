package com.iot.platform.Interface.Entity;

import java.sql.Date;

import com.iot.platform.Core.System.DateRange;

public interface DateRangeInterface {

    Date getFromDate();

    Date getToDate();

    void setFromDate(Date fromDate);

    void setToDate(Date toDate);

    default void setDateRange(DateRange dateRange) {
        if (dateRange == null) {
            this.setFromDate(null);
            this.setToDate(null);
        } else {
            this.setFromDate(dateRange.getFromDate());
            this.setToDate(dateRange.getToDate());
        }
    }

    default DateRange getDateRange() {
        return new DateRange(this.getFromDate(), this.getToDate());
    }

}