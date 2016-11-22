package com.wifiesta.restaurant.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.wifiesta.restaurant.model.Menu;

public class MenuFilter {

    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private DayOfWeek validDayOfWeek;
    @DateTimeFormat(iso = ISO.TIME)
    private LocalTime fromTime;
    private LocalTime toTime;

    // Just for spring
    public MenuFilter() {
    }

    public MenuFilter(LocalDateTime validFrom, LocalDateTime validTo) {
        this(validFrom, validTo, null, null, null);
    }

    public MenuFilter(DayOfWeek validDayOfWeek) {
        this(null, null, validDayOfWeek, null, null);
    }

    public MenuFilter(LocalDateTime validFrom, LocalDateTime validTo, DayOfWeek validDayOfWeek, LocalTime fromTime, LocalTime toTime) {
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.validDayOfWeek = validDayOfWeek;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public boolean menuApplies(Menu menu) {
        if (menu == null) {
            return false;
        }

        if (!this.appliesWeekTimes(menu)) {
            return false;
        }

        if (!this.appliesDates(menu)) {
            return false;
        }

        return true;
    }

    // Para el filtrado de fechas y horas se toma la siguiente lógica:
    // el menu debe contener dentro de sus fechas/horas habilitadas, al filtro recibido, es decir:
    // el from del menu siempre debe ser menor (o igual) al from del filtro, y
    // el to del menu siempre debe ser mayor (o igual) al to del filtro.
    // Esto significa que el menú está "contenido" en el filtro.
    // De esta manera solo se devuelven menus que estén habilitados en todo el rango pedido.

    private boolean appliesWeekTimes(Menu menu) {
        if (menu.getValidWeekTimes() != null && !menu.getValidWeekTimes().isEmpty()) {
            return menu.getValidWeekTimes().stream().anyMatch(validWeekTime -> {
                if (this.validDayOfWeek != null && !this.validDayOfWeek.equals(validWeekTime.getDayOfWeek())) {
                    return false;
                }
                if (this.fromTime != null && this.fromTime.isAfter(validWeekTime.getFromTime())) {
                    return false;
                }
                if (this.toTime != null && this.toTime.isBefore(validWeekTime.getToTime())) {
                    return false;
                }
                return true;
            });
        }
        return true;
    }

    private boolean appliesDates(Menu menu) {
        if (menu.getValidFrom() != null) {
            if (this.validFrom != null && this.validFrom.isAfter(menu.getValidFrom())) {
                return false;
            }
        }
        if (menu.getValidTo() != null) {
            if (this.validTo != null && this.validTo.isBefore(menu.getValidTo())) {
                return false;
            }
        }
        return true;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public void setValidDayOfWeek(DayOfWeek validDayOfWeek) {
        this.validDayOfWeek = validDayOfWeek;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

}
