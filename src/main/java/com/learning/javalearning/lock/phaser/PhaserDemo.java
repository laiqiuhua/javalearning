package com.learning.javalearning.lock.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * Phaser这个单词是“移相器，相位器”的意思（好吧，笔者并不懂这是什么玩意，下方资料来自百度百科）。这个类是从JDK 1.7 中出现的。
 * 移相器（Phaser）能够对波的相位进行调整的一种装置。任何传输介质对在其中传导的波动都会引入相移，这是早期模拟移相器的原理；现代电子技术发展后利用A/D、D/A转换实现了数字移相，顾名思义，它是一种不连续的移相技术，但特点是移相精度高。 移相器在雷达、导弹姿态控制、加速器、通信、仪器仪表甚至于音乐等领域都有着广泛的应用
 * Phaser类有点复杂，这里只介绍一些基本的用法和知识点。详情可以查看JDK文档，文档里有这个类非常详尽的介绍。
 * 前面我们介绍了CyclicBarrier，可以发现它在构造方法里传入“任务总量”parties之后，就不能修改这个值了，并且每次调用await()方法也只能消耗一个parties计数。但Phaser可以动态地调整任务总量！
 * 名词解释：
 * party：对应一个线程，数量可以通过register或者构造参数传入;
 * arrive：对应一个party的状态，初始时是unarrived，当调用arriveAndAwaitAdvance()或者 arriveAndDeregister()进入arrive状态，可以通过getUnarrivedParties()获取当前未到达的数量;
 * register：注册一个party，每一阶段必须所有注册的party都到达才能进入下一阶段;
 * deRegister：减少一个party。
 * phase：阶段，当所有注册的party都arrive之后，将会调用Phaser的onAdvance()方法来判断是否要进入下一阶段。
 * Phaser终止的两种途径，Phaser维护的线程执行完毕或者onAdvance()返回true 此外Phaser还能维护一个树状的层级关系，构造的时候new Phaser(parentPhaser)，对于Task执行时间短的场景（竞争激烈），也就是说有大量的party, 那可以把每个Phaser的任务量设置较小，多个Phaser共同继承一个父Phaser。
 * Phasers with large numbers of parties that would otherwise experience heavy synchronization contention costs may instead be set up so that groups of sub-phasers share a common parent. This may greatly increase throughput even though it incurs greater per-operation overhead.
 * 翻译：如果有大量的party，那许多线程可能同步的竞争成本比较高。所以可以拆分成多个子Phaser共享一个共同的父Phaser。这可能会大大增加吞吐量，即使它会带来更多的每次操作开销。
 */
public class PhaserDemo {
    static class PreTaskThread implements Runnable {

        private String task;
        private Phaser phaser;

        public PreTaskThread(String task, Phaser phaser) {
            this.task = task;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            for (int i = 1; i < 4; i++) {
                try {
                    // 第二次关卡起不加载NPC，跳过
                    if (i >= 2 && "加载新手教程".equals(task)) {
                        continue;
                    }
                    Random random = new Random();
                    Thread.sleep(random.nextInt(1000));
                    System.out.println(String.format("关卡%d，需要加载%d个模块，当前模块【%s】",
                            i, phaser.getRegisteredParties(), task));

                    // 从第二个关卡起，不加载NPC
                    if (i == 1 && "加载新手教程".equals(task)) {
                        System.out.println("下次关卡移除加载【新手教程】模块");
                        phaser.arriveAndDeregister(); // 移除一个模块
                    } else {
                        phaser.arriveAndAwaitAdvance();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Phaser phaser = new Phaser(4) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println(String.format("第%d次关卡准备完成", phase + 1));
                return phase == 3 || registeredParties == 0;
            }
        };

        new Thread(new PreTaskThread("加载地图数据", phaser)).start();
        new Thread(new PreTaskThread("加载人物模型", phaser)).start();
        new Thread(new PreTaskThread("加载背景音乐", phaser)).start();
        new Thread(new PreTaskThread("加载新手教程", phaser)).start();
    }
}