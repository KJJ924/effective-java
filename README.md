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

---

**ITEM 2**

# **생성자에 매개변수가 많다면 빌더를 고려하라.**



앞서 살펴본 정적 팩토리와 생성자에는 똑같은 제약이 존재한다.

-> **선택적 매개변수가 많을 때 적절히 대응하기 어려움.**



다음과 같은 문제를 해결하기위한 3가지 방법을 알아보자.

- **점층적 생성자 패턴(Teslescoping Constuructor Pattern)**
- **자바빈즈 패턴(JavaBeans Pattern)**
- **빌더 패턴(Builder Ppattern)**





해당 User 클래스를  통해 위 3가지 방법을 알아보자.

```java
public class User{
    private final String name;
    private final int age;
    private final String local;
    private final String phone;
 }
```

다음과 같은 User 클래스가 존재 할때 name과 age는 필수적으로 받고 local, phone 은 선택적으로 받아 객체를 생성하고 싶다.





##  1. **점층적 생성자 패턴(teslescoping constuructor pattern)**

```java
public class User{
    private final String name;
    private final int age;
    private final String local;
    private final String phone;

    public User(String name, int age) {
        this(name,age,"","");
    }

    public User(String name, int age, String local) {
        this(name,age,local,"");

    }
    
    // local을 설정하기 싫으면 new User("name",17,"","phone") 을 사용해야함
    public User(String name, int age, String local, String phone) {
        this.name = name;
        this.age = age;
        this.local = local;
        this.phone = phone;
    }
```

다음 코드와 같이 점층적 생성자 패턴 이용하면 클라이언트 측 코드에서 해당 **매개변수가 무엇을 뜻하는지 파악하기 매우힘들다**.

또한 **설정을 원치않는 매개변수를 직접 값을 지정해줘야한다**.



예시

```java
new User("jaejoon",10); // 필수 값만 설정하고 싶을때
new User("jaejoon",10,"서울"); // 필수값 + 지역
new User("jaejoon",10,"","010-0000-0000"); //필수값 + 핸드폰번호
new User("jaejoon",10,"서울","010-0000-0000") ; // 필수값+ 지역+ 핸드폰번호

// 위와 같은 인스턴스 생성방법은 매개변수의 순서가 변경되면 치명적인 버그가 발생할 수 있다.
// new User("서울",10,"JaeJoon","010-0000-0000") 인 경우
```



하지만  점층적 생정자 패턴을 사용하여 코딩을 해도 무방하다.

**그러나 매개변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어렵다.**



---



## 2.**자바빈즈 패턴(JavaBeans Pattern)**



자바 빈즈 패턴은 평소에 많이 쓰는 **Setter 메서드**를 이용하여 원하는 매개변수 값을 설정하는 방법이다.

```java
public class User{
    private String name =""; // 점층적 생성자 패턴과 다른점은 fianl 키워드를 사용할 수 없음으로 객체를 불변으로 만들지못함.
    private int age= 0;   // 또한 필수 값들이 설정되었는지 확인이 불가능하다.
    private String local="";
    private String phone="";
    
    public User(){}
    
    //setter
    public void setName(String name) {  // 하지만 setter를 통해 매개변수들을 점층적 생정자패턴보다 쉽게 설정할 수 있다.
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }	

    public void setLocal(String local) {
        this.local = local;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
```



예시

```java
User user =new User();
user.setName("jaejoon");
user.setage(10);
user.setLocal("서울"); // 설정은 클라이언트 마음대로..
user.setPhone("010-0000-0000");
```



다음 코드처럼 자바빈즈 **패턴에서는 객체 하나를 만들려면 메서드를 여러번 호출해야하고**

**객체가 완전히 생성되기 전까지는 일관성(consistency)가 무너진다.**



또한 외부 에서 User 의 값을 조작할수 있음으로 객체의 안정성이 매우 취약해진다.

---



## **빌더 패턴(Builder Ppattern)**



**객체를 직접 만들지 않고 ** 빌더(Builder) 를 통해 필수 매개변수만으로 생성자를 호출해 빌더 객체를 생성한다.

그리고 빌더(Builder) 가 제공하는 세터메서들로 원하는 선택 매개변수들을 설정한다.

마지막으로 bulid 메서드를 호출하여 객체를 최종적으로 생성한다.



예제

