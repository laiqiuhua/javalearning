package com.learning.javalearning.privilege;

import okhttp3.OkHttpClient;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.SecurityPermission;

public class TestingDoPrivileged {

    static SecurityPermission XYZ = new SecurityPermission("xyz");
    static SecurityPermission ABC = new SecurityPermission("abc");
    static SecurityPermission SET_POLICY = new SecurityPermission("setPolicy");


    public static void main(String[] args) throws UnknownHostException {
//        setDummySecurityManager();
//        frameA();
//        System.out.println(Arrays.toString(allAddressesByName("127.0.0.1")));
        Unsafe unsafe = getUnsafe();
        System.out.println(unsafe.addressSize());
//        System.out.println(Reflection.getCallerClass());
//        System.out.println(ClassLoader.getSystemClassLoader());

//        DatagramSocket datagramSocket = new DatagramSocket();

        OkHttpClient okHttpClient = new OkHttpClient();

    }

    /**
     * Returns a sun.misc.Unsafe. Suitable for use in a 3rd party package. Replace with a simple call
     * to Unsafe.getUnsafe when integrating into a jdk.
     *
     * @return a sun.misc.Unsafe
     */
    @CallerSensitive
    private static sun.misc.Unsafe getUnsafe() {
        try {
            return sun.misc.Unsafe.getUnsafe();
        } catch (SecurityException tryReflectionInstead) {
        }
        try {
            System.out.println(Reflection.getCallerClass());
            return java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedExceptionAction<sun.misc.Unsafe>() {
                        public sun.misc.Unsafe run() throws Exception {
                            Class<sun.misc.Unsafe> k = sun.misc.Unsafe.class;
                            for (java.lang.reflect.Field f : k.getDeclaredFields()) {
                                f.setAccessible(true);
                                Object x = f.get(null);
                                if (k.isInstance(x)) return k.cast(x);
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    });
        } catch (java.security.PrivilegedActionException e) {
            throw new RuntimeException("Could not initialize intrinsics", e.getCause());
        }
    }


    public static InetAddress[] allAddressesByName(final String hostname) throws UnknownHostException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<InetAddress[]>() {
                @Override
                public InetAddress[] run() throws UnknownHostException {
                    return InetAddress.getAllByName(hostname);
                }
            });
        } catch (PrivilegedActionException e) {
            throw (UnknownHostException) e.getCause();
        }
    }


    private static void frameA() {
        //set the permission XYZ
        setPolicy(XYZ,SET_POLICY);

        //Here the code/resource frameA  has access to XYZ permission
        //Now call frameB which needs ABC permission for the code to execute.
        frameB();

    }
    private static void frameB() {
        //this will pass as this code has the permission XYZ because the policy is set in frameA with XYZ permission
        System.getSecurityManager().checkPermission(XYZ);
        try {
            // this will fail as this code does not have permission for abc
            System.getSecurityManager().checkPermission(ABC);
        } catch (AccessControlException e) {
            System.out.println("passed");
            //pass
        }
        //As the caller of frameB which is frameA has the permission to XYZ only, the following code should fail,
        // right? But it is passing.
        AccessController.doPrivileged((PrivilegedAction) () -> {
            frameC();
            return null;
        });
    }
    private static void frameC() {
        System.getSecurityManager().checkPermission(ABC);
    }

    private static void setDummySecurityManager() {
        setPolicy(SET_POLICY);
        SecurityManager sm = new SecurityManager();
        System.setSecurityManager(sm);
    }
    private static void setPolicy(Permission... perms) {
        Policy.setPolicy(new Policy() {
            @Override
            public PermissionCollection getPermissions(CodeSource codesource) {
                PermissionCollection pc = new Permissions();
                for (Permission pm : perms)
                    pc.add(pm);
                return pc;
            }
        });
    }
}