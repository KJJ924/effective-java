# effective-java
 :pencil2: 이펙티브 자바 3판 정리


---

**ITEM 1**

# **생성자 대신 정적 팩터리 메서드를 고려하라**



## 목표

- 책에서 소개하는 해당 ITEM 의 **5가지** 장점과 **2가지** 단점을 알아보자



**장점**

1.  이름을 가질 수 있다.
2.  호출될 때마다 인스턴스를 새로 생성하지않아도된다.
3.  반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
4.  입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
5.  정적팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.



**단점**

1. 상속을 하라면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.
2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.

---

 **장점**

### **1. 이름을 가질 수 있다.**

생성자에 넘기는 매개변수와 생성자 자체로 반환될 **객체의 특성을 잘 설명하지 못하는 경우**

정적 팩터리 메서드를 사용하면 반환될 **객체의 특성을 명확하게 나타낼 수 있다.**



예제 코드를 보자.

```java
class User{
    private String name;

    public User() { }

    public User(String name) {
        this.name = name;
    }

    static User Name(String name){
        User user = new User();
        user.name =name;
        return user;
    }
```



```java
public static void main(String[] args) {
    //생성자에 넘기는 매개변수와 생성자 자체만으로는 반환될 특성을 설명하지못함.
    User user = new User("JaeJoon");

    // 1. 이름을 가질 수 있다. 반활될 객체의 특성을 설명할 수 있음
    User jaeJoon = User.Name("JaeJoon");
}
```



new User("Name") 보다 User.name("name") 이 해당 객체를 더 자세히 설명 할 수 있다.



또한 **생성자는 메서드 시그니처의 제약이 존재한다**.

-> 똑같은 타입을 파라미터로 받는 생성자를 정의 할 수 없다.

```java
public User(String name) {
    this.name = name;
}

public User(String local) { // 메소드 시그니처 중복으로 오류 발생
    this.local =local;
}
```

하지만 정적 팩터리 메서드에는 제약이없다. 



---



## 2. **호출될 때마다 인스턴스를 새로 생성하지않아도된다.**

불변(immutable) 클래스 는 인스턴스를 만들어놓거나

새로 생성한 인스턴스를 캐싱하여 재활용 하는 식으로 **불필요한 객체생성을 피할 수 있다.**

```
장점: 불필요한 객체의 생성을 피할 수 있다. / 객체가 자주요청되는 상황이라면 성능을 향상시킬 수 있음.
```

책에서 소개하는 예제는 **`Boolean.valueOf(boolean)`** 을 예를 들고있다.



해당 구문을 읽고 내가 생각한 예제를 보자.(오류가 있을 수 도 있습니다)

```java
class User{
    private String name;

    private static final User ADMIN = new User("admin"); 
    private static final User TEST_USER = new User("testUser"); 
    
    static User getAdmin(){
            return ADMIN;
        }
    
    static User getTestUser(){
        return TEST_USER;
    }
}
```

만약 admin 과 testUser는 불변이고 자주 사용된다면 인스턴스를 매번 사용하지 않고.

User.getAdmin() , User.getTestUser() 를 통해 **같은 객체를 반환하는 식으로 코딩하는 것**이 더욱 좋다.



----



## 3. **반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.**



반환할 객체의 클래스를 자유롭게 선택할 수 있다 

-> 클래스에서 만들어줄 객체의 클래스를 선택 할 수 있는 **유연함이 생김**



유연함이 생김에 따라 어떠한 API를 사용하기위해 프로그래머가 익혀야 하는 개념의 수와 난이도가 낮아진다.



책에서 소개하는 예제는 **`java.util.Collections`** 을 예를 들고있다.



해당 구문을 읽고 내가 생각한 예제를 보자.(오류가 있을 수 도 있습니다)



다음과 같은 구조와 코드가 존재 할때

![](https://user-images.githubusercontent.com/64793712/105736907-a8f0ce00-5f78-11eb-9cf1-a973fabb14ed.png)



```java
static class CARD{
    public static final String SAMSUNG_CARD = "삼성";
    public static final String KAKAO_CARD="카카오";

    static Payment payment(String card){
        switch (card){
            case SAMSUNG_CARD:
                // 3. 반환 타입의 하위타입 객체를 반환할 수 있는 능력이 있다.
                return new SamSungPayment();
            case KAKAO_CARD:
                return new KakaoPayment();
        }
        throw new IllegalArgumentException();
    }

}
```

Payment 라는 상위타입 Interface 로 하위 타입인 SamSungPayment 클래스를 숨김으로써

프로그래머는 **인터페이스대로 동작할 객체를 얻을 것을 알기에 ** 굳이 SamSungPayment.class 를 찾아가서 알아보지 않아도된다.



------

## 4. **입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.**



반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.  의 연장선에 있다고 생각이든다.



말 그대로 반환 타입의 하위타입이기만 하면 어떤 클래스의 객체를 반환하든 상관이 없기 때문이다.



본 책의 예제는  EnumSet 클래스를 예로 들고있다.

EnumSet 의 요소가 64 개 이하면 ReqularEnumSet 의 인스턴스를

EnumSet 의 요소가 65 개 이상이면 JumboEnumSet 의 인스턴스를 반환한다.



하지만 앞서말한 장점 3과 똑같이 클라이언트 측에서는 구체적인 클래스를 몰라도 사용하는데 아무 문제가 발생하지 않는다.



앞서 내가 만든 예제도 똑같다.

```java
static class CARD{
    public static final String SAMSUNG_CARD = "삼성";
    public static final String KAKAO_CARD="카카오";
    static Payment payment(String card){ // 들어오는 card 의 값 에 따라 반환하는 객체가 달름
        switch (card){
            case SAMSUNG_CARD:
                return new SamSungPayment();
            case KAKAO_CARD:
                return new KakaoPayment();
        }
        throw new IllegalArgumentException();
    }
}
```

payment(String card) 에서 card 에들어오는 매개변수가 에 따라 

구체적인 클래스들  SamSungPayment , KakaoPayment 이  반환된다 하지만 클라이언트측에서 반환받는 객체는 

Payment 인터페이스이기 때문에 구체적인 클래스를 몰라도 얼마든지 사용하는데 문제가 없다.

---



## 5. **정적팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.**



해당 문구는 잘 이해가 가지 않지만 책에서 설명하는 내용으로는 JDBC를 예로 들고있다.

또한 Service provider framework 를 만드는 근간이 된다고 설명 하고 있다.



Service provider framework  3개의 핵심 컴포넌트는 다음과 같다.

1. 서비스 인터페이스(service interface)
2. 제공자 등록 API(provider registraction API)
3. 서비스 접근 API (service access API)



완벽한 이해는 하지못했지만..

뒷 페이지에서 서비스 프로바이더 인터페이스가 없다면 리플렉션 을 사용해야 한다.  라고 설명되어있다.



해당 문구를 추측하여 생각한 예제 코드는 다음과 같다.

```java
static class CARD{
    public static final String SAMSUNG_CARD = "삼성";
    public static final String KAKAO_CARD="카카오";
    public static final String TOSS_CARD ="토스";

    static Payment payment(String card){
        switch (card){
            case SAMSUNG_CARD:
                return  new SamSungPayment();
            case KAKAO_CARD:
                return new KakaoPayment();
            case TOSS_CARD:
                Payment toss = null;
                try {
                    // 5. 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도된다.
                    Constructor<?> tossConstructor = Class.forName("FQCN(Toss)").getConstructor();
                    toss  = (Payment) tossConstructor.newInstance();
                } catch (Exception... 생략) {
                    e.printStackTrace();
                }
                return toss;

        }
        throw new IllegalArgumentException();
    }

}
```

위 코드를 보면 지금 당장은 FQCN 에 해당하는 객체 클래스가 존재하지 않아도 문제가 발생하지 않는다.

이러한 유연함을 가질 수 있다는 장점인 것 같다.





------

**단점**



## 1. **상속을 하라면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.**



해당 단점은 불변타입으로 만들려면 이 제약을 지켜야 한다는 점이 오히려 장점이으로 받아 들일 수도 있다.



## **2.정적 팩터리 메서드는 프로그래머가 찾기 어렵다.**

생성자 처럼 API 설명에 명확히 드러나지 않음으로  사용자는 정적 팩터리 메서드 방식 클래스를 인스턴스화 할 방법을 찾아야한다.

따라서 API 문서를 잘 작성하고 **메서드 이름도 널리 알려진 규약에** 따라 짓는 식으로 문제를 완화 해야한다.



 **정적 팩토리에서 흔히 사용하는 명명 방식.**

| **명명 규칙**                  | **설명**                                                     |
| ------------------------------ | ------------------------------------------------------------ |
| **from()**                     | 매개변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메소드.                                                                                                 ex) Date.from() |
| **of()**                       | 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메소드.                                                                                                    ex) Enum.of() |
| **valueOf()**                  | from 과 of 의 더 자세한 버전                                                                                                                                                                            ex) BigInteger.valueOf() |
| **instance()  getInstance()**  | (매개 변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만 같은 인스턴스임을 보장하지는 않는다.                                                        ex)StackWalker.getInstance() |
| **create()** **newInstance()** | instance 혹은 getInstance와 같지만 매번 새로운 인스턴스를 생성해 반환함을 보장한다.                                                                                       ex) Array.newInstance() |
| **getType()**                  | getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 쓴다.                                                                             ex)Files.getFileStore() |
| **newType()**                  | newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩토리 메소드를 정의할 때 쓴다.                                                                                 ex)Files.newBufferedReader() |
| **type()**                     | getType과 newType의 간결한 버전                                                                                                                                                                                  ex)Collections.list() |