```java
public class User{
    private final String name; //fianl 키워드를 사용할 수 있음
    private final int age;
    private final String local;
    private final String phone;
    
    public static class Builder{
        private final String name;
        private final int age;

        private String local = ""; //필수 값이 아닌 값들은 자동으로 "" 로 설정할 수 있음
        private String phone = "";
        public Builder(String name, int age) { // 필수 값을 강제 할 수 있음.
            this.name = name;
            this.age = age;
        }
        public Builder local(String local){ // 빌더 자신을 반환하기때문에 메서드 체인닝(method chaning)을 지원 할수있음
            this.local=local;
            return this;
        }
        public Builder phone(String phone){
            this.phone = phone;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }

    public User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.local = builder.local;
        this.phone = builder.phone;
    }
    
}
```



예시

```java
User user = new Item2.User.Builder("재준", 10) //필수값을 강제 할 수 있음
    .local("서울") //선택 매개변수
    .phone("010") //선택 매개변수
    .build();
```



객체를 생성한 예시를 보면 앞서 살펴본 패턴들 보다 빌더 패턴이 더 읽기 쉽고 생성하기 쉬운 객체가 되었다.

또한 불변객체를 유지할 수 있음으로 안전하다.



빌더 패턴은 앞서 살펴 본 패턴의 장점만 가지고 객체를 생성하는 방법이다.



하지만 빌더 패턴을 쓰는것도  점층적 생성자 보다는 간결하지만 장황하게 작성하는 경우가 생긴다.

하지만 API는 시간이 지날수록 매개변수가 많아지는 경향이 있음으로 빌더패턴을 애초에 시작하는 편이 더 좋다



또한 Lombok을 사용하면 빌더를 손쉽게 만들 수 있다.

참고: https://projectlombok.org/features/Builder



**핵심은**

`생성자나 정적 팩터리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하는 게 더 낫다.`

`매개변수 중 다수가 필수가 아니거나 같은 타입이면 특히 더 그렇다.`

`빌더는 점층적 생성자보다 클라이언트 코드를 읽고 쓰기가 훨씬 간결하고, 자바빈즈보다 훨씬 안전하다.`


---

**ITEM 3**

# **private 생성자나 열거타입으로 싱글턴임을 보증하라**



싱글턴(Singleton) 이란 인스턴스를 오직 하나만 생성할 수 있는 클래스를 말한다.



해당 아이템에선 싱글턴을 만드는 3가지 방법을 소개하고 있다.



- **public static final  방식**
- **static factory method 방식**
- **원소가 하나인 Enum 타입**



---------------------



## **public static final  방식**



해당 방식은 생성자를 private 으로 선언하여 접근을 막고

접근할 수 있는 수단을 public staitc fianl 필드로 제한 하는 것이다.



코드로 바로 알아보자

```java
public class PublicStaticFinalSingleton {
	//초기화 할때 한번만 설정됨으로 하나의 인스턴스만을 보장 할 수 있음
    public static final PublicStaticFinalSingleton INSTANCE  = new PublicStaticFinalSingleton(); 

	// 생성자를 private 으로 선언하여 외부 클라이언트에서 접근 불가능하게 선언!
    private PublicStaticFinalSingleton() {} 
}
```



**main**

```java
public static void main(String[] args){
        PublicStaticFinalSingleton instance = PublicStaticFinalSingleton.INSTANCE;
        PublicStaticFinalSingleton instance1 =  new PublicStaticFinalSingleton() //불가능
}  
```



하지만 이런 방식에도 자바 리플렉션을 이용하여 private 생성자에 접근하여 인스턴스를 생성할 수 있다.

```java
public static void main(String[] args) throws 예외생략{
    PublicStaticFinalSingleton instance = PublicStaticFinalSingleton.INSTANCE;

    Constructor<PublicStaticFinalSingleton> constructor = PublicStaticFinalSingleton.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    PublicStaticFinalSingleton newInstance = constructor.newInstance();
    System.out.println(instance==newInstance); //false
}
```

이러한 행위를 막고 싶다면  private 생성자 내에서 두번째 객체가 생성되지 못하게 막으면 된다.



**PublicStaticFinalSingleton() 생성자 코드 변경**

```java
public class PublicStaticFinalSingleton {

    public static final PublicStaticFinalSingleton INSTANCE  = new PublicStaticFinalSingleton();

    public static boolean status; //flag 변수

    private PublicStaticFinalSingleton() {
        if(status){
            throw new IllegalStateException("이 객체는 여러개의 인스턴스를 생성할수 없습니다");
        }
        status = true;
    }
}

```



**이 public staitc fianl  방식의 장점 은**

