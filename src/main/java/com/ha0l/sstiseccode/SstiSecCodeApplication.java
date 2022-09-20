package com.ha0l.sstiseccode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ha0l
 * @date 2022-09-08
 */
@RestController
@SpringBootApplication
@ComponentScan(basePackageClasses = SstiSecCodeApplication.class)
public class SstiSecCodeApplication {

//    @GetMapping("/")
//    public String base() {
//        return "System is Running!";
//    }

    public static void main(String[] args) {
        SpringApplication.run(SstiSecCodeApplication.class, args);
    }

}
