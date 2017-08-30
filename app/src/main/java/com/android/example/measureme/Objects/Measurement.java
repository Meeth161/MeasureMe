package com.android.example.measureme.Objects;

import java.util.ArrayList;
import java.util.List;

public class Measurement {

    List<Product> productList = new ArrayList<>();
    Customer customer;
    public int isSynced = 0;
    String date;

    public Measurement() {
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
