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


