package com.sadcos.supermarketcomparator.products;

import com.google.gson.annotations.SerializedName;

public class diaProducts {
    @SerializedName("id") private int id;
    @SerializedName("product_name") private String product_name;
    @SerializedName("link") private String link;
    @SerializedName("price") private String price;
    @SerializedName("price_per_kg") private String price_per_kg;

    public int getId() {
        return id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getLink() {
        return link;
    }

    public String getPrice() {
        return price;
    }

    public String getPrice_per_kg() {
        return price_per_kg;
    }
}
