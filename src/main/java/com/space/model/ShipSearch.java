package com.space.model;

import com.space.controller.ShipOrder;

public class ShipSearch {
    String name;
    String planet;
    ShipType shipType;
    long after;
    long before;
    String isUsed;
    double minSpeed;
    double maxSpeed;
    int minCrewSize;
    int maxCrewSize;
    double minRating;
    double maxRating;
    ShipOrder order = ShipOrder.ID;
    int pageNumber = 0;
    int pageSize = 3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public long getAfter() {
        return after;
    }

    public void setAfter(long after) {
        this.after = after;
    }

    public long getBefore() {
        return before;
    }

    public void setBefore(long before) {
        this.before = before;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public double getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMinCrewSize() {
        return minCrewSize;
    }

    public void setMinCrewSize(int minCrewSize) {
        this.minCrewSize = minCrewSize;
    }

    public int getMaxCrewSize() {
        return maxCrewSize;
    }

    public void setMaxCrewSize(int maxCrewSize) {
        this.maxCrewSize = maxCrewSize;
    }

    public double getMinRating() {
        return minRating;
    }

    public void setMinRating(double minRating) {
        this.minRating = minRating;
    }

    public double getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(double maxRating) {
        this.maxRating = maxRating;
    }

    public ShipOrder getOrder() {
        return order;
    }

    public void setOrder(ShipOrder order) {
        this.order = order;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
