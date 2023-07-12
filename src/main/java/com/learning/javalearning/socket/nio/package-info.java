/**
 * Java NIO API 基于Channel、Buffer、Selector类，它们是操作系统低级 I/O 操作的适配器。
 *
 * Channel类表示与能够执行 I/O 操作（读或写）的实体（硬件设备、文件、套接字、软件组件等）的连接。
 *
 * 与单向流相比，通道是双向的。
 *
 * Buffer类是一个固定大小的数据容器，具有读取和写入数据的附加方法。所有Channel数据都通过Buffer处理，但从不直接处理：发送到Channel的所有数据都写入Buffer ，从Channel接收的所有数据都读入Buffer。
 *
 * 与面向字节的流相比，通道是面向块的。面向字节的 I/O 更简单，但对于某些 I/O 实体来说可能相当慢。面向块的 I/O 速度更快，但也更复杂。
 *
 * Selector类允许在一次调用中订阅来自多个已注册SelectableChannel对象的事件。当事件到达时，选择器对象将它们分派到相应的事件处理程序。
 *
 * @author laiqiuhua
 * @date 2023/7/11
 **/
package com.learning.javalearning.socket.nio;