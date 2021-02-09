**ITEM 15**

# 클래스와 맴버의 접근 권한을 최소화하라.



## 서론

잘 설계된 컴포넌트와 어설프게 설계된 컴포넌트의 차이는 클래스의 내부 데이터와 내부 구현정보를 외부 컴포넌트로 부터 얼마나 은닉화을 했는가 이 부분에서 결정된다 (**정보은닉 , 캡슐화**)







## 정보 은닉의 장점.

1. **시스템 개발속도를 높인다**(여러 컴포넌트를 병렬로 개발할 수 있기 떄문)
2. **시스템 관리 비용을 낮춘다.**(컴포넌트가 분리 되어있기때문에 디버깅과 교체가 쉬움)
3. **정보은닉 자체가 성능을 높여주지는 않지만 성능최적화에 도움을준다.**(아이템67)
4. **소프트웨어 재사용성을 높인다**(컴포넌트간 의존성이 낮아지기때문에)
5. **큰 시스템을 제작하는 난이도를 낮춰준다**(개별 컴포넌트의 동작을 검증할수 있기 때문*1번장점이랑 비슷한느낌?* )





## 자바에서 정보은닉을 위한 기본 원칙과 방법



- 핵심

  **모든클래스와 맴버의 접근성을 가능한 한 좁혀야한다.(가장낮은 접근수준을 부여)**



- 톱레벨 클래스와 인터페이스에 부여할 수 있는 접근수준은(public, package-private) 이 두가지다.

  public ->  공개API**(하위 호완을 위해 영원히 관리해줘야함)**

  package-private - >패키지 내에서만 사용가능 **(클라이언트에 피해 없이 다음릴리즈에서 수정 교체 제거 가능)**

  이처럼 패키지를 외부에서 사용할일이 없다면 package-private 를 사용하자.



- 한 클래스에서만 사용하는 package-private 클래스 ,인터페이스 는 이를 사용하는 클래스안에 private static 으로 중첩시키자



- 클래스의 공개 API를 설계 후 그 외의 모든 클래스의 멤버는 private 으로 만들자

  만약 같은 패키지의 다른 클래스가 접근해야 하는 맴버에 한하여 package-private 으로 풀어주자

  위와 같은 행동이 자주 있을시 컴포넌트 분해 여부를 생각해보자

  (주의: Serializabl을 구현한 클래스에서는 의도치 않게 (package-private, private 멤버가) 공개 API가 될 수 있다)





## 주의점



- 코드를 테스트하려는 목적으로 클래스 ,인터페이스 맴버를 공개 API로 만들어서는 안된다.

  private  맴버를 package-private 까지는 허용!  그 이상은 절대안됨



- public 클래스의 인스턴스 필드는 되도록 public이 아니어야한다.(아이템 16)

  - public 필드로 선언시 필드와 관련된 모든 것은 불변식을 보장할 수 없게됨

  - **public 가변 필드**를 갖는 클래스는 일반적으로 스레드 세이프 하지 않다.

  - 정적필드도 똑같다.(단 예외가 있음)



- 정적필드 예외 상황
  - 클래스가 표현하는 추상 개념일 경우 **상수** 라면 public static final 필드로 공개해도 좋다.
  - 단 이런 상수 일경우 각 단어 사이에 밑줄( _ ) 을 넣어 관례를 지키자
  - 만약 상수가 아닌 가변 객체를 참조한다면 클라이언트측에서 배열의 내용을 수정할 수 있으니 **기본 타입 값 과 불변 객체만 참조하자**



- 클래스에서 public static final 배열필드를 두거나 이 필드를 반환하는 접근자 메서드를 제공해서는 안된다.

  ```java
  public class Foo {
      // 클라이언트가 얼마든지 배열의 요소 값을 변경 가능함
      public static final String[] fooList = {"foo1","foo2","foo3"};
  }
  ```



이러한 경우 두가지방법이 있다.

1.  배열  public 접근 지정자를 private 으로 변경 후 public 불변 리스트를 추가

     ```java
     public class Foo {
         private static final String[] fooList = {"foo1","foo2","foo3"};
         public static List<String> immutableList = Collections.unmodifiableList(List.of(fooList)); //불변 리스트를 제공해주자
     }
     ```

2.  배열을 private 으로 선언후 그 복사본을 반환(방어적 복사)

     ```java
     public class Foo {
     
         private static final String[] fooList = {"foo1","foo2","foo3"};
       
         // 방법 2)
         public static String[] copyList(){
             return fooList.clone();
     }
     ```







## 용어



- **톱레벨 클래스**

  가장 바깥에 존재하는 클래스를 말한다.

  ```java
  //public,package-private 만 부여가능
  public class TopLevelClass { 
      static class SubClass{}
      static class SubClass2{}
  }
  ```

  ![스크린샷 2021-02-10 오전 6 18 24](https://user-images.githubusercontent.com/64793712/107437680-b316ed00-6b72-11eb-9be5-37e741d524f4.png)






## 이해하지 못한부분

1. 자바 9  모듈 시스템 개념의 도입 구절 -> 아직 모듈이라는 개념이 부족한거 같다.

   

