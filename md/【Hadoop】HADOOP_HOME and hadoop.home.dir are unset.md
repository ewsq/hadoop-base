## 【Hadoop】HADOOP_HOME and hadoop.home.dir are unset.

# 原因

在Windows上使用Idea操作Hadoop报错，因为windows没有安装且配置Hadoop。

# 解决

在Windows上解压Hadoop压缩包，并完成环境变量的配置。

> 在path中添加Hadoop的bin目录

但仍然报错，根据[Hadoop官网的wiki](https://wiki.apache.org/hadoop/WindowsProblems)可知，到[github](https://github.com/steveloughran/winutils)上下载对应的文件复制到对应的目录即可。
![在这里插入图片描述](.\images\001.png)
将下载好的WINUTILS.EXE复制到bin目录下，将 HADOOP.DLL复制到System32目录下。