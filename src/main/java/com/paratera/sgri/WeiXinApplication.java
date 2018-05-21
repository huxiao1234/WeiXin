package com.paratera.sgri;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeiXinApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(WeiXinApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.err.println("启动成功！！！！！！");
    }
}