해당 클래스가 싱글턴인 것을 API에 명백히 드러낼수 있고

public staitc  필드가 fianl 이니  절대로 다른 객체를 참조할 수없다.



---



## **static factory method 방식**

해당 방식은 생성자를 private 으로 선언하여 접근막고 필드도 private 으로 접근을 제한하여

정적 팩토리 메서드를 통해 객체의 인스턴스에 접근할 수 있도록 하는 방법이다.



코드를 살펴보자

```java
public class StaticFactoryMethodSingleton {

    private static final StaticFactoryMethodSingleton INSTANCE = new StaticFactoryMethodSingleton();

    private StaticFactoryMethodSingleton() {
    }
	
    //정적 팩토리 메소드를 통해 객체의 인스턴스에 접근가능함.
    public static StaticFactoryMethodSingleton getInstance(){
        return INSTANCE;
    }
}
```



**main**

```java
public static void main(String[] args){
    StaticFactoryMethodSingleton instance = StaticFactoryMethodSingleton.getInstance();
	StaticFactoryMethodSingleton instance1 = new StaticFactoryMethodSingleton() //불가능
}
```



해당 방식도 자바 리플렉션으로 접근이 가능하니  필요하다면

public staitc fianl 방식에서 살펴본 것처럼 예외처리를 해줘야한다.



**해당 static factory method 방식의 장점**

클라이언트 코드를 수정하지 않고도 싱글톤을 사용할지 안할지 변경 할 수 있다.

```java
public class StaticFactoryMethodSingleton {

    private static final StaticFactoryMethodSingleton INSTANCE = new StaticFactoryMethodSingleton();

    private StaticFactoryMethodSingleton() {
    }

    public static StaticFactoryMethodSingleton getInstance(){
        // 해당 부분만 변경하여 싱글톤 사용여부를 변경가능
        return new StaticFactoryMethodSingleton(); 
    }
}
```



해당 메소드를 Supplier<> 에 대한 메소드레퍼런스로 사용 할 수 있다.

```java
Supplier<StaticFactoryMethodSingleton> supplier =  StaticFactoryMethodSingleton::getInstance;
StaticFactoryMethodSingleton staticFactoryMethodSingleton3 = supplier.get();
```



---

## **원소가 하나인 Enum 타입**

해당 방식은 앞서 살펴본 방식과 비슷하지만 리플렉션 같은 예외사항을 생각할 필요없다.

하지만 이런방법은 실제에서 사용 할 수 있을지 의문이든다..

또한 이러한 방식은 Enum외 클래스를 상속해야한다면 사용할수없다.



```java
public enum EnumSingleton {
    INSTANCE

 //.. methond
}
```



가장 중요한 포인트는 **원소가 하나인 열거타입을 선언**해서 만드는 부분이다.



---

**ITEM 4**
# **인스턴스화를 막으려거든 private 생성자를 사용하라**



Class 내부에 정적 메서드와 정적필드만을 담은 클래스가 존재할때 이러한 class 는 굳이 인스턴스를 만들어서 사용하지 않아도된다.

왜냐하면 정적 메서드와 정적 필드 만을 담은 Class 들은 대부분 유틸리티로 사용할려고 생성한 클래스이다.

따라서 유틸리티 클래스는 인스턴스로 만들어서 쓰려고 설계한 클래스가 아니기 때문에. 인스턴스화를 막는 것이 좋다.



이 책에서 인스턴스화를 막는 방법으로 두 가지 방법을 설명하고 있다 .

1. **추상 클래스로 만든다.**
2. **private 생성자를 만든다.**

하나 씩 알아보자..



## 1.추상 클래스로 만든다.

말 그대로 abstract 클래스는 new 키워드를 이용하여 인스턴스화를 진행 할 수 없기 때문에

spirng 의 유틸리티 클래스는 이 방법을 사용하고 있다.



코드를 보자.

```java
public abstract class AbstractUtilityClass {
    static void hello(String name){
        System.out.println("hello" + name);
    }

    public static void main(String[] args) {
        // new 키워드를 이용한 인스턴스화 를 막을수는 있지만 ...??
        AbstractUtilityClass utilityClass = new AbstractUtilityClass(); // error
    }
}
```

위 코드처럼 new 키워드를 이용하여 인스턴스를 생성할 수 없다. 하지만



abstarc 클래스의 하위 클래스를 만들어 인스턴스화를 할 수 있다.



코드를보자.

```java
public abstract class AbstractUtilityClass {
    static void hello(String name){
        System.out.println("hello" + name);
    }

    static class subClass extends AbstractUtilityClass{
    }


    public static void main(String[] args) {

        // new 키워드를 이용한 인스턴스화 를 막을수는 있지만 ...??
        //AbstractUtilityClass utilityClass = new AbstractUtilityClass();

        //상속 받았음으로 AbstractUtilityClass 의 기본 생성자를 호출하게 됨으로 인스턴스가 생성이됨..
        subClass utilityClass1 =new subClass();
    }
}
```

이러한 문제점이 존재하지만 생성된 인스턴스를 통해 static 메서드를 사용할 수는 없음으로 그렇게 큰 문제는 안될꺼 같다.

하지만 책에서는 인스턴스화를 완벽하게 막을수없기 때문에 abstarct 클래스로 만드는것보다.

Private 생성자를 만드는 것을 추천하고 있다.

---



## **Private 생성자를 만든다**



말 그대로 private 생성자를 추가하여 인스턴스화를 막는 방법이다.



코드를 바로 보자.

```java
//인스턴스화를 막으려거든 private 생성자를 사용하라
public class UtilityClass {

    //생성자가 있지만 호출할 수 없는 생성자이기 때문에
    //직관적이지 않다. 그래서 다음과 같이 의미를 나타내는 아래처럼 주석을 쓰자.
    //기본 생성자가 만들어지는 것을 막는다 (인스턴스 방지용).
    private UtilityClass(){ }

    static void hello(String name){
        System.out.println("hello"+name);
    }
}
```

이 처럼 명시적인 생성자가 private이니 외부에서 인스턴스를 생성 할 수 없다.

또한 상속을 불가능하게 하는 효과도 존재한다.

하지만 생성자가 존재하는데 호출하지 못한다. 이러한 코드는 명시적이지 못하니 위 코드와같이 주석을 반드시 달아주자.



이러한 방법은 대표적으로 Arrays 클래스 잘 나타내준다.

<img width="564" alt="Arrays private 생성자" src="https://user-images.githubusercontent.com/64793712/106268544-9b845e00-626e-11eb-8855-bd41adb12577.png">

---
**ITEM 5**

# **자원을 직접 명시하지 말고 의존 객체 주입을 사용하라**



클래스 내부에 하나 이상의 자원에 의존하고 해당 자원이 클래스 동작에 영향을 준다면  생성자를 통해 의존자원을 주입 받자는 의미이다.

해당 기법은 클래스의 유연성 , 재사용성,테스트 용이성을 개선해준다.



위 구절이 해당 아이템이 말하고자하는 핵심이다.



이제 코드를 보며 이해를해보자.

**무기 인터페이스**

```java
public interface Weapon {
    //공격을 합니다.
    void attack();
}
```



**정적 유틸리티 방식**

```java
public class Warrior {

    private final Weapon weapon = new Sword(); // 전사는 weapon 이라는 자원을 직접 생성하여 의존하고있다.

    private Warrior(Weapon weapon) {} //객체 생성금지

    public static void attack(){
        weapon.attack(); // 무기 자원이 해당 전사 클래스의 동작에 영향을 준다.
    }
} 
```

여기 전사(Warrior)라는 클래스가 있다. 전사라는 클래스는 무기(Weapon) 이라는 자원을 의존하고 있다.



자 해당 방식(정적 유틸리티 방식) 으로 코드를 생성할 때 이 전사라는 객체는 오로지 Sword 만 사용할 수 있다.

만약 더 좋은 무기를 가지고 있어도 이 전사는 무기를 변경할 수 없다.

물론 new Sword() 부분에 새로운 무기를 넣어주면 전사는 새로운 무기를 장착 할 수 있다.

하지만 해당방식은 이러한 경우에서 사용하는건 바람직 하지 않는거 같다.



여기서 이러한 경우란?

**사용하는 자원에 따라 동작이 달라지는 클래스 를 의미한다.**



자 이제 자원에 대한 주입은 클라이언트에게 책임을 넘겨서

전사라는 인스턴스를 생성할때 생성자를 통해 필요한 자원(무기) 를 넘겨주는 방식을 사용해보자.



**의존객체 주입 방식**

```java
public class Warrior {

    private final Weapon weapon; // 무기라는 자원을 의존하고있지만 직접 생성하지않고 있다.

    public Warrior(Weapon weapon) {
        this.weapon = Objects.requireNonNull(weapon); //해당 인스턴스가 만들어 질때 무기라는 의존성을 주입 받기를 원한다.
    }

    public void attack(){
        weapon.attack(); // 주입받은 무기를 이용하여 공격을 진행한다.
    }
}
```



