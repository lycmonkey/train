package com.lyc;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author lyc
 */
@MapperScan("com.lyc.member.mapper")
@SpringBootApplication
public class MemberApplication {
    private static final Logger LOG = LoggerFactory.getLogger(MemberApplication.class);
    public static void main(String[] args) {
//        SpringApplication.run(MemberApplication.class, args);
        SpringApplication application = new SpringApplication(MemberApplication.class);
        final ConfigurableEnvironment environment = application.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
        LOG.info("测试地址\thttp://127.0.0.1:{}{}/hello", environment.getProperty("server.port"),
                environment.getProperty("server.servlet.context-path"));
    }
}
