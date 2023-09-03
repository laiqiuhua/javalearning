package com.learning.javalearning.sundr;


import io.sundr.builder.annotations.Buildable;

/**
 * @author laiqiuhua
 * @date 2022/9/2
 **/
public class Artist {
    private String firstName;
    private String lastName;
    @Buildable
    public Artist(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }



}

