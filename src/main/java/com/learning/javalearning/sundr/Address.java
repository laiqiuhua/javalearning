package com.learning.javalearning.sundr;

import io.sundr.builder.annotations.Buildable;

/**
 * @author laiqiuhua
 * @date 2022/9/2
 **/
public class Address {
    private String street;
    private int number;
    private String zipCode;

    @Buildable
    public Address(String street, int number, String zipCode) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
    }

}
