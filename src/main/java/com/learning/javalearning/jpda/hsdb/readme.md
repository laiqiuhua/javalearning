执行完`jdb.sh`后打一个行号断点在输出end代码，具体行号即可如下
```
stop at com.learning.javalearning.jpda.hsdb.Main:8
```

2.1、启动
1、windows启动
````
java -cp .;%JAVA_HOME%/lib/sa-jdi.jar sun.jvm.hotspot.HSDB
````
注意：Linux和Solaris在Oracle/Sun JDK6就可以使用HSDB了，但Windows上要到Oracle JDK7才可以用HSDB

2、mac启动
````
echo $JAVA_HOME  # 查看存在java_home
````
执行启动命令
````
sudo java -cp $JAVA_HOME/lib/sa-jdi.jar sun.jvm.hotspot.HSDB
````
2.2、进程连接以及使用
2.2.1、基本连接
　　启动HSDB之后，把它连接到目标进程上。从菜单里选择File -> Attach to HotSpot process：
　　启动java程序，通过idea等工具打断点，暂停程序，可以通过JPS查看等查看进程ID
　　在弹出的对话框里输入刚才记下的pid，然后按OK。这会儿就连接到目标进程了：
　　
　　刚开始打开的窗口是Java Threads，里面有个线程列表。双击代表线程的行会打开一个Oop Inspector窗口显示HotSpot VM里记录线程的一些基本信息的C++对象的内容。 
　　不过这里我们更可能会关心的是线程栈的内存数据。先选择main线程，然后点击Java Threads窗口里的工具栏按钮从左数第2个可以打开Stack Memory窗口来显示main线程的栈：

　　

Stack Memory窗口的内容有三栏： 
　　　　左起第1栏是内存地址，请让我提醒一下本文里提到“内存地址”的地方都是指虚拟内存意义上的地址，不是“物理内存地址”，请不要弄混了这俩概念； 
　　　　第2栏是该地址上存的数据，以字宽为单位，本文例子中我是在Windows 7 64-bit上跑64位的JDK7的HotSpot VM，字宽是64位（8字节）； 
　　　　第3栏是对数据的注释，竖线表示范围，横线或斜线连接范围与注释文字。

2.2.2、console使用以及命令

现在让我们打开HSDB里的控制台，以便用命令来了解更多信息。 
　　在菜单里选择Windows -> Console
　　
　　然后会得到一个空白的Command Line窗口。在里面敲一下回车就会出现hsdb>提示符

可以通过help命令看看命令列表

复制代码
````
assert true | false
attach pid | exec core
buildreplayjars [ all | app | boot ]  | [ prefix ]
class name
classes
detach
dis address [length]
disassemble address
dumpcfg { -a | id }
dumpclass { address | name } [ directory ]
dumpcodecache
dumpheap [ file ]
dumpideal { -a | id }
dumpilt { -a | id }
dumpreplaydata { <address > | -a | <thread_id> }
echo [ true | false ]
examine [ address/count ] | [ address,address]
field [ type [ name fieldtype isStatic offset address ] ]
findpc address
flags [ flag | -nd ]
help [ command ]
history
inspect expression
intConstant [ name [ value ] ]
jdis address
jhisto
jseval script
jsload file
jstack [-v]
livenmethods
longConstant [ name [ value ] ]
mem address [ length ]
pmap
print expression
printall
printas type expression
printmdo [ -a | expression ]
printstatics [ type ]
pstack [-v]
quit
reattach
revptrs address
scanoops start end [ type ]
search [ heap | perm | rawheap | codecache | threads ] value
source filename
symbol address
symboldump
symboltable name
sysprops
thread { -a | id }
threads
tokenize ...
type [ type [ name super isOop isInteger isUnsigned size ] ]
universe　　　　#查看GC堆的地址范围和使用情况
verbose true | false
versioncheck [ true | false ]
vmstructsdump
whatis address
where { -a | id }
````
复制代码
基本命令说明：

1、universe命令来查看GC堆的地址范围和使用情况

复制代码
```
hsdb> universe
Heap Parameters:
Gen 0:   eden [0x0000000110400000,0x00000001104ab870,0x00000001106b0000) space capacity = 2818048, 24.93129996366279 used
from [0x00000001106b0000,0x00000001106b0000,0x0000000110700000) space capacity = 327680, 0.0 used
to   [0x0000000110700000,0x0000000110700000,0x0000000110750000) space capacity = 327680, 0.0 usedInvocations: 0

Gen 1:   old  [0x0000000110750000,0x0000000110750000,0x0000000110e00000) space capacity = 7012352, 0.0 usedInvocations: 0
```
复制代码
　　可以发现HotSpot在1.8的Java堆中，已经去除了Perm gen区，由youyoung gen和old gen组成。　　

