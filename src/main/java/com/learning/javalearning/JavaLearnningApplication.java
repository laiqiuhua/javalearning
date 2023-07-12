package com.learning.javalearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@RestController
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JavaLearnningApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaLearnningApplication.class, args);
//        System.out.println("web service start");
//        HelloWorldServiceImpl implementor = new HelloWorldServiceImpl();
//        String address = "http://localhost:8080/helloWorld";
//        Endpoint.publish(address, implementor);
//        System.out.println("web service started");
    }

    @GetMapping("/test")
    public String test(String test1, String test2) {
        System.out.println(test1+"--"+test2);
        Map m = new HashMap();
        WeakHashMap c;

        return test1+"---"+test2;
    }
}
