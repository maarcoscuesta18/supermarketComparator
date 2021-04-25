package com.sadcos.supermarketcomparator.products;

import com.google.gson.annotations.SerializedName;
public class mercadonaProducts {
    @SerializedName("id") private int id;
    @SerializedName("product_name") private String product_name;
    @SerializedName("link") private String link;
    @SerializedName("price") private Double price;

    public int getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getLink() {
        return link;
    }

    public Double getPrice() {
        return price;
    }
}
