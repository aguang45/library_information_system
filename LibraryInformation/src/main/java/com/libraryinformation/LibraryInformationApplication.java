package com.libraryinformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.libraryinformation.*"})
public class LibraryInformationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryInformationApplication.class, args);
    }

}
