package com.example.ama.Model;

public class Cart {
    private String pid,pname,Quantity,price,SellerID,Delivery_Status;

    public Cart() {
    }

    public Cart(String sellerID) {
        SellerID = sellerID;
    }

    public String getDelivery_Status() {
        return Delivery_Status;
    }

    public void setDelivery_Status(String delivery_Status) {
        Delivery_Status = delivery_Status;
    }

    public Cart(String pid, String pname, String quantity, String price, String Delivery_Status) {
        this.pid = pid;
        this.pname = pname;
        Quantity = quantity;
        this.price = price;
        this.Delivery_Status=Delivery_Status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getSellerID() {
        return SellerID;
    }

    public void setSellerID(String sellerID) {
        SellerID = sellerID;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
