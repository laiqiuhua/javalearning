package com.learning.javalearning.cxf;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
@WebService
public interface HelloWorldService {

    @WebMethod
    String sayHello();

    @WebMethod
    User getUser(String id);
}
