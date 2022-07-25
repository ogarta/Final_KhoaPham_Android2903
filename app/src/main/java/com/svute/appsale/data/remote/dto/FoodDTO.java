package com.example.appsale29032022.data.remote.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pphat on 7/19/2022.
 */
public class FoodDTO {
    @SerializedName("_id")
    private String id;
    private String name;
    private String address;
    private int price;
    private String img;
    private int quantity;
    private List<String> gallery;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "FoodDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", quantity=" + quantity +
                ", gallery=" + gallery +
                '}';
    }
}
