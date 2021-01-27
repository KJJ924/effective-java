package com.jaejoon.demo.item3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class ClientMain {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        PublicStaticFinalSingleton instance = PublicStaticFinalSingleton.INSTANCE;

        Constructor<PublicStaticFinalSingleton> constructor = PublicStaticFinalSingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        PublicStaticFinalSingleton newInstance = constructor.newInstance();
        System.out.println(instance==newInstance);


        //==========================================

        StaticFactoryMethodSingleton staticFactoryMethodSingeTone = StaticFactoryMethodSingleton.getInstance();
        StaticFactoryMethodSingleton staticFactoryMethodSingleton2 = StaticFactoryMethodSingleton.getInstance();


        Supplier<StaticFactoryMethodSingleton> supplier =  StaticFactoryMethodSingleton::getInstance;
        StaticFactoryMethodSingleton staticFactoryMethodSingleton3 = supplier.get();

        System.out.println(staticFactoryMethodSingeTone == staticFactoryMethodSingleton3);
        System.out.println(staticFactoryMethodSingleton2==staticFactoryMethodSingleton3);


    }
}
