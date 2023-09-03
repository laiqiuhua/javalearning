package com.learning.javalearning.cxf;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
@Service
@WebService(endpointInterface= "com.learning.javalearning.cxf.HelloWorldService",serviceName="HelloWorld")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Resource
    private WebServiceContext context;

    public String sayHello() {
        MessageContext messageContext = context.getMessageContext();

        HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);

//或者通过如下方式获取，AbstractHTTPDestination.HTTP_REQUEST和MessageContext.SERVLET_REQUEST这两个常量值是不一样的，

//但它们对应于MessageContext对象中存储的都是HttpServletRequest对象

//HttpServletRequest request = (HttpServletRequest) messageContext.get(AbstractHTTPDestination.HTTP_REQUEST);

        HttpServletResponse response = (HttpServletResponse) messageContext.get(MessageContext.SERVLET_RESPONSE);
        response.addHeader("aaaa", "bbbbb");
        response.addHeader("bbbbb", "ccccc");
        response.addHeader("dddddd", "fffff");
        ServletContext servletContext = (ServletContext) messageContext.get(MessageContext.SERVLET_CONTEXT);
        return "iiiii";

    }

    @Override
    public User getUser(String id) {
        User user = new User();
        user.setUserId(111L);
        user.setEmail("ssdsd");
        user.setUsername("qiuhua");
        MessageContext messageContext = context.getMessageContext();
        HttpServletResponse response = (HttpServletResponse) messageContext.get(MessageContext.SERVLET_RESPONSE);
        response.addHeader("aaaa", "bbbbb");
        response.addHeader("bbbbb", "ccccc");
        response.addHeader("dddddd", "fffff");
        response.addCookie(new Cookie("testCookie", "cookieValue"));
        return user;
    }

}

