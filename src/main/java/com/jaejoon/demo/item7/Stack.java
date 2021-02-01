package com.jaejoon.demo.item7;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack {

    private static final int DEFAULT_INITIAL_CAPACITY=10;
    private Object[] element;
    private int size=0;

    public Stack(Object[] element) {
        checkSize();
        this.element = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        element[size++] = e;
    }

    public Object pop(){
        if(size==0){
            throw new EmptyStackException();
        }
        // 자원해제를 하지 않았기 때문에 element 에는 아직 자원이 존재한다.
        // 따라서 다음과 pop1 같이 코드를 변경해야한다.
        return element[--size];  //해당부분에서 메모리누수 가 발생한다.
    }

    public Object pop1(){
        if(size==0){
            throw new EmptyStackException();
        }
        Object value = element[--size];
        element[size] =null;  // 참조하고 있는 레퍼런스를 해제 하면 GC 의 대상이 됨으로 메모리 누수가 없다.
        return value;
    }

    public void checkSize(){
        if(element.length == size){
           this.element=Arrays.copyOf(element,size*2+1);
        }
    }
}

