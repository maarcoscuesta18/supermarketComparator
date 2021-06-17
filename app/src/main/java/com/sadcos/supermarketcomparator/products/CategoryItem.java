package com.sadcos.supermarketcomparator.products;

import java.util.Comparator;

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

    public static Comparator<CategoryItem> ProductNameAZCommparator = new Comparator<CategoryItem>() {
        @Override
        public int compare(CategoryItem o1, CategoryItem o2) {
            return o1.getProduct_name().compareTo(o2.getProduct_name());
        }
    };
    public static Comparator<CategoryItem> ProductNameZACommparator = new Comparator<CategoryItem>() {
        @Override
        public int compare(CategoryItem o1, CategoryItem o2) {
            return o2.getProduct_name().compareTo(o1.getProduct_name());
        }
    };
    public static Comparator<CategoryItem> ProductPriceUpCommparator = new Comparator<CategoryItem>() {
        @Override
        public int compare(CategoryItem o1, CategoryItem o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    };
    public static Comparator<CategoryItem> ProductPriceDownCommparator = new Comparator<CategoryItem>() {
        @Override
        public int compare(CategoryItem o1, CategoryItem o2) {
            return Double.compare(o2.getPrice(), o1.getPrice());
        }
    };

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
