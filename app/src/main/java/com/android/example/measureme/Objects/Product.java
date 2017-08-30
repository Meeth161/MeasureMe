package com.android.example.measureme.Objects;

public class Product {

    String details;
    String description;
    String type;

    public Product() {
    }

    public Product(String type) {
        this.type = type;
        this.details = null;
        this.description = null;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }
}
