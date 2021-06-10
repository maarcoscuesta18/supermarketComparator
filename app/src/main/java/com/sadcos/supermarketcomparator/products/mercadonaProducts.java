package com.sadcos.supermarketcomparator.products;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class mercadonaProducts {
    @SerializedName("id") private int id;
    @SerializedName("product_name") private String product_name;
    @SerializedName("link") private String link;
    @SerializedName("price") private Double price;
    private String qty;
    private String cartproduct_name;
    private Double cartprice;
    private String cartlink;
    private double totalprice;
    
    public mercadonaProducts(String product_name,String link, String price,String qty,String totalprice) {
        this.cartproduct_name = product_name;
        this.cartprice = Double.parseDouble(price);
        this.cartlink=link;
        this.qty=qty;
        this.totalprice = Double.parseDouble(totalprice);
    }

    public static Comparator<mercadonaProducts> ProductNameAZCommparator = new Comparator<mercadonaProducts>() {
        @Override
        public int compare(mercadonaProducts o1, mercadonaProducts o2) {
            return o1.getProduct_name().compareTo(o2.getProduct_name());
        }
    };
    public static Comparator<mercadonaProducts> ProductNameZACommparator = new Comparator<mercadonaProducts>() {
        @Override
        public int compare(mercadonaProducts o1, mercadonaProducts o2) {
            return o2.getProduct_name().compareTo(o1.getProduct_name());
        }
    };
    public static Comparator<mercadonaProducts> ProductPriceUpCommparator = new Comparator<mercadonaProducts>() {
        @Override
        public int compare(mercadonaProducts o1, mercadonaProducts o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    };
    public static Comparator<mercadonaProducts> ProductPriceDownCommparator = new Comparator<mercadonaProducts>() {
        @Override
        public int compare(mercadonaProducts o1, mercadonaProducts o2) {
            return Double.compare(o2.getPrice(), o1.getPrice());
        }
    };
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

    public void setQty(String qty) {
        this.qty = qty;
    }

    public double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    public String getCartlink() {
        return cartlink;
    }
}
