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