**ITEM 18**

# 상속보다는 컴포지션을 사용하라.



## 서론

메서드 호출과 달리 상속은 캡슐화를 깨트린다.

-> 상위클래스의 구현에 따라 하위클래스의 동작에 이상이 생길수 있다. 당장 문제가 발생하지 않아도 상위클래스의 릴리즈 마다 내부구현이 변함에 따라 오작동 할 수 있다.





## 의도하지 않은 오류 예제.

```java
public class InstrumentedHashSet<E> extends HashSet<E> {

    private int count;

    public InstrumentedHashSet(){
    }

    public InstrumentedHashSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    @Override
    public boolean add(E e) {
        count++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        count+=c.size();
        return super.addAll(c);
    }

    public int getCount() {
        return count;
    }
}
```



해당 예제는 HashSet이 생성된 이후 얼마나 요소가 더해졋는지 알수있는 클래스이다.

해당 예제를 테스트코드로 작성해보자.



**테스트코드**

```java
@Test
@DisplayName("일반 add 메서드를 호출했을때는 정상적으로 작동한다.")
void addValue(){
    InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
    s.add("string");
    s.add("String 1");
    s.add("String 2");
    assertThat(s.getCount()).isEqualTo(3);
}

@Test
@DisplayName("addAll 메서드를 호출하면 내부적으로 add을 또 호출하여 중복으로 count 가 된다.")
void addAllCount(){
    InstrumentedHashSet<String> s  = new InstrumentedHashSet<>();

    s.addAll(List.of("String","String2","String3"));

    assertThat(s.getCount()).isNotEqualTo(3); //result value =6 expect = 3

}
```

다음과 같이 addAll 메서드를 사용하면 의도했던거와 다르게 count 값은 6이 된다.



HashSet 의 addAll() 메서드는 내부적으로 add() 메서드를 사용하기 때문에 중복으로 count 가 된다.



따라서 이러한 문제를 해결하기위해 ? 



메서드를 재정의하지 않고 새로 만드는 것도 올바른 해답이 아니다.

단지 그 순간 오류를 회피하는것이지 완전한 오류 해결이 아니기 때문이다.

왜냐하면 상위클래스가 릴리즈 되어서 새로 정의된 메서드가 추가되었는데

우연치 않게 하위클래스의 메서드 시그니처와 중복된다면 또 오류를 발생시킨다.



따라서 완벽한 is-a 관계가 아니면 상속보단 컴포지션을 이용하자.





## 컴포지션



**기존 클래스(HashSet) 을 확장하지 않고 기존 클래스의 인스턴스를 참조하게하는 컴포지션 를 만들자.**



```java
public class ForwardingSet<E> implements Set<E> {

    private final Set<E> set; // 기존 클래스의 인스턴스를 참조하게 만든다.

    public ForwardingSet(Set<E> set){
        this.set = set; // 해당 클래스를 생성할때 인스턴스를 주입받아야한다.
    }

   //..나머지 메서드 생략

    @Override
    public boolean add(E e) {
        return set.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return set.addAll(c);
    }
}
```

해당 방법과 같이 설계를 한다면 이제 기존클래스의 내부 구현방식에 영향을 받지 않는다.



자 이제 새로 만들어진 ForwardingSet 클래스을 확장한 **래퍼 클래스**를 만들어보자

```java
public class CountingSet<E> extends ForwardingSet<E> {
    private int count;

    public CountingSet(Set<E> set) {
        super(set);
    }

    @Override
    public boolean add(E e) {
        count++; // 기능 추가
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        count+=c.size(); // 기능추가
        return super.addAll(c);
    }

    public int getCount() {
        return count;
    }
}

```

해당 래퍼클래스는 다른 Set 에 계측 기능을 덧씌운다는 뜻에서 **데코레이터 패턴** 이라고 한다.



Test Code 

```java
@Test
@DisplayName("컴포지션 이용하여 기존 발생한 문제를 해결한다.")
void addAllCount_success(){
    CountingSet<String> s  = new CountingSet<>(new HashSet<>());

    s.addAll(List.of("String","String2","String3"));

    assertThat(s.getCount()).isEqualTo(3);

}

@Test
@DisplayName("Set 인터페이스 의 구현체에 대해서 count 기능을 이용할수 있다")
void test(){
    CountingSet<String> s = new CountingSet<>(new HashSet<>());
    CountingSet<String> t = new CountingSet<>(new TreeSet<>());

    s.addAll(List.of("String","String2","String3"));
    t.addAll(List.of("String","String2","String3"));

    assertThat(s.getCount()).isEqualTo(3);
    assertThat(t.getCount()).isEqualTo(3);
 }
```





래퍼클래스의 단점

-> 래퍼클래스가 콜백 프레임워크와는 어울리지 않는다는 단점이 있다.





## 요약

1. 상속은 상위 클래스와 하위클래스가 순수한 is-a 관계 일때만 사용
2. 상속은 상위클래스의 취약점을 그대로 물려받기 때문에 상속의 취약점을 피하기위해선 컴포지션 전달을 이용하자.