package com.space.model;

import java.util.Date;

public class ShipInfo {
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_PLANET_LENGTH = 50;

    private String name;
    private String planet;
    private ShipType shipType;
    private Date prodDate;
    private boolean isUsed = false;
    private double speed;
    private int crewSize;

    public ShipInfo() {
    }

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

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(int crewSize) {
        this.crewSize = crewSize;
    }

    public boolean canCreateShip(){
        boolean result = true;

        if (name == null || name.isEmpty() || name.length() > MAX_NAME_LENGTH){
            result = false;
        }

        if (planet == null || planet.isEmpty() || planet.length() > MAX_PLANET_LENGTH){
            result = false;
        }

        if (shipType == null){
            result = false;
        }

        if (prodDate == null || prodDate.getTime() < 0) {
            result = false;
        }

        if (speed < 0.01 || speed > 0.99){
            return false;
        }

        if (crewSize < 1 || crewSize > 9999) {
            return false;
        }

        return result;
    }

    public boolean canUpdate(){
        boolean result = true;

        if (name!=null
                && (name.length() > MAX_NAME_LENGTH || name.isEmpty())){
            result = false;
        }

        if (planet!=null
                && (planet.length() > MAX_PLANET_LENGTH || planet.isEmpty())){
            result = false;
        }

        if (!(speed >= 0.01 && speed <= 0.99)){
            result = false;
        }

        if (!(crewSize >= 1 && crewSize <= 9999)) {
            result = false;
        }

        if (prodDate != null && prodDate.getTime() < 0){
            result = false;
        }

        return result;
    }
}
