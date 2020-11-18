# Java API操作Hadoop可能会遇到的问题以及解决办法

## Could not locate Hadoop executable: xxx\bin\winutils.exe

```
Caused by: java.io.FileNotFoundException: Could not locate Hadoop executable: D:\hadoop-3.0.3\bin\winutils.exe -see https://wiki.apache.org/hadoop/WindowsProblems  at org.apache.hadoop.util.Shell.getQualifiedBinInner(Shell.java:620)  at org.apache.hadoop.util.Shell.getQualifiedBin(Shell.java:593)  at org.apache.hadoop.util.Shell.<clinit>(Shell.java:690)  at org.apache.hadoop.util.StringUtils.<clinit>(StringUtils.java:78)    ...
```

### **解决办法：**

下载WINUTILS.EXE，并放到Hadoop的bin目录，下载地址：https://github.com/steveloughran/winutils

## Caused by: java.io.FileNotFoundException: HADOOP_HOME and hadoop.home.dir are unset

```
java.io.FileNotFoundException: java.io.FileNotFoundException: HADOOP_HOME and hadoop.home.dir are unset. -see https://wiki.apache.org/hadoop/WindowsProblems  at org.apache.hadoop.util.Shell.fileNotFoundException(Shell.java:549) ~[hadoop-common-3.1.0.jar:na]  at org.apache.hadoop.util.Shell.getHadoopHomeDir(Shell.java:570) ~[hadoop-common-3.1.0.jar:na]  at org.apache.hadoop.util.Shell.getQualifiedBin(Shell.java:593) ~[hadoop-common-3.1.0.jar:na]  at org.apache.hadoop.util.Shell.<clinit>(Shell.java:690) ~[hadoop-common-3.1.0.jar:na]  ...
```

### **解决办法：**

这个问题一般是因为当前服务器没有安装Hadoop，因此首先需要下载并解压Hadoop安装包，其次需要配置系统环境变量：

控制面板 > 系统和安全 > 系统 > 高级系统变量 > 环境变量，添加一个环境变量，名字是：**HADOOP_HOME**，值是：**D:\hadoop-3.0.3**

注：修改完成之后需要重启eclipse或IDEA，然后通过以下命令检查是否生效：

```
public static void main(String[] args) {
//System.setProperty("HADOOP_USER_NAME","root");  
System.out.println(System.getenv("HADOOP_USER_NAME"));  
System.out.println(System.getenv("HADOOP_HOME"));
}
```



## Permission denied: user=zifangsky, access=WRITE, inode=”/”:root:supergroup:drwxr-xr-x

```
org.apache.hadoop.security.AccessControlException: Permission denied: user=zifangsky, access=WRITE, inode="/":root:supergroup:drwxr-xr-x  at org.apache.hadoop.hdfs.server.namenode.FSPermissionChecker.check(FSPermissionChecker.java:399)  at org.apache.hadoop.hdfs.server.namenode.FSPermissionChecker.checkPermission(FSPermissionChecker.java:255)  at org.apache.hadoop.hdfs.server.namenode.FSPermissionChecker.checkPermission(FSPermissionChecker.java:193)  at org.apache.hadoop.hdfs.server.namenode.FSDirectory.checkPermission(FSDirectory.java:1853)  at org.apache.hadoop.hdfs.server.namenode.FSDirectory.checkPermission(FSDirectory.java:1837)  at org.apache.hadoop.hdfs.server.namenode.FSDirectory.checkAncestorAccess(FSDirectory.java:1796)  ...
```



### 解决办法：

这个问题的原因是当前运行系统用户跟HDFS上面的文件系统的用户/用户组不同，因此没有权限执行创建、删除等操作。

**方法一：**解决方法跟上面一样，通过添加环境变量，人为设置当前用户为HDFS的启动用户：

添加一个环境变量，名字是：**HADOOP_USER_NAME**，值是：**root**

**方法二：**使用HDFS的命令行接口修改相应目录的权限（不推荐）：

```
hadoop fs -chmod 777 /
```

注：后面的/是当前要操作的HDFS路径。