package com.learning.javalearning.spi.impl;

import com.learning.javalearning.spi.SayHello;
import org.kohsuke.MetaInfServices;

/**
 * Created by eddy on 2017/2/11.
 */
@MetaInfServices
public class Say implements SayHello {

    public String say(String words) {
        return "reply->"+words;
    }

}