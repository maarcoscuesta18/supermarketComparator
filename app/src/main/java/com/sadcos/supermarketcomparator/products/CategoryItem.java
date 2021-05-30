package com.sadcos.supermarketcomparator.products;

public class CategoryItem {
    private String product_name;
    private double price;
    private String price_per_kg;
    private String link;
    private String supermarket;
    public CategoryItem(String product_name,String link, double price, String price_per_kg,String supermarket) {
        this.product_name = product_name;
        this.price = price;
        this.price_per_kg = price_per_kg;
        this.link = link;
        this.supermarket = supermarket;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPrice_per_kg() {
        return price_per_kg;
    }

    public void setPrice_per_kg(String price_per_kg) {
        this.price_per_kg = price_per_kg;
    }

    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    public String getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(String supermarket) {
        this.supermarket = supermarket;
    }
}
