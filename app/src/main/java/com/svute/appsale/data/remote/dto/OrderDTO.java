package com.example.appsale29032022.data.remote.dto;

import com.example.appsale29032022.data.model.Food;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDTO {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("products")
    @Expose
    private List<Food> foods = null;
    private String idUser;
    private Integer price;
    private Boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", foods=" + foods +
                ", idUser='" + idUser + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
