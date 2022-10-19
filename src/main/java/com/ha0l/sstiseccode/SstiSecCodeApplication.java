package com.ha0l.sstiseccode;

import com.ha0l.sstiseccode.controller.VelocityController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ha0l
 * @date 2022-09-08
 */
@RestController
@SpringBootApplication
//@ComponentScan(value = com.ha0l.sstiseccode.controller.VelocityController)
@ComponentScan(value = {"com.ha0l.sstiseccode.controller"})
public class SstiSecCodeApplication {


    public static void main(String[] args) {
        SpringApplication.run(SstiSecCodeApplication.class, args);
    }

}
