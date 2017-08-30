package com.android.example.measureme.Objects;

public class Customer {

    String customerName;
    String customerAddress;
    String customerPhone;
    String alternatePhone;

    public Customer() {
    }

    public Customer(String customerName, String customerAddress, String customerPhone, String alternatePhone) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPhone = customerPhone;
        this.alternatePhone = alternatePhone;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

}