　　2、scanoops 查看类型

Java代码里，执行到Test.fn()末尾为止应该创建了3个Test2的实例，它们必然在GC堆里，但都在哪里，可以用scanoops命令来看：
````
hsdb> scanoops 0x0000000110400000 0x0000000110e00000 com.lhx.cloud.javathread.MarkWord.Test2
0x00000001104a5ec0 com/lhx/cloud/javathread/MarkWord/Test2
0x00000001104a5ee8 com/lhx/cloud/javathread/MarkWord/Test2
0x00000001104a5ef8 com/lhx/cloud/javathread/MarkWord/Test2
````
　　scanoops接受两个必选参数和一个可选参数：必选参数是要扫描的地址范围，一个是起始地址一个是结束地址；可选参数用于指定要扫描什么类型的对象实例。实际扫描的时候会扫出指定的类型及其派生类的实例。
　　左边是对象的起始地址，右边是对象的实际类型

从它们所在的地址，对照前面universe命令看到的GC堆的地址范围，可以知道它们都在eden里。

3、whatis命令可以进一步知道它们都在eden之中分配给main线程的thread-local allocation buffer (TLAB)中
````
hsdb> whatis 0x00000001104a5ec0
````
Address 0x00000001104a5ec0: In thread-local allocation buffer for thread "main" (6659)  [0x000000011049db70,0x00000001104a5fd0,0x00000001104ab858,{0x00000001104ab870})
　　如果是用Parallel GC，其实稍微改造一下Serviceability Agent的Java部分就可以让whatis正确显示了，其实就是上文在启动时设置下GC方式
````
hsdb> whatis 0x000000076ab7a5b8
````
Address 0x000000076ab7a5b8: In unknown section of Java heap 
　　4、inspect命令来查看对象的内容：
````
hsdb> inspect 0x00000001104a5ec0
````
instance of Oop for com/lhx/cloud/javathread/MarkWord/Test2 @ 0x00000001104a5ec0 @ 0x00000001104a5ec0 (size = 16)
_mark: 1
_metadata._klass: InstanceKlass for com/lhx/cloud/javathread/MarkWord/Test2
　　　　为了方便klass地址显示，在mac上禁用掉指针压缩，即jdb -XX:-UseCompressedOops ,但此处没生效，可以使用下面的men查看

可见一个Test2的实例要16字节。因为Test2类没有任何Java层的实例字段，这里就没有任何Java实例字段可显示。

5、mem命令来看实际内存里的数据格式：
````
hsdb> mem 0x00000001104a5ec0 2
0x00000001104a5ec0: 0x0000000000000001
0x00000001104a5ec8: 0x0000000111201028 
````
mem命令接受的两个参数都必选，一个是起始地址，另一个是以字宽为单位的“长度”。我们知道一个Test2实例有16字节，所以给定长度为2来看。

mem详细含义：

0x00000001104a5ec0:  _mark:                        0x0000000000000001   
0x00000001104a5ec8:  _metadata._compressed_klass:  0x0000000111201028  
对于一个空的Test2的实例包含2个给VM用的隐含字段作为对象头，和0个Java字段。

对象头的第一个字段是mark word，记录该对象的GC状态、同步状态、identity hash code之类的多种信息

对象头的第二个字段是个类型信息指针，klass pointer。这里因为默认开启了压缩指针，所以本来应该是64位的指针存在了32位字段里。

最后还有4个字节是为了满足对齐需求而做的填充（padding）

6、Inspector工具界面

结合4、5可以通过，在菜单里选Tools -> Inspector，在地址里输入前面看到的klass地址：【5中第二项左侧的地址】　　　　

　　　　

Oop【原InstanceKlass】存着Java类型的名字、继承关系、实现接口关系，字段信息，方法信息，运行时常量池的指针，还有内嵌的虚方法表（vtable）、接口方法表（itable）和记录对象里什么位置上有GC会关心的指针（oop map）等等。

是给VM内部用的，并不直接暴露给Java层；InstanceKlass不是java.lang.Class的实例。

在HotSpot VM里，java.lang.Class的实例被称为“Java mirror”，意思是它是VM内部用的klass对象的“镜像”，把klass对象包装了一层来暴露给Java层使用。

在InstanceKlass里有个_java_mirror字段引用着它对应的Java mirror，而mirror里也有个隐藏字段指向其对应的InstanceKlass。所以当我们写obj.getClass()，在HotSpot VM里实际上经过了两层间接引用才能找到最终的Class对象：

