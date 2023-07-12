package com.learning.javalearning.spi;

import java.util.ServiceLoader;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class SPITest {

    public static void main(String[] args) {
        ServiceLoader<SayHello> services = ServiceLoader.load(SayHello.class);

        for (SayHello sayHello : services) {
            System.out.println(sayHello.say("SPI Test"));
        }
    }
}
