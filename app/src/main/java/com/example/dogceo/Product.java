package com.example.dogceo;

import android.util.Log;

import java.util.ArrayList;

public class Product {
    private String productName;
    private String imageID;

    public Product() {
    }

    public Product(String imageID, String productName) {
        this.imageID = imageID;
        this.productName = productName;
    }


    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public static ArrayList<Product> getData(ArrayList<String> picturedataList, ArrayList<String> namedataList) {
        ArrayList<Product> productList = new ArrayList<Product>();

        for (int i = 0; i < picturedataList.size(); i++) {
            productList.add(new Product(namedataList.get(i),picturedataList.get(i)));

        }
        for (int i = 0; i < productList.size(); i++) {
        }
        return productList;
    }
}