obj->_klass->_java_mirror
　　　　在Oracle JDK7之前，Oracle/Sun JDK的HotSpot VM把Java类的静态变量存在InstanceKlass结构的末尾；从Oracle JDK7开始，为了配合PermGen移除的工作，Java类的静态变量被挪到Java mirror（Class对象）的末尾了。

在JDK7之前Java mirror存放在PermGen里，而从JDK7开始Java mirror默认也跟普通Java对象一样先从eden开始分配而不放在PermGen里。到JDK8则进一步彻底移除了PermGen，把诸如klass之类的元数据都挪到GC堆之外管理，而Java mirror的处理则跟JDK7一样。

7、revptrs 反向指针

HotSpot VM内部使用直接指针来实现Java引用。在64位环境中有可能启用“压缩指针”的功能把64位指针压缩到只用32位来存。压缩指针与非压缩指针直接有非常简单的1对1对应关系，前者可以看作后者的特例。

如果要找t1、t2、t3这三个变量，等同于找出存有指向上述3个Test2实例的地址的存储位置。

“反向指针”——如果a变量引用着b对象，那么从b对象出发去找a变量就是找一个“反向指针”。

※、查询第一个test2实例：
````
hsdb> revptrs 0x00000001104a5ec0
Computing reverse pointers...
Done.
null
Oop for java/lang/Class @ 0x00000001104a35c8
````
　　　　找到了一个包含指向Test2实例的指针，在一个java.lang.Class的实例里。

用whatis命令来看看这个Class对象在哪里：
````
hsdb> whatis 0x00000001104a35c8
Address 0x00000001104a35c8: In thread-local allocation buffer for thread "main" (6659)  [0x000000011049db70,0x00000001104a5fd0,0x00000001104ab858,{0x00000001104ab870})
````
可以看到这个Class对象也在eden里，具体来说在main线程的TLAB里。

这个Class对象是如何引用到Test2的实例的呢？再用inspect命令：
````
hsdb> inspect 0x00000001104a35c8
instance of Oop for java/lang/Class @ 0x00000001104a35c8 @ 0x00000001104a35c8 (size = 168)
````
````
<<Reverse pointers>>:
t1: Oop for com/lhx/cloud/javathread/MarkWord/Test2 @ 0x00000001104a5ec0 Oop for com/lhx/cloud/javathread/MarkWord/Test2 @ 0x00000001104a5ec0
hsdb> 
````
可以看到，这个Class对象里存着Test类的静态变量t1，指向着第一个Test2实例。 【这里没有对象头】

本来JVM规范里也没明确规定静态变量要存在哪里，通常认为它应该在概念中的“方法区”里；但现在在JDK7的HotSpot VM里它实质上也被放在Java heap里了。可以把这种特例看作是HotSpot VM把方法区的一部分数据也放在Java heap里了。
　　　　前面也已经提过，在JDK7之前的Oracle/Sun JDK里的HotSpot VM把静态变量存在InstanceKlass末尾，存在PermGen里。那个时候的PermGen更接近于完整的方法区一些。

※、查询第二个test2实例：

依次通过，revptrs，whatis，inspect命令查看

复制代码
````
hsdb> revptrs 0x00000001104a5ee8
Oop for com/lhx/cloud/javathread/MarkWord/Test @ 0x00000001104a5ed0
hsdb> whatis 0x00000001104a5ed0
Address 0x00000001104a5ed0: In thread-local allocation buffer for thread "main" (6659)  [0x000000011049db70,0x00000001104a5fd0,0x00000001104ab858,{0x00000001104ab870})
````
````
hsdb> inspect 0x00000001104a5ed0
instance of Oop for com/lhx/cloud/javathread/MarkWord/Test @ 0x00000001104a5ed0 @ 0x00000001104a5ed0 (size = 24)
<<Reverse pointers>>:
_mark: 1
_metadata._klass: InstanceKlass for com/lhx/cloud/javathread/MarkWord/Test
t2: Oop for com/lhx/cloud/javathread/MarkWord/Test2 @ 0x00000001104a5ee8 Oop for com/lhx/cloud/javathread/MarkWord/Test2 @ 0x00000001104a5ee8
````
复制代码
　　　　　　也在main线程的TLAB里，可以看到这个Test实例里有个成员字段t2，指向了第二个Test2实例。

※、查询第三个test2实例：
````
hsdb> revptrs 0x00000001104a5ef8
no live references to 0x00000001104a5ef8
````
查看线程栈

　　　　　　