자 여기서 핵심은 전사라는 클래스는 Weapon 이라는 자원을 구체적으로 알고 있지 않다.

클라이언트가 어떤 무기를 넘겨주냐에 따라 전사 클래스는 해당 무기에 맞는 공격을 진행 할 수 있다.

즉 전사가 무기를 변경할때마다 전사클래스의 코드 변경은 일어나지 않는다는 뜻이다.



해당 전사클래스를 인스턴스를 만들때 클라이언트가 어떤 무기를 주입하냐 에 따라 전사의 행동은 결정된다.



클라이언트 코드.

```java
public static void main(String[] args) {
    Weapon dagger = new Dagger();
    Weapon sword = new Sword();

    Warrior warrior = new Warrior(dagger); //여기서 단검을 주입하거나 ,검을 주입하나에 따라 전사의 공격행동은 변경된다.
}
```



이렇게 의존 객체를 주입하여 사용하면 엄청난 유연성과 테스트를 작성할때 매우 편하다.



하지만 이러한 방식은 의존성이 수천 개 이상 넘는 프로젝트에서 직접 관리할려고하면 코드가 복잡해질 수있다.



하지만



그러한 문제점은 스프링 ,주스 , 대거 같은 프레임워크를 활용한다면 해소를 할 수 있다.

---
**ITEM 6**

# 불필요한 객체 생성을 피하라



만약 같은 역할을 하는 객체를 계속해서 인스턴스화 해서 만든다면?

불필요한 객체를 만듦으로 비용은 계속들어간다.



따라서



똑같은 기능의 객체를 매번 생성하기보다는 객체하나를 재사용 하자.

->하지만 해당 구절을 읽고 다음과 같은 생각을 가지면 안된다.



객체를 만드는 것이 비싸며 가급적 피해야한다는 오해가 생기면 안된다.

객체를 재사용함에 따라 심각한 버그와 보안성의 문제가 생길 수 있다.





## **문자열 객체**

```java
public static void main(String[] args) {
    String name ="jaejoon"; 
    String name1 ="jaejoon";
    String name2 = new String("jaejoon"); //극단적인 예시
    System.out.println(name==name1); // true ->name과 name1 의 인스턴스가 같음.
    System.out.println(name==name2); // false ->name 과 name2 의 인스턴스가 달름
}
```

해당 코드에서 알 수 있는건 String 객체를 생성할때 new 키워드를 사용하지않고 객체를 생성한다면  자바 가상머신에 동일한 문자열 리터럴이 존재하면 그 리터럴을 재사용한다.



## 정적 팩토리 메서드 사용을 해라.

불변클래스에서 정적팩터리 메서드를 제공하여 불필요한 객체생성을 막자.



예로 Bolean(string) 대신 Boolean.valueOf(String) 팩터리 메서드를 사용하자.

```java
Boolean aBoolean = new Boolean("true"); // 해당코드는 자바 9에서 deprecate 됨.
Boolean bBoolean = Boolean.valueOf("true");
```



## 비싼 객체의 재사용 여부를 판단

생성비용이 비싼 객체를 반복적으로 사용한다면 재사용할 수 있는지 고려해보자.



책에서는 정규표현식으로 예를 들고있다. 예제를 바로 보자



```java
static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }
```

여기 문자열이 로마 숫자를 표현하는지 확인하는 static 메서드가 존재한다.

해당 메서드에서의 문제점은 s.matches 에 있다.

String 에 존재하는  matches는 Pattern 인스턴스를 생성하여  한 번 쓰고 버려진다.



따라서 해당코드로 변경하는것이 더욱 바람직하다.

```java
public static final Pattern ROMAN =Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
static boolean isRomanNumeral(String s) {
    return ROMAN.matcher(s).matches();
}
```

하지만 해당코드도 문제점이 있는데 해당메서드가 실행되지않으면 필요하지않은 객체를 생성함으로써 자원을 낭비하는 경우가 발생한다.



이러한 경우 지연초기화(lazy initialization) 으로 불필요한 초기화를 없앨 수 있지만 추천하지않는다.

왜냐하면 코드를 복잡하게 만들어지지만 성능은 크게 개선되지 않을 때가 많다.



## 오토박싱을 주의하자.



오토박싱(auto boxing)이란  기본타입과 박싱된 기본타임을 섞어 쓸 때 자동으로 상호 변환해주는 기술이다.



