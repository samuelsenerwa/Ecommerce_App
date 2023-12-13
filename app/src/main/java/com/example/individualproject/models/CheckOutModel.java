package com.example.individualproject.models;

public class CheckOutModel {
    String name, address, paymentDetails, shippingMethod;

    public CheckOutModel() {
    }

    public CheckOutModel(String name, String address, String paymentDetails, String shippingMethod) {
        this.name = name;
        this.address = address;
        this.paymentDetails = paymentDetails;
        this.shippingMethod = shippingMethod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
}
