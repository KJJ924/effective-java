package com.jaejoon.demo.item20;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    void process(){

        Phone iPhone = new IPhone();
        Phone galaxyPhone = new GalaxyPhone();

        galaxyPhone.process();
    }

    @Test
    void LGPhone(){
        Phone LGPhone = new AbstractPhone() {
            @Override
            public void greeting() {
                System.out.println("hello LG");
            }
        };
        LGPhone.process();
    }
}