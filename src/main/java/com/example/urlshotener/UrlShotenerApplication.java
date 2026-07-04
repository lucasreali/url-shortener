package com.example.urlshotener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class UrlShotenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlShotenerApplication.class, args);
    } 

}
