package com.wifiesta.restaurant.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    private String id;
    private String name;
    private String description;
    private String pictureUrl;
    private Price price;
    private Integer ranking;

    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private List<WeekTimeInterval> validWeekTimes;

    public void addValidWeekTime(WeekTimeInterval weekTime) {
        if (this.validWeekTimes == null) {
            this.validWeekTimes = new ArrayList<>();
        }
        this.validWeekTimes.add(weekTime);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Menu) {
            Menu other = (Menu) obj;
            return this.id.equals(other.id);
        }
        return false;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getRanking() {
        return this.ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public LocalDateTime getValidFrom() {
        return this.validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDateTime getValidTo() {
        return this.validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public List<WeekTimeInterval> getValidWeekTimes() {
        return this.validWeekTimes;
    }

    public void setValidWeekTimes(List<WeekTimeInterval> validWeekTimes) {
        this.validWeekTimes = validWeekTimes;
    }

}
