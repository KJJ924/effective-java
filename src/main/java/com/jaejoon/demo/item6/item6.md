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