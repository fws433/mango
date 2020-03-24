package com.louis.mango.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.louis.mango"})
public class MangoApplication {

    public static void main(String[] args) {

        SpringApplication.run(MangoApplication.class, args);
    }

}
