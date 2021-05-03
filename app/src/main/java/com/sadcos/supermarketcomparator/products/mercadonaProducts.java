package com.sadcos.supermarketcomparator.products;

import com.google.gson.annotations.SerializedName;
public class mercadonaProducts {
    @SerializedName("id") private int id;
    @SerializedName("product_name") private String product_name;
    @SerializedName("link") private String link;
    @SerializedName("price") private Double price;
    private String qty;
    private String cartproduct_name;
    private Double cartprice;
    public mercadonaProducts(String product_name, String price,String qty) {
        this.cartproduct_name = product_name;
        this.cartprice = Double.parseDouble(price);
        this.qty=qty;
    }

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

    public String getQty() {
        return qty;
    }

    public String getCartproduct_name() {
        return cartproduct_name;
    }

    public Double getCartprice() {
        return cartprice;
    }
}
