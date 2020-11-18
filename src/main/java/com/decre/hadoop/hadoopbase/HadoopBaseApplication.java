package com.decre.hadoop.hadoopbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HadoopBaseApplication {
    public static void main(String[] args) {
        //此配置仅在window调试环境中需要，由于hadoop-common需要找到winutils.exe,其实找不到也可以正常操作hadoop文件，但就是启动的时候会报“Could not locate executable null\bin\winutils.exe in the Hadoop binaries”
        System.setProperty("hadoop.home.dir", "D:\\hadoop-3.3.0");
        SpringApplication.run(HadoopBaseApplication.class, args);
    }
}
