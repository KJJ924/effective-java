**ITEM 21**

# 인터페이스를 구현하는 쪽을 생각해 설계하라



## 서론

자바 8 이전에 인터페이스에 새로운 메서드를 추가하게 되면 메서드를 추가한 인터페이스를 구현하고있는 하위 클래스에서

대부분 컴파일 오류가 날 것이다.



하지만 자바 8이후 defalut 메서드를 추가할수 있게 되었다.

따라서 인터페이스내에 defalut 메서드를 추가해도 하위클래스에서는 컴파일오류는 발생하지 않는다.

그렇다면 defalut 메서드를 추가함으로써 발생하는 위험은 없을까?



## defalut 메서드를 추가함으로써 발생하는 위험

자바 8 이전의 모든 클래스는 인터페이스에 새로운 메서드가 추가 될일 없다고 가정하고 작성됐었다.

디폴트 메서드는 구현 클래스에 대해 아무것도 모른채 메서드가 추가가 되는 것이다.



이러한 경우 대부분 상황에서 잘 작동하지만

**모든 상황에서 불변식을 해치지 않는 디폴트 메서드를 작성하기란 어렵다**



예시를 보자.

자바 8 부터  Collection 인터페이스에 removeIf()  디폴트 메서드가 추가 되었다.

```java
default boolean removeIf(Predicate<? super E> filter) {
    Objects.requireNonNull(filter);
    boolean removed = false;
    final Iterator<E> each = iterator();
    while (each.hasNext()) {
        if (filter.test(each.next())) {
            each.remove();
            removed = true;
        }
    }
    return removed;
}
```

해당 메서드는 람다를 활용하기위해 추가된 메서드다.



해당 디폴트메서드는 모든 구현체와 잘 어울러지는 것은 아니다.



Collection 인터페이스를 데코레이터 패턴으로 사용한 

```java
org.apache.commons.collections4.collection.SynchronizedCollection
```

클래스를 살펴보자.([4.2 버전](https://mvnrepository.com/artifact/org.apache.commons/commons-collections4) 이후를 보셔야합니다. 최신버전에는 해당 이슈가 고쳐졌습니다.)



해당 클래스는 클라이언트가 제공한 객체로 락을 거는 능력을 추가해준다.



하지만 해당 클래스는 아직 자바 8에 추가된 removeIf() 디폴트 메서드 대해 재정의하고 있지 않다.

```java
public static void main(String[] args) {
    List<String> list = new ArrayList<>();
    list.add("test");
    list.add("test2");
    list.add("java Spring");
    list.add("java Spring Data JPA");

    SynchronizedCollection<String> sc = SynchronizedCollection.synchronizedCollection(list);

    Predicate<String> startJava =  s-> s.startsWith("java");
    sc.remove("test"); //SynchronizedCollection 가 재정의한 remove() 메서드 호출
    sc.removeIf(startJava::evaluate); // Collection 에 정의된 removeIf() 디폴트 메서드  호출

}
```

해당 코드의 문제점이 보이는가?



이 코드를 돌려보면 아무런 문제가 없는것처럼 보인다.

하지만 SynchronizedCollection 클래스는 말 그대로 멀티 쓰레드 환경에서 안전성을 보장해주는 클래스이다.

멀티쓰레드 환경에서 해당 코드를 실행하다보면 예기치 못한 결과를 만나볼수 있을 것이다.

(java 8 이후 추가된 removeIf 디폴트 메서드를 재정의 하지 않았기 때문에)





## 결론

디폴트 메서드는 (컴파일에 성공하더라도) 기존 구현체에 런타임 오류를 일으킬 수 있다.

-> 기존 인터페이스에 디폴트 메서드를 추가하는 일은 꼭 필요한 경우에서만 하자

