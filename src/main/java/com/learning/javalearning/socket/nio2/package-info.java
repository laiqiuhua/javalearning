/**
 * Java NIO2 API 基于异步通道（AsynchronousServerSocketChannel、AsynchronousSocketChannel等），支持异步 I/O 操作（连接、读取或写入、错误处理）。
 *
 * 异步通道提供两种机制来控制异步 I/O 操作。第一种机制是返回java.util.concurrent.Future对象，该对象对挂起操作进行建模，可用于查询状态并获取结果。第二种机制是向操作传递一个java.nio.channels.CompletionHandler对象，该对象定义在操作完成或失败后执行的处理程序方法。为两种机制提供的 API 是等效的。
 *
 * 异步通道提供了独立于平台执行异步操作的标准方法。然而，Java sockets API 可以利用操作系统本机异步功能的程度将取决于对该平台的支持。
 * @author laiqiuhua
 * @date 2023/7/11
 **/
package com.learning.javalearning.socket.nio2;