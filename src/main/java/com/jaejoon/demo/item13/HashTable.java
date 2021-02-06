package com.jaejoon.demo.item13;

public class HashTable {
    private Entry[] bucket;
    static class Entry{
        final Object key;
        Object value;
        Entry next;

        public Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        Entry deepCopy(){
            return new Entry(key,value,next ==null ? null: next.deepCopy());
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            HashTable result = (HashTable) super.clone(); // 안에있는 엔트리는 같은 참조값을 바라보고있음.
            return result;
        }
    }
}
