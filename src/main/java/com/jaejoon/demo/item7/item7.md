**ITEM 7**

# 다 쓴 객체 참조를 해제하라



자바는 참조하고 있지 않는 객체를 가비지 컬렉터를 통해 모두 회수한다.

가비지 컬렉터의 대상여부는 reachable 인지 unreachable 인지 확인하여 gc 의 여부를 결정하기 때문에

이러한 부분에서 메모리 관리를 전혀 안해도 된다는 것은 착각이다.



그러면 메모리 누수를 발생시키는 3가지의 주범을 알아보자.





## 자기 메모리를 직접관리하는 클래스

예제 코드를보자.

```java
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
        return element[--size];  //해당부분에서 메모리누수 가 발생한다.
    }

    public void checkSize(){
        if(element.length == size){
           this.element=Arrays.copyOf(element,size*2+1);
        }
    }
}
```

해당 코드에서 주석에 적힌 부분에서 메모리 누수가 발생한다.

스택에서 꺼내진 객체들 아직 참조를 해제하지 않았기 때문이다.

따라서 다음과 같이 코드를 변경해줘야한다.



```java
public Object pop(){
    if(size==0){
        throw new EmptyStackException();
    }
    Object value = element[--size];
    element[size] =null;  // 참조하고 있는 레퍼런스를 해제 하면 GC 의 대상이 됨으로 메모리 누수가 없다.
}
    
```

이렇게 해당 참조를 다 썻을 때 null 처리를 통하여 참조를 해제하면된다.



하지만 null 처리를 통한 객체 참조 해제는 예외적인 경우에서만 해야한다.



다 쓴 참조를 해제하는 가장 좋은방법은 참조를 담은 변수를 유효범위(scope) 밖으로 밀어내는 것이 가장좋다.



Null 처리를 이용한 객체참조해제는 객체 자신이 메모리를 직접관리하는 경우 가바지컬렉터는 해당 객체가 비활성 영역인지 알 수 있는 방법이 없다.

이러한 비활성화 영역은 오로지 프로그래머만이 알기 때문에 비활성영역이 되는 순간 null 처리로 객체의 참조를 해제 해줘야 가비지 컬렉터가 회수를 할 수 있다.



즉 **자기 메모리를 직접 관리하는 클래스라면 프로그래머는 항시 메모리 누수에 주의해야한다.**





## 캐시



객체 참조를 캐시에 넣고 나서 그대로 두는 경우를 말한다.



```java
public static void main(String[] args) {
    Object key = new Object(); //객체가 참조하는동안만 캐쉬가 필요한 경우
    Object value = new Object();

    Map<Object,Object> cache =new HashMap<>();
    cache.put(key,value); // 맵 자체가 key 의 레퍼런스는 들고 있음으로  key 는 gc의 대상이 될 수 없습니다.
}
```

예를 들어 key가 살아 있는동안만 캐쉬에 담고 싶다.

하지만 해당코드에서는 불가능하다.



따라서 key 가 참조하는 동안만 엔트리가 살아있는 캐시가 필요한 상황이라면 WeakHashMap 을 사용하자.

```java
public static void main(String[] args) {
    Object key = new Object();
    Object value = new Object();

    
    Map<String,Object> weakCache = new WeakHashMap<>();
    // put 을 하게 되면 내부적으로 key 를 WeakReference 로 한번 감싸게 됩니다.
    // weak ref 는 strong ref(key) 가 사라지게 되면 gc 의 대상이 됨으로 회수가 가능하게됩니다.
    weakCache.put(s,value);
}
```



WeakHashMap 을 이해하기 위해선 자바 reference 를 알고 있어야한다.



해당 내용은 아래 사이트에 자세히 설명되어있다.

https://d2.naver.com/helloworld/329631



## 리스너 혹은 콜백



앞서 살펴본 캐시와 마찬같이로 콜백을 등록하고 명확히 해지하지 않으면

콜백이 계속 쌓여갈 것이다.

이 또한 콜백을 담을때 약한참조(weak reference) 로 저장해주면 가비지 컬렉터가 수거 해간다.


---