package com.louis.mango.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

/**
 * 启动器
 * @author Louis
 * @date Jan 15, 2019
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication


public class MangoMonitorApplication {

	public static void main(String[] args) {

		SpringApplication.run(MangoMonitorApplication.class, args);
	}
}