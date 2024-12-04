package com.lyc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author lyc
 */
@SpringBootApplication
public class GateWayApplication {
    private static final Logger LOG = LoggerFactory.getLogger(GateWayApplication.class);
    public static void main(String[] args) {
//        SpringApplication.run(MemberApplication.class, args);
        SpringApplication application = new SpringApplication(GateWayApplication.class);
        final ConfigurableEnvironment environment = application.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("网关地址\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
//        LOG.info("测试地址\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }
}
