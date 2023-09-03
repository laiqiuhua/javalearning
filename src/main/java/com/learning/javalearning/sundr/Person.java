package com.learning.javalearning.sundr;

import org.apache.tomcat.util.http.parser.EntityTag;

public class Person extends EntityTag {
  private final String firstName;
  private final  String lastName;
  
  public Person(String firstName, String lastName) {
     this.firstName = firstName;
     this.lastName = lastName;
  }
  
  public String getFirstName() {
     return this.firstName;
  }
  
  public String getLastName() {
     return this.lastName;
  }

  //More code ...
}