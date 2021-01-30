package com.jaejoon.demo.item5.springtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RunApplication implements ApplicationRunner {

    @Autowired
    ApplicationContext context;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Warrior warrior = context.getBean(Warrior.class);
        warrior.attack();
    }
}
