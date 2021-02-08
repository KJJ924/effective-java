**ITEM 10**

# equals는 일반 규약을 지켜 재정의하라.



equals 메서드를 재정의하는것은 쉽다.

하지만 잘못 재정의한다면 큰 문제가 발생할수 있다.

따라서 재정의하지 않아야 하는 경우와 반대로 재정의 해야하는 경우를  알아보자.



## 다음에 항목에 하나라도 해당한다면 equals를 재정의하지 않는것이 좋다.

**1.각 인스턴스가 본질적으로 고유하다.**

​	-> 값 표현하는 개체가아니라 동작하는 개체를 표현하는 클래스.



**2.인스턴스의 논리적 동치성(logical equality)을 검사할 일이 없는 경우.**

​	-> Pattern의 인스턴스가 같은 정규 표현식을 나타내는지 검사.



**3.상위 클래스에서 재정의한 equals가 하위클래스에도 들어맞을 때.**

​	-> Set 구현체는 AbstractSet 이 구현한 equals 를 상속받아 쓴다.



**4.클래스가 private 이거나 package-private 이고 equals 메서드를 호출할 일이 없을 때.**

​	equals가 호출되는걸 막고 싶다면 다음과같이 코드를 작성하면된다.

```java
@Override public boolean equals (Object o){
  	throw new AssertionError();	//호출 금지
}
```



## equals 를 재정의해야 할때.



객체식별성(object identity) 두 객체가 물리적으로 같은가 ? X

논리적동치성(logical equality)을 확인해야 하는 경우. O



즉 객체가 같은지 알고싶은 것이 아니라 객체가 가지고있는 값이 같은지 알고싶어하는경우.

```java
public class User {
    private Long Id; // 해당 값이 같으면 같은 객체로본다
}
```



equals의 논리적 동치성을 재정의하는 경우

Map의 key 와  Set의 원소로 사용가능하다 또한 값자체를 비교하길 원하는 프로그래머의 기대에 부흥 할 수 있다



논리적 동치성을 확인해야하는 경우에도 equals를 재정의 할 필요없는 경우가있다.

- **인스턴스 통제 클래스(아이템1 참고) 라면 재정의 할필요없다.**

  -> 왜냐하면  이런 클래스에선 논리적으로 같은 인스턴스가 2개 이상 만들어지지 않는다.



## equals 메서드를 재정의 할때 반드시 일반 규약을 따르자.



- **반사성(reflexivity):** null 이 아닌 모든 참조 값 x에 대해 x.equals(x) 는 true다.

  ```java
  //반사성(reflexivity) 
  public static void main(String[] args) {
    	User user = new User();
      user.equals(user); //항상 true 다.
  }
  ```



- **대칭성(symmetry)**:null 이 아닌 모든참조값 x,y에 대해 x.equals(y) 가 true 면 y.equals(x) 도 ture다.

  ```java
  //대칭성(symmetry)
  public static void main(String[] args) {
      User x = new User();
      User y = new User();
      x.equals(y); //ture 이면
      y.equals(x); //ture 다
  }
  ```



**위반사례**

  ```java
  // 대칭성 위배
  public final class CaseInsensitiveString{
      private final String s;
  
      public CaseInsensitiveString(String s){
          this.s = Objects.requireNonNull(s);
      }
  
      @Override public boolean equals(Object o){
          if(o instanceof CaseInsensitiveString)
              return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
  
          if(o instanceof String)// 여기서는 문제 없지만 String 클래스가 해당클래스를 알고있을까??
              return s.equalsIgnoreCase((String) o); //한 방향으로만 작동하게됨
          return false;
      }
  
      public static void main(String[] args) {
          CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
          String s ="polish";
          cis.equals(s); // ture
          s.equals(cis); // 같아야하지만! false 
      }
  }
  ```

이러한 경우 CaseInsensitiveString 의 equals 를 String 과 연동하겠다는 것을 포기하자.

  ```java
  @Override public boolean equals(Object o){
      return o instanceof CaseInsensitiveString &&
              ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
  }
  ```

다음과 같이 간단한 모습으로 변경하자.



- **추이성(transitivity)**: null 이 아닌 모든 참조값 x,y,z에 대해 x.equals(y) 가 true 이고 y.equals(z) 도 ture면 x.equals(z) 도 ture 이다.

  ```java
  //추이성(transitivity)
  public static void main(String[] args) {
      User x = new User();
      User y = new User();
      User z = new User();
      x.equals(y); // x y 가 같고
      y.equals(z); // y z 가 같으면
      x.equals(z); // x z 도 같다.
  }
  ```



- **일관성(consistency)**: null 이 아닌 모든 참조 값 x,y 에 대해 x.equals(y) 를 반복해서 호출하면 항상 ture 를 반환하거나 항상 false 를 반환한다.

  ```java
  //일관성(consistency)
  public static void main(String[] args) {
      User x = new User();
      User y = new User();
  
      for (int i = 0; i < Integer.MAX_VALUE; i++) {
          x.equals(y); // 반복중 ture 이거나 false 둘중 항상 같은 값을 반환해야함.
      }
  }
  ```



- **null-아님**: null 이 아닌 모든 참조 값 x 에 대해 x.equals(null) 은 false 다.

  ```java
  //null-아님 
  public static void main(String[] args) {
    	User user = new User();
      user.equals(null); //false
  }
  ```



## 양질의 equals 메서드 구현 4단계

1.  ==연산자를 사용해 입력 값이 자기 자신의 참조인지 확인한다.

-> if (this == o) return true;

2. Instanceof 연산자로 입력이 올바른 타입인지 확인한다.

   ->o instanceof 타입.class

3. 입력을 올바른 타입으로 형변환한다.

   -> 2번에서 검사하기 때문에 안전하게 형변환을 진행할 수 있다.

4. 입력 객체와 자기자신의 대응되는 핵심 필드들이 모두 일치하는지 하나하나 검사한다.

   ->return x == point.x && y == point.y;





## Equals 구현시 주의 사항.



기본타입: ==  비교(단 float,double 필드 는 Float.compare(float,folat) , Double.compare(double,double) 로 비교 부동소수값을 다뤄야함)

참조타입: equals 비교

배열필드: 원소 각각을 앞서 말한 지침대로 비교 / 모두 핵심필드 일시 Arrays.equals() 를 사용



null 정상값으로 취급하는 참조 타입필드 주의.

-> 이런필드는 정적메서드 Objects.equals(object,object) 로 비교해 NullpointException  발생을 예방하자.



필드 비교 순서는 equals의 성능을 좌우한다.

-> 가능성이 더크거나 비교하는 비용이 싼 필드를 먼저 비교하자.



equals 를 재정의할 땐 반드시 hashCode 도 재정의하자.

-> 아이템(11)



object 외의 타입을 매개변수를 받는 equals 메서드는 선언하지말자.

```java
public boolean equals(User user){} //해당 메서드는 오버라이딩 이 아닌 오버로딩이다.
```

따라서 @Override 애노테이션을 붙여 컴파일 타임에 문제를 정확히 할 수 있도록 하자.



## 핵심정리

꼭 필요한 경우가 아니면 equals 를 재정의하지 말자 많은 경우에 object의 equals가 원하는 비교를 정확히 수행해준다.

재정의해야 할 땐 클래스의 핵심필드 모두를 빠짐없이 다섯가지 규약을 확실히 지켜가며 비교해야 한다.

---