package com.sadcos.supermarketcomparator.products;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class stringPriceProducts {
    @SerializedName("id") private int id;
    @SerializedName("product_name") private String product_name;
    @SerializedName("link") private String link;
    @SerializedName("price") private String price;
    @SerializedName("price_per_kg") private String price_per_kg;
    private String qty;
    private String cartproduct_name;
    private String cartpriceperkg;
    private Double cartprice;
    private Double totalprice;
    private String cartlink;

    public stringPriceProducts(String product_name, String link, String price, String price_per_kg, String qty, String totalprice) {
        this.cartproduct_name = product_name;
        this.cartprice = Double.parseDouble(price);
        this.cartlink=link;
        this.cartpriceperkg=price_per_kg;
        this.qty=qty;
        this.totalprice = Double.parseDouble(totalprice);
    }
    public static Comparator<stringPriceProducts> ProductNameAZCommparator = new Comparator<stringPriceProducts>() {
        @Override
        public int compare(stringPriceProducts o1, stringPriceProducts o2) {
            return o1.getProduct_name().compareTo(o2.getProduct_name());
        }
    };
    public static Comparator<stringPriceProducts> ProductNameZACommparator = new Comparator<stringPriceProducts>() {
        @Override
        public int compare(stringPriceProducts o1, stringPriceProducts o2) {
            return o2.getProduct_name().compareTo(o1.getProduct_name());
        }
    };
    public static Comparator<stringPriceProducts> ProductPriceUpCommparator = new Comparator<stringPriceProducts>() {
        @Override
        public int compare(stringPriceProducts o1, stringPriceProducts o2) {
            return Double.compare(Double.parseDouble(o1.getPrice()),Double.parseDouble(o2.getPrice()));
        }
    };
    public static Comparator<stringPriceProducts> ProductPriceDownCommparator = new Comparator<stringPriceProducts>() {
        @Override
        public int compare(stringPriceProducts o1, stringPriceProducts o2) {
            return Double.compare(Double.parseDouble(o2.getPrice()),Double.parseDouble(o1.getPrice()));
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

    public String getPrice() {
        return price;
    }

    public String getPrice_per_kg() {
        return price_per_kg;
    }



    public String getQty() {
        return qty;
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

    public String getCartproduct_name() {
        return cartproduct_name;
    }
    public Double getCartprice() {
        return cartprice;
    }

    public String getCartpriceperkg() {
        return cartpriceperkg;
    }
    public void setCartpriceperkg(String cartpriceperkg) {
        this.cartpriceperkg = cartpriceperkg;
    }

    public String getCartlink() {
        return cartlink;
    }
    public void setCartlink(String cartlink) {
        this.cartlink = cartlink;
    }

    @Override
    public String toString() {
        return "stringPriceProducts{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", link='" + link + '\'' +
                ", price='" + price + '\'' +
                ", price_per_kg='" + price_per_kg + '\'' +
                ", qty='" + qty + '\'' +
                ", cartproduct_name='" + cartproduct_name + '\'' +
                ", cartpriceperkg='" + cartpriceperkg + '\'' +
                ", cartprice=" + cartprice +
                ", totalprice=" + totalprice +
                ", cartlink='" + cartlink + '\'' +
                '}';
    }
}