**오토박싱은 기본 타입과 그에 대응하는 박싱된 기본 타입의 구분을 흐려주지만 ,완전히 없애주는 것은 아니다.**



```java
public static void main(String[] args) {
    Long sum =0L; //박스 타입 Long
    long start = System.currentTimeMillis();
    for (long i = 0; i < Integer.MAX_VALUE; i++) {
        sum+=i; //프리미티브 타입 long
    }
    System.out.println("sum = " + sum);
    System.out.println(System.currentTimeMillis()-start);

}
```

해당 코드는 더해질 때마다 Long인스턴스가 만들어진다. 따라서 해당코드를 실행해보면 성능이 매우 느리다.

따라서 의도치 않은 오토박싱이 숨어들지 않도록 주의하자.

---
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
**ITEM 9**

# try-finally 보다는 try-with-resources를 사용하라



Try-with-resoruces 는 자바 7부터 사용할 수 있다.

또한 try-with-resources 을 사용하기 위해선 자원을 반납하는 클래스 자체에 AutoCloseable 인터페이스를 구현하고 있어야한다.



try-with-resources 는 기존 개발자들이 try -finally 부분에서 자원반납하던 행위를 직접하지 않아도된다.

이러한 기술을 지원함으로써 개발자가 자원을 닫는 행위를 놓치는 문제점을 잡을수 있고 코드가 매우 명확해진다.

예제코드를 보면서 얼마나 편해지는지 알아보자.



여기 커스텀한 자원을생성후 반납해야하는 MyResource 가있다.

```java
public class MyResource implements AutoCloseable{

    public void run(){
        System.out.println("프로그램이 실행됨.");
        throw new RuntimeException("강제종료");
    }


    @Override
    public void close(){
        System.out.println("프로그램이 종료하는중");
        throw new RuntimeException("종료하는 도중 문제발생");
    }
}
```

해당코드를 클라이언트측에서 사용해보자.



## try-finally

```java
public static void main(String[] args) {
        // 기존 문제점 한눈에봐도 코드가 너무 이해하기 힘듬
        MyResource myResource = null;
        try{
            myResource= new MyResource();
            myResource.run(); // 해당 부분에서 첫번째 오류가 발생하지만..?
            MyResource resource = null;
            try {
                resource = new MyResource();
              	resource.run(); // 해당 부분에서 첫번째 오류가 발생하지만..?
            }finally {
                if(resource !=null){
                    resource.close(); // 해당 부분에서 첫번째 오류가 발생하지만..?
                }
            }
        }finally {
            if(myResource !=null) {
                myResource.close(); //마지막으로 여기에서 오류가 발생함으로 앞서 발생한 예외를 스텍트레이스에 찍어주지 않음.
            }
        }
    }
```

기존 try-finally 의 문제점은 코드를 이해하기도 힘들뿐만 아니라.

![image](https://user-images.githubusercontent.com/64793712/106509713-516ed700-6511-11eb-81c6-687bbcbfe653.png)

앞서 발생하는 예외가 있음에도 불구하고 제일 나중에 발생한 예외만 스택트레이스 내역에 출력된다.



이러한 문제점은 디버깅을 매우 힘들게한다.



앞서 살펴본 문제점 모두 try-with-resources 사용하면 해결된다.



## **try-with-resources**

```java
 public static void main(String[] args) {
        // 위에 있는 코드랑 비교하면 매우명확하다. 또한 오류를 범할수 있는 확률이 현저하게 내려간다.
        try(MyResource  myResource1 = new MyResource(); 
            MyResource myResource2 =new MyResource()){
            // try-with-resource 를 사용 하면 앞서 살펴본 문제를 해결할 수 있다.
            myResource1.run();
            myResource2.run();
        }
    }
```

개발자가 해야하는 자원 반납의 역할을 AutoCloseable 인터페이스를 구현하고 **try-with-resources** 을  사용함으로서 매우 안전한 코딩을 할 수 있다.



또한 앞서 살펴본 앞서 발생하는 예외를 먹어버리는 현상도 해결 할 수 있다.

![image](https://user-images.githubusercontent.com/64793712/106510339-246ef400-6512-11eb-8b66-941066aa9d58.png)



핵심 정리

**꼭 회수해야 하는 자원을 다룰 떄는 try-with-resources 를 사용하자.**

**코드는 더 짧고 분명해지고 , 만들어지는 예외정보도 훨씬 유용하다. 

---
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

