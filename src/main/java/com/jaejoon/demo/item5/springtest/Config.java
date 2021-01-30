package com.jaejoon.demo.item5.springtest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Dagger dagger(){
        return new Dagger();
    }

    @Bean
    public Warrior warrior(){
        return new Warrior(dagger());
    }
}
