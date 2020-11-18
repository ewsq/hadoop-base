package com.decre.hadoop.hadoopbase.config;

import com.decre.hadoop.hadoopbase.service.HdfsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Decre
 * @date 2019/4/2 0002 0:00
 * @since 1.0.0
 * Descirption:
 */
@Configuration
public class HdfsConfig {

    @Value("${hdfs.defaultFS}")
    private String defaultHdfsUri;
    @Value("${hdfs.hadoop_home_dir}")
    private String hadoop_home_dir;

    @Bean
    public HdfsService getHbaseService() {
        if (!isBlank(hadoop_home_dir)) {
            System.setProperty("hadoop.home.dir", hadoop_home_dir);
        }
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration(false);
        conf.set("fs.defaultFS", defaultHdfsUri);
        return new HdfsService(conf, defaultHdfsUri);
    }


    public boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
