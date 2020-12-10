package com.example.duan1.Model;

public class Cart {
    private String id;
    private String name;
    private Double price;
    private String images;

    public Cart() {
    }

    public Cart(String id, String name, Double price, String images) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
