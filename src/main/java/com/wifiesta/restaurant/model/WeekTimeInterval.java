package com.wifiesta.restaurant.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WeekTimeInterval {

    private DayOfWeek dayOfWeek;
    private LocalTime fromTime;
    private LocalTime toTime;

    // Just for serializer.
    public WeekTimeInterval() {
    }

    public WeekTimeInterval(DayOfWeek dayOfWeek, LocalTime fromTime, LocalTime toTime) {
        this.dayOfWeek = dayOfWeek;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public DayOfWeek getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getFromTime() {
        return this.fromTime;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return this.toTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

}
