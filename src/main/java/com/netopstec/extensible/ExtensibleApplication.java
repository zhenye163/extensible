package com.netopstec.extensible;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy
@EnableTransactionManagement
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAsync
public class ExtensibleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtensibleApplication.class, args);
	}
}
