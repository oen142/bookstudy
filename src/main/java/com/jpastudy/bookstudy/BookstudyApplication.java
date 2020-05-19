package com.jpastudy.bookstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstudyApplication.class, args);
    }

    /*@Bean
    Hibernate5Module hibernate5Module(){
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING , true);
        return hibernate5Module;
    }*/
}
