# [源码追踪，解决Could not locate executable null\bin\winutils.exe in the Hadoop binaries.问题](https://www.cnblogs.com/tijun/p/7567664.html)

**在windows系统使用java程序访问hadoop程序（使用hadoop-common的jar包），会出现这个异常，但不影响现有程序运行。**



总归是一个异常，老是爆红，看着心烦，下面是异常信息

![img](.\images\1238778-20170921110752853-1052944539.png)



让我们源码追踪一下，看看到底是什么原因导致，点击第一行爆红的异常信息提示，就是（shell.java:355）

![img](.\images\1238778-20170921111004212-2026407003.png)

看到我的截图提示，大概了解什么原因了。发现HADOOP_HOME_DIR为null,右边outline里面找到一个私有静态变量叫HADOOP_HOME_DIR，

点击看看是如何赋值的，

![img](.\images\1238778-20170921111320368-1033040190.png)

HADOOP_HOME_DIR他的初始化应该是调用了一个checkHadoopHome()的方法，找到这个方法的实现

![img](.\images\1238778-20170921111414400-1577793529.png)

在checkHadoopHome()这个方法实现里面，真相已经大白于天下了，方法里面需要找到一些系统配置信息，而我们程序里或系统里没有给到。



这就针对的解决就可以了。而且根据checkHadoopHome()这个方法的实现过程，我们可以得出两种解决这个问题的方法。



两种方法的前提都是需要在本地解压hadoop的安装包的。



https://github.com/steveloughran/winutils

![](.\images\2020-11-18 09 46 44.png)



也可以下载：

https://github.com/srccodes/hadoop-common-2.2.0-bin

我这里的解压的是hadoop-common-2.2.0-bin-master.zip。

![img](.\images\1238778-20170921113520321-1657344488.png)

 

这里主要的还是需要在bin目录下找winutils.exe。

第一种方法，

我们看到源码292行，他是先从System.getProperty("hadoop.home.dir");这里获得信息，

从他的注释信息看

```
 // first check the Dflag hadoop.home.dir with JVM scope
```

首先检查的应该是java虚拟机里面的这个hadoop.home.dir，

那我们就可以直接在程序开始执行出手动添加

```
System.setProperty("hadoop.home.dir", "D:\\Programe\\hadoop-common-2.2.0-bin-master");
```

注意：这段代码要加到程序的开头第一行

运行程序，刚刚的异常爆红就不会出现了。

第二种方法，

从源码296行的System.getenv("HADOOP_HOME");，不难看出，如果在虚拟机里面没有找到hadoop.home.dir

那就从这行代码获得，从注释信息来看

```
// fall back to the system/user-global env variable
```

那就是从系统的用户全局变量里面找HADOOP_HOME这个配置。

那我们直接在Windows的系统变量里面配置HADOOP_HOME，然后在PATH里面配置HADOOP_HOME/bin

这种方法需要重启计算机，运行程序，刚刚的异常爆红就不会出现了。

至此，两种方法任选其一，都是可以解决问题的。