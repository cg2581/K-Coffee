package com.example.duan1.Model;

import com.google.firebase.database.Exclude;

public class ProductDrink {
    private String id;
    private String nameDrink;
    private String codeDrink;
    private Double priceDrink;
    private String describeDrink;
    private String imagesDrink;

    public ProductDrink() {
    }

    public ProductDrink(String nameDrink, String codeDrink, Double priceDrink, String describeDrink, String imagesDrink) {
        this.nameDrink = nameDrink;
        this.codeDrink = codeDrink;
        this.priceDrink = priceDrink;
        this.describeDrink = describeDrink;
        this.imagesDrink = imagesDrink;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getNameDrink() {
        return nameDrink;
    }

    public void setNameDrink(String nameDrink) {
        this.nameDrink = nameDrink;
    }

    public String getCodeDrink() {
        return codeDrink;
    }

    public void setCodeDrink(String codeDrink) {
        this.codeDrink = codeDrink;
    }

    public Double getPriceDrink() {
        return priceDrink;
    }

    public void setPriceDrink(Double priceDrink) {
        this.priceDrink = priceDrink;
    }

    public String getDescribeDrink() {
        return describeDrink;
    }

    public void setDescribeDrink(String describeDrink) {
        this.describeDrink = describeDrink;
    }

    public String getImagesDrink() {
        return imagesDrink;
    }

    public void setImagesDrink(String imagesDrink) {
        this.imagesDrink = imagesDrink;
    }

    @Override
    public String toString() {
        return "ProductDrink{" +
                "id='" + id + '\'' +
                ", nameDrink='" + nameDrink + '\'' +
                ", codeDrink='" + codeDrink + '\'' +
                ", priceDrink=" + priceDrink +
                ", describeDrink='" + describeDrink + '\'' +
                ", imagesDrink='" + imagesDrink + '\'' +
                '}';
    }
}
