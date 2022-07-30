package com.svute.appsale.data.model;

import java.util.List;

public class Food {

    private String _id;
    private String name;
    private String address;
    private int price;
    private String img;
    private int quantity;
    private List<String> gallery;

    public Food() { }

    public Food(String id, String name, String address, int price, String img, int quantity, List<String> gallery) {
        this._id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.img = img;
        this.quantity = quantity;
        this.gallery = gallery;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", quantity=" + quantity +
                ", gallery=" + gallery +
                '}';
    }
}
