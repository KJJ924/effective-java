**ITEM 11**

# equals를 재정의하려거든 hashCode도 재정의하라





equals 를 재정의 한다는 것은 논리적 동치성(logical equality) 을 검사하기위해서이다.



즉 다음과같은 class 의 인스턴스를 만들었을때 해당 class가 가지고있는 id값이 같으면 같은객체로 보겠다는 뜻이다.



```java
public class SameObj {
    private final int id;
  
  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SameObj sameObj = (SameObj) o;
        return id == sameObj.id;  //id값이 같으면 같은객체로 보겠다.
    }
}
```



자 여기까지는 문제가없다.

하지만 자바에서 HashMap , HashSet 등등 객체의 hash 를 이용한 자료구조는 객체의 hashcode를 이용한다.



즉 equals 만 재정의 하고 hashCode 를 재정의하지않으면 컬렉션의 요소로 사용할때 문제가 발생한다.

```java
@Test
void hashCode_not_override(){
    SameObj sameObjTest = new SameObj(1); //id =1 인 인스턴스 생성
    Map<SameObj,Integer> sameObjIntegerMap = new HashMap<>();
    sameObjIntegerMap.put(sameObjTest, sameObjTest.getId()); 

   	assertThat(sameObjTest).isEqualTo(new SameObj(1)); //equals만 재정의했음으로 해당코드는 성공!
    assertThat(sameObjIntegerMap.get(new SameObj(1))).isNotNull(); // 같은 id 값을 가지고있지만 해당 테스트는 실패한다.
}
```

다음 테스트를 실행해보면 테스트가 실패 할 것이다.



이러한 버그는 HashCode의 규약을 지키지 않아서 발생한다. 이제 HashCode 3가지 규약을 알아보자.



## HashCode의 3가지 규약

1. equals 가 변경되지 않았다면 ,애플리케이션이 실행되는 동안 그 객체의 hashCode 메서드를 몇번이고 호출해도 같은 값을 반환해야한다.
2. equals(Object)가 두객체를 같다고 판단했으면, 두 객체의 hashCode는 똑같은 값을 반환해야한다.
3. equals(Object)가 두객체를 다르다고 판단했더라도 두 객체의 hashCode 가 서로 다른 값을 반환할 필요는 없다. 단 , 객체에 대해서는 다른 값을 반환해야 해시테이블 의 성능이 좋아진다.



여기 3가지 규약 중 앞서 살펴본 문제는 2번에 때문에 발생한다.

해당 규약을 지키기위해서 논리적으로 같은 객체는 반드시 같은 해시코드를 반환하게 hashCode를 재정의 해야한다.



자 그럼 해쉬코드를 다음과 같이 재정의해보면 어떨까?

```java
@Override 
public int hashCode() {
    return 41;
}
```

다음과 같이 재정의한다면 위에서 실패한 테스트는 이제는 성공한다.



하지만 다음과같이 재정의하게된다면 매우 성능이 떨어질것이다.



HashTable 의 자료구조는 다음과 같다.

![img](https://media.vlpt.us/post-images/cyranocoding/8d25f580-b225-11e9-a4ce-730fc6b3757a/1iHTnDFd3sR5FqjHD1FDu9A.png)

*출처: https://ko.wikipedia.org/wiki/*

자 다음과 같은 구조를 가질때 만약 모든 인스턴스의 hashCode가 41을 가지게된다면

위의 그림에있는 bucket에 모든 인스턴스가 쌓일 것이다.

그럼 시간복잡도가 O(1) 인 해쉬테이블이 O(n) 으로 느려 질것이다.



해당 문제는 3번째 규약인  *단 , 객체에 대해서는 다른 값을 반환해야 해시테이블 의 성능이 좋아진다.*  을 지키지 않아서이다.



따라서 문제는 해결되지만 성능은 매우 떨어짐으로 해당 방법으로 문제 해결을 하면 안된다.



그럼  hashCode 를 어떻게 짜야 좋을까?



## 좋은 해쉬함수 만들기



1. int변수 result를 선언 후 값 c로 초기화한다. 이때 c는 해당 객체의 첫번째 핵심필드(equals 비교에 사용되는 필드) 를 단계 2.a 방식으로 계산한 해시코드다.

2. 해당 객체의 나머지 핵심 필드 f 가각에 대해 다음 작업을 수행한다.

   a. 필드의 해시코드 c를 계산한다.

   ​	i. 기본 타입 필드라면 ,Type.hashCode(f)를 수행한다. 여기서 Type 은 해당 기본타입의 래퍼 클래스다.

   ​	ii.참조 필드이면서 클래스의 equals가 필드의 equals를 재귀적으로 호출해 비교한다면 이 필드의 hashCode를 재귀적으로 호출한다. 만약 필드의 값이 null 이면 0을 반환.

   ​	iii. 필드가 배열이라면 핵심원소 각각을 별도 필드처럼 다룬다.

   b.단계 2.a에서 계산한 해시코드 c로 result를 갱신한다. 코드로는 다음과같다.

​	`result=31*result+c`

3. result를 반환한다.



해당 예시 코드이다.

```java
@Override
public int hashCode() {
    int c = 31;
    //1. int변수 result를 선언한 후 첫번째 핵심 필드에 대한 hashcode로 초기화 한다.
    int result = Integer.hashCode(firstNumber);

    //2. 기본타입 필드라면 Type.hashCode()를 실행한다
    //Type은 기본타입의 Boxing 클래스이다.
    result = c * result + Integer.hashCode(secondNumber);

    //3. 참조타입이라면 참조타입에 대한 hashcode 함수를 호출 한다.
    //4. 값이 null이면 0을 더해 준다.
    result = c * result + address == null ? 0 : address.hashCode();

    //5. 필드가 배열이라면 핵심 원소를 각각 필드처럼 다룬다.
    for (String elem : arr) {
      result = c * result + elem == null ? 0 : elem.hashCode();
    }

    //6. 배열의 모든 원소가 핵심필드이면 Arrays.hashCode를 이용한다.
    result = c * result + Arrays.hashCode(arr);

    //7. result = 31 * result + c 형태로 초기화 하여 
    //result를 리턴한다.
    return result;
}
```

*출처: https://jaehun2841.github.io/2019/01/12/effective-java-item11/#hashcode*



솔직히 너무 복잡하다.

따라서 이미 만들어진 메소드를 사용하자.



`Objects.hash()`

```java
@Override
public int hashCode() {
    return Objects.hash(id); //앞서살펴본것보다 성능이 뒤 떨어지긴 한다.
}
```

해당 메서드는 성능이 뒤떨어지긴하지만.

내 생각에는 성능도 중요하지만 가독성이 더 좋은코드면 나쁘지 않은 코드라고 생각한다.

만약 극한의 성능을 추구한다면 앞에서 살펴본 것처럼 코딩하자.



Lombok 을 사용해서도 재정의 할 수있다.  @EqualsAndHashCode  << 사실 제일많이써봄





## hashCode 를 재정의 할때 주의 할점



성능을 높인답시고 해시코드를 계산할 때 핵심필드를 생략해서는 안된다.



hashCode가 반환하는 값의 생성 규칙을 API 사용자에게 자세히 공표하지 말자.

-> 클라이언트가 이값에 의지하지않게 되고 추후에 계산 방식을 변경할수 있다.





## 핵심정리

equals를 재정의 할때는 hashCode를 반드시 재정의하자.

그렇지 않다면 프로그램이 제대로 동장하지 않을 것이다.

재정의한 hashCode는 Object의 API 문서에 기술된 일반 규약을 따라하야 하며,

서로 다른 인스턴스라면 되도록 해시코드도 서로 다르게 구현해야한다.

---