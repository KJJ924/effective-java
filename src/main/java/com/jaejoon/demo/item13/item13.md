---
**ITEM 13**

# Clone 재정의는 주의해서 진행하라



Cloneable 은 복제해도 되는 클래스를 알려주는 **믹스인 인터페이스** 이다.

하지만 Cloneable은 의도한 목적을 제대로 이루지 못했다.



Cloneable 인터페이스는 아무 메서드를 가지고있지않다.



clone 메서드가 정의된것이 Cloneable이 아닌 Object 이고 접근지정자마저 protected 이다.

따라서 클래스내에서 Cloneable을 구현하고 있다해서 clone 메서드를 사용할수 있지는 않다.

```java
public class User implements Cloneable { //해당 인터페이스를 구현하고있다고해서 사용할 수는 없다.
}
```



그럼 왜 Cloneable 인터페이스는 존재할까?



해당 인터페이스가 존재하는 이유는  Object의 clone 메서드의 동작방식을 결정한다.

구현 여부에 따라 구현하고있다면 필드단위로 복사하여 반환해주지만,

구현하고있지않다면 CloneNotSupportedExcetion 을 던진다.



이러한 인터페이스 이용은 매우 이례적이며 따라하지말자.





## clone 메소드의 일반규약



x.clone() != x  -> 원본객체와 클론객체의 주소값은 일치하지않는다.

x.clone().getClass() == x.getClass() -> 원본객체의 클래스와 클론객체의 클래스는 같다. 반드시 만족하지않아도 된다.

x.clone().equals(x) ->  원본객체와 클론객체의 논리적 동치성은 일치해야한다. 필수는 아니다.

x.clone().getClass() == x.getClass()  super.clone() 을 호출하여 얻은 객체를 clone 메소드가 반환하면 참이다.





## clone 메서드의 사용



**가변 상태를 참조**하지않는 클래스라면

super.clone() 호출하는것만으로도 충분하다.

단 Object clone 메소드는 Object 타입으로 객체를 리턴하고있다.

이러한 방식보다는 자바는 **공변 반환타이핑** 을 지원함으로 복사한 클래스 타입으로 리턴하여



공변반환타이핑

->재정의한 메서드의 반환타입은 상위클래스의 메서드가 반환하는 타입의 하위 타입일 수 있다.



클라이언트 코드측에서 형변환을하지 않도록 하자. 또한 예외를 던지지말고 try-catch 블록으로

CloneNotSupportedException 잡아주자. 해당예외는 체크예외가아닌  언체크예외로 설계되었어야한다(잘못된 설계)

```java
public class UserClone implements Cloneable {
    private String name;

    @Override
    public UserClone clone()  {
        UserClone clone = null;
      //굳이 외부 클라이언트에 체크예외를 던지지말자. 만약 예외가 발생해도? 외부클라이언트에서는 해줄 수 있는 처리가없다.
      // 원래라면 cloneNotSupportedException 이 체크예외가아닌 언체크드예외로 설계하는것이 맞다고 책에선 말하고있다
        try { 
          return(UserClone) super.clone(); //object 타입으로 반환하지말자.
        } catch (CloneNotSupportedException e) {
          throw new AssertionError(); 
        }
    }
}
```





위에서 살펴본 방법은 가변상태를 가지지않는 클래스라면 충분하다.

하지만 가변상태를 가지고있다면 어떻게 될까?

```java
public class UserClone implements Cloneable {
    private String name;
    private Info info; //info라는 가변상태를 참조하고있다. 
  
    @Override
    public UserClone clone()  {
        try { 
          return(UserClone) super.clone();
        } catch (CloneNotSupportedException e) {
          throw new AssertionError(); 
        }
    }
}
```

먼저 앞서 보았던 방식으로 코딩하게 된다면.

해당코드는 원본객체나 클론객체의 Info 값을 수정된다면.

서로의 값이 동시에 변하게 될것이다.(불변식을 지키지못함)

```java
@Test
void userClone(){
    UserClone resource = new UserClone("name" ,new Info(19,"seoul"));
    UserClone resourceClone = resource.clone();
    Info info = resourceClone.getInfo(); // 클론객체의 info 객체를 가져와
    info.setAge(10);//값을 변경하면?

    assertThat(resource.getInfo().getAge()).isEqualTo(19); //원본 데이터의 값은 그대로 유지되어야한다.
}
```

해당 테스트코드를 실행해보면 테스트가 깨질것이다.



![image](https://user-images.githubusercontent.com/64793712/107149065-fa965100-6999-11eb-83ed-20cec15fd925.png)

자 다음과같은 그림처럼 두개의 인스턴스(원본,클론객체)가 같은 Info인스턴스를 바라보고 있음으로 발생하는 문제이다.



clone은 원본 객체에 아무런 해를 끼치지 않는 동시에 복제된 객체의 불변식을 보장해야한다.

따라서 다음과같이 복사하자.



```java
@Override
public UserClone clone()  {
    try { 
        UserClone clone = (UserClone) super.clone();
        clone.info = clone.info.clone(); // 가변상태를 참조하고있는 필드도 복사해서 넘겨주자.
        return clone;
    } catch (CloneNotSupportedException e) {
       throw new AssertionError();
    }
}
```

해당 방법으로하면 앞서 살펴본 문제도 해결가능하다.



하지만  Info 필드가 fianl 이였다면 해당 방식은 사용하지못한다. final 인 경우 새로운 값을 할당하지 못하기 때문이다.

따라서

복제할 수있는 클래스를 만들기 위해 일부필드에서 final 한정자를 제거해야 할 수 도 있다.



**복잡한 가변상태를 갖는 클래스의 복제(HashTable)**

해시테이블 내부는 버킷들의 배열이고, 각 버킷은 키 -값 쌍을 담는 연결 리스트의 첫번째 엔트리를 참조함.



```java
public class HashTable {
    private Entry[] bucket = ...;
    static class Entry{
        final Object key;
        Object value;
        Entry next;

        public Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
      //..생략 
    }
}
```



```java
@Override
public HashTable clone() {
	try {
      HashTable result = (HashTable) super.clone();
      result.bucket = result.bucket.clone();
      return result;
	} catch (CloneNotSupportedException e) {
			throw new AssertionError();
	}
}
```

다음과같은 클론은 자신만의 버킷 배열을 가지지만 이 배열은 원본과 같은 Entry를 가진다.



따라서 버킷안에있는 Entry의 요소들을 순회하여 각 버킷에 대해 깊은복사를 수행하자.

```java
Entry deepCopy() { //엔트리가 가르키는 연결리스트를 재귀적으로 복사.
    return new Entry(key, value, next == null ? null : next.deepCopy());
}
```



```java
@Override
   public HashTable clone() {
        try {
            HashTable result = (HashTable) super.clone();
            result.buckets = new Entry[buckets.length];
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] != null)
                    result.buckets[i] = buckets[i].deepCopy();
            }
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
```

하지만 이런방식은 재귀호출 때문에 스택프레임을 소비하기때문에

배열의리스트가 길다면 스택오버플로우가 발생할 수있다.



따라서 재귀호출대신 반복자를 써서 순회하는 방식을 추천한다.



## clone() 재정의보단... 복사생성자 ,복사 팩터리를 사용하자



Cloneable 을 이미 구현한 클래스를 확장하지 않는다면

복사 생성자와 복사팩터리를 사용하여 너다은 객체 복사방식을 제공하자.



**복사생성자**

```java
public Yum(Yum yum){...}; //복사 생성자 -> 단순히 자신과같은 클래스의 인수로 받는 생성자를 말함
```



**복사팩토리**

```java
public static Yum newInstance(Yum yum){...};
```



해당 방식들의 장점은 무엇일까?



1. 위험한 객체 생성 매커니즘(생성자를 사용하지않고 객체생성) 을 사용하지않음
2. 문서화 규약에 기대지않음
3. final 필드 용법과 충돌하지않음
4. 불필요한 검사 예외를 던지지않는다.
5. 형변환을 할필요가 없어진다.
6. 해당 클래스가 구현한 인터페이스 타입의 인스턴스를 파라미터로 받을수 있다.