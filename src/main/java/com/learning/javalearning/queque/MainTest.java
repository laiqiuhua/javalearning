package com.learning.javalearning.queque;

/**
 * @Author qhlai
 * @email qhlai@gizwits.com
 */
public class MainTest {

    public static void main(String[] args) throws InterruptedException {

        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e)-> {
            System.out.println("thread=>"+t+"  e=>"+ e);
//            System.exit(0);

            Runtime.getRuntime().exit(0);
        });
        int num = 0;
        while (num < 10000) {
            Thread t = new Thread(() -> {
//                throw new ThreadDeath();
                try {

                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            num++;
        }
    }

}
