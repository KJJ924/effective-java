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