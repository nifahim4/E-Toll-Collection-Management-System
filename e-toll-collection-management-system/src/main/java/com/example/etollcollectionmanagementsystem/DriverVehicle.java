package com.example.etollcollectionmanagementsystem;

public class DriverVehicle {
    private int id;
    private String driverName;
    private String driverLisence;
    private String vehicleNumber;
    private String vehicleModel;
    private String vehicleType;
    private String date;

    public DriverVehicle() {
    }


    public DriverVehicle(String driverName, String driverLisence, String vehicleNumber, String vehicleModel, String vehicleType) {
        this.driverName = driverName;
        this.driverLisence = driverLisence;
        this.vehicleNumber = vehicleNumber;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverLisence() {
        return driverLisence;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehicleType() {
        return vehicleType;
    }
    public String getDate() {
        return date;
    }
    public String getTimestamp() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }
}