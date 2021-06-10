package com.sadcos.supermarketcomparator.products;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class diaProducts {
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

    public diaProducts(String product_name, String link,String price,String price_per_kg,String qty,String totalprice) {
        this.cartproduct_name = product_name;
        this.cartprice = Double.parseDouble(price);
        this.cartlink=link;
        this.cartpriceperkg=price_per_kg;
        this.qty=qty;
        this.totalprice = Double.parseDouble(totalprice);
    }
    public static Comparator<diaProducts> ProductNameAZCommparator = new Comparator<diaProducts>() {
        @Override
        public int compare(diaProducts o1, diaProducts o2) {
            return o1.getProduct_name().compareTo(o2.getProduct_name());
        }
    };
    public static Comparator<diaProducts> ProductNameZACommparator = new Comparator<diaProducts>() {
        @Override
        public int compare(diaProducts o1, diaProducts o2) {
            return o2.getProduct_name().compareTo(o1.getProduct_name());
        }
    };
    public static Comparator<diaProducts> ProductPriceUpCommparator = new Comparator<diaProducts>() {
        @Override
        public int compare(diaProducts o1, diaProducts o2) {
            return Double.compare(Double.parseDouble(o1.getPrice()),Double.parseDouble(o2.getPrice()));
        }
    };
    public static Comparator<diaProducts> ProductPriceDownCommparator = new Comparator<diaProducts>() {
        @Override
        public int compare(diaProducts o1, diaProducts o2) {
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
}
