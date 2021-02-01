package com.jaejoon.demo.item7;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache {
    public static void main(String[] args) {
        Object key = new Object();
        Object value = new Object();

        Map<Object,Object> cache =new HashMap<>();
        cache.put(key,value); // 맵 자체가 key 의 레퍼런스는 항상 들고 있음으로  key 는 gc의 대상이 될 수 없습니다.


        // a가 가르키고 있는  Strong ref 가 사라지면  a 는 GC 의 대상이 됩니다.
        WeakReference<String> a = new WeakReference<>("age");
        String s = a.get();
        s=null;

        Map<Object,Object> weakCache = new WeakHashMap<>();
        // put 을 하게 되면 내부적으로 key 를 WeakReference 로 한번 감싸게 됩니다.
        // 위에서 설명한 것처럼 weak ref 는 strong ref(key) 가 사라지게 되면 gc 의 대상이 됨으로 회수가 가능하게됩니다.
        weakCache.put(key,value);
    }
}
