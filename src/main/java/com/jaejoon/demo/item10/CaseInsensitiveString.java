package com.jaejoon.demo.item10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class CaseInsensitiveString{
    private final String s;

    public CaseInsensitiveString(String s){
        this.s = Objects.requireNonNull(s);
    }
/*
    @Override public boolean equals(Object o){
        if(o instanceof CaseInsensitiveString)
            return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);

        if(o instanceof String)// 여기서는 문제 없지만 String 클래스가 해당클래스를 알고있을까??
            return s.equalsIgnoreCase((String) o); //한 방향으로만 작동하게됨
        return false;
    }
*/

    @Override public boolean equals(Object o){
        return o instanceof CaseInsensitiveString &&
                ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
    }

    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s ="polish";
        cis.equals(s); // ture
        System.out.println(cis.equals(s));
        s.equals(cis); // false
        System.out.println(s.equals(cis));

        List<CaseInsensitiveString> list = new ArrayList<>();
        list.add(cis);
        System.out.println(list.contains(s));
    }
}