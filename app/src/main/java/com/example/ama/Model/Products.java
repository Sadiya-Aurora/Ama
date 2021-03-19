package com.example.ama.Model;

public class Products {
    private String pname, description, price, image, category, pid, date, time,ProductStatus,SellerID,SellerPhone,SellerEmail;

    public Products()
    {

    }

    public Products(String sellerID, String sellerPhone, String sellerEmail) {
        SellerID = sellerID;
        SellerPhone = sellerPhone;
        SellerEmail = sellerEmail;
    }

    public Products(String pname, String description, String price, String image, String category, String pid, String date, String time, String ProductStatus) {
        this.pname = pname;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.ProductStatus = ProductStatus;
}
    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public String getSellerID() {
        return SellerID;
    }

    public void setSellerID(String sellerID) {
        SellerID = sellerID;
    }

    public String getSellerPhone() {
        return SellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        SellerPhone = sellerPhone;
    }

    public String getSellerEmail() {
        return SellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        SellerEmail = sellerEmail;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getProductStatus() {
        return ProductStatus;
    }

    public void setProductStatus(String ProductStatus) {
        this.ProductStatus = ProductStatus;
    }
}