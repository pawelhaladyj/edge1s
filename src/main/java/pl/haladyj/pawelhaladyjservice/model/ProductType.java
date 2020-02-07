package pl.haladyj.pawelhaladyjservice.model;

import java.math.BigInteger;

public enum ProductType {

    MALE("5"),
    FEMALE("5"),
    KID("10");

    private String discount;

    ProductType(String discount) {
        this.discount = discount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
