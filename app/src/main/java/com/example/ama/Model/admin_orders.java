package com.example.ama.Model;

public class admin_orders {
    private String Address, City, Name, Phone, Status, date, time,Total_Bill;

    public admin_orders() {

    }



    public admin_orders(String address, String city, String name, String phone, String status, String date, String time, String total_Bill) {
        Address = address;
        City = city;
        Name = name;
        Phone = phone;
        Status = status;
        this.date = date;
        this.time = time;
        Total_Bill = total_Bill;

    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal_Bill() {
        return Total_Bill;
    }

    public void setTotal_Bill(String total_Bill) {
        Total_Bill = total_Bill;
    }
}
