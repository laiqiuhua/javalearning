package com.learning.javalearning.privilege;


import com.learning.javalearning.security.FileUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ReflectPermission;
import java.security.AccessControlException;
import java.security.Permission;


public class DemoDoPrivilege { 
 
   public static void main(String[] args) { 
       System.out.println("***************************************"); 
       System.out.println("I will show AccessControl functionality..."); 
 
       System.out.println("Preparation step : turn on system permission check..."); 
       // 打开系统安全权限检查开关
       System.setProperty("java.security.policy", "/Users/laiqiuhua/IdeaProjects/javalearnning/MyPolicy.txt");
       SecurityManager security = new SecurityManager();
//       if (security != null) {
//           FilePermission perm = new FilePermission("/Users/laiqiuhua/IdeaProjects/javalearnning/bin/*", "write");
//           security.checkPermission(perm);
//       }

       System.out.println(System.getProperty("java.security.policy"));
       System.setSecurityManager(security);
       System.out.println();
 
       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); 
       System.out.println(" Create a new file named temp1.txt via privileged action ...");
       // 用特权访问方式在工程 A 执行文件路径中创建 temp1.txt 文件
       FileUtil.doPrivilegedAction("temp1.txt");
       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); 
       System.out.println(); 
 
       System.out.println("/////////////////////////////////////////"); 
       System.out.println("Create a new file named temp2.txt via File ..."); 
       try { 
           // 用普通文件操作方式在工程 A 执行文件路径中创建 temp2.txt 文件
           File fs = new File("/Users/laiqiuhua/IdeaProjects/javalearnning/bin/temp2.txt");
           fs.createNewFile();
       } catch (IOException e) {
           e.printStackTrace();
       } catch (AccessControlException e1) {
           e1.printStackTrace(); 
       } 
       System.out.println("/////////////////////////////////////////"); 
       System.out.println(); 
 
       System.out.println("-----------------------------------------"); 
       System.out.println("create a new file named temp3.txt via FileUtil ..."); 
       // 直接调用普通接口方式在工程 A 执行文件路径中创建 temp3.txt 文件
       FileUtil.makeFile("temp3.txt"); 
       System.out.println("-----------------------------------------"); 
       System.out.println(); 
 
       System.out.println("***************************************"); 
   }

    private static boolean canAccessPrivateMethods() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }
        return true;
    }

//    public void checkPermission(Permission perm) {
//        if (perm instanceof ReflectPermission) {
//            ReflectPermission refl = (ReflectPermission) perm;
//            if (refl.getName().equals("suppressAccessChecks")) {
//                Class[] callers = getClassContext();
//                int expectedBaseDepth = 4;
//                if (callers.length >= expectedBaseDepth && callers[expectedBaseDepth].getCanonicalName() != null &&
//                        callers[expectedBaseDepth].getCanonicalName().startsWith("org.vinsert.api.script.")) {
//                    throw new SecurityException("vInsert is not allowed to use reflection!");
//                }
//            }
//        }
//    }


    public void checkPermission1(Permission perm) {
        // don't throw an error, so reset can reset security manager.
        if (perm instanceof ReflectPermission && "suppressAccessChecks".equals(perm.getName())) {
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                if (ste.getClassName().startsWith("org.eclipse.persistence.testing.tests.security") && "test".equals(ste.getMethodName())) {
                    throw new SecurityException("Dummy SecurityException test");
                }
            }
        }
    }




}