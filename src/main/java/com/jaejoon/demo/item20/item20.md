**ITEM 20**

# 추상 클래스보다는 인터페이스를 우선하라.



## 서론

자바가 제공하는 다중구현 메커니즘은 인터페이스와 추상 클래스 이다.

두 매커니즘의 차이점은 다음과 같다.



추상클래스: 추상클래스가 정의한 타입을 구현하는 클래스는 반드시 추상클래스의 하위 클래스가 되어야한다. 

인터페이스:선언한 메서드를 모두 정의하고 그 일반 규약을 잘 지킨 클래스라면 다른 어떤 클래스를 상속했든 같은 타입으로 취급된다.



이러한 차이점이 생기는 이유는 자바는 다중상속을 지원하지 않기 때문이다.



*해당 부분은 [백기선라이브스터디 8 주차 인터페이스](https://k3068.tistory.com/34) 에서 자세히 설명했습니다.*



## 인터페이스의 장점



- **기존 클래스에도 손쉽게 새로운 인터페이스를 구현해넣을 수 있다.**
  - 인터페이스가 요구하는 메서드를 추가하고 클래스선언에 implements 구문만 추가하면 끝이다 (Comparable,Iterable,AutoCloserable)



- **인터페이스는 믹스인(mixin) 정의에 안성 맞춤이다.**
  - 주된 기능에 선택적 기능을 추가하는 것.



- **인터페이스로는 계층구조가 없는 타입 프레임워크를 만들 수 있다.**

  - ```java
    public interface SingerSongwriter extends Singer,Songwriter {
        void strum();
        void actSensitive();
    }
    ```

  - 만약 해당구조를 추상클래스로 생성한다면 새로운 추상클래스를 만들어서 클래스 계층을 표현해야한다.

  - 추상클래스로 계층구조를 만들기 위해 많은 조합이 필요하고 결국엔 고도비만 계층구조가 만들어진다.



- **인터페이스는 기능을 향상시키는 안전하고 강력한 수단이 된다.**
  - 래퍼클래스 (아이템 18)



## 인터페이스의 제약

자바 8 이후부터 인터페이스 내에 default 메서드를 정의할수 있다.



1. 디폴트 메서드를 제공할 때는 @implSpec 을 붙혀 문서화 하자.
2. equals 와 hashCode는 디폴트 메서드로 정의하면 안된다.
3. 인터페이스는 인스턴스 필드를 가질수 없다.
4. public이 아닌 정적 맴버도 가질 수 없다.(private static 메서드는 예외)
5. 내가 만들지 않은 인터페이스에는 디폴트 메서드를 추가할 수없다.



## 추상 클래스의 장점

앞서 살펴본 인터페이스의 제약 사항 과 반대되는 몇가지 특징을 가지고있다.

1. protected 추상 메서드를 정의 할 수 있다.
2. 인터페이스는 상수 프로퍼티만(public static) 가질수 있지만 추상 클래스는 아니다.





## 템플릿 메서드 패턴(인터페이스+추상골격 구현 클래스)



변하지 않는 기능은 슈퍼클래스에 만들어두고 자주 변경되며 확장할 기능은 서브 클래스에서 만들도록 한다.



템플릿 메서드 패턴을 사용하지 않은 코드

```java
public interface Phone {
    void booting(); //전원을 킵니다.
    void greeting();// 핸드폰 별 인사말
    void display();// 화면을 킵니다.
    void process(); //핸드폰 실행 프로세스
}
```



```java
public class IPhone implements Phone{
    @Override
    public void booting() {
        System.out.println("전원을킵니다");
    }
    @Override
    public void display() {
        System.out.println("화면을킵니다");
    }
    @Override
    public void greeting() {
        System.out.println("hello IPhone");
    }
    @Override
    public void process() {
        booting();
        display();
        greeting();
    }
```



```java
public class GalaxyPhone implements Phone {
    @Override
    public void greeting() {
        System.out.println("hello Galaxy");
    }
  @Override
    public void booting() {
        System.out.println("전원을킵니다");
    }
    @Override
    public void display() {
        System.out.println("화면을킵니다");
    }

    @Override
    public void process() {
        booting();
        display();
        greeting();
    }
}
```



코드를 보면 중복코드가 매우 많다.

해당 코드를 변경해보자.



일단 중복 코드는  booting() , display() ,process()  이다.

해당 코드를 추상 클래스로 묶어보자.

여기서 네이밍 패턴은 Abstract(name).class 이다 .

```java
public abstract class AbstractPhone implements Phone{ // Abstract(Phone) 

    @Override
    public void booting() {
        System.out.println("booting");
    }

    @Override
    public void display() {
        System.out.println("turn on display");
    }

    @Override
    public void process() {
        booting();
        greeting();
        display();
    }
}
```



생각을해보자.

클라이언트 측에서 booting() ,display() 메서드를 알고 있어야할까?(public 접근지시자가 맞을까??)

사용자 입장에선 process() 메서드만 알고있어도 핸드폰이 작동하는 것이 좋을 것 같다.



이 처럼 내부에서는 재정의를 열어두고 외부 클라이언트에선 호출을 금하고 싶을때 protected 접근자를 이용하자.

만약 재정의를 하지 않는다면 private static 메서드로(자바9 이후) 으로 인터페이스내 에서 구현을 하자.



인터페이스에선 핵심메서드만 정의

```java
public interface Phone {
    void process();
}
```



**추상클래스**(추상 골격 구현 클래스)

```java
public abstract class AbstractPhone implements Phone{

    protected void booting() {
        System.out.println("booting");
    }

    protected void display() {
        System.out.println("turn on display");
    }

    protected abstract void greeting(); // 해당 부분만 구현체가 구현하게 한다..

    @Override
    public void process() {
        booting();
        greeting();
        display();
    }
}

```



**구현체**

```java
public class IPhone extends AbstractPhone{
    @Override
    protected void greeting() {
        System.out.println("hello IPhone");
    }
}

public class GalaxyPhone extends AbstractPhone {
    @Override
    protected void greeting() {
        System.out.println("hello Galaxy");
    }
}
```



실행코드.

```java
@Test
void process(){
    Phone iPhone = new IPhone();
    Phone galaxyPhone = new GalaxyPhone();

    iPhone.process();
    galaxyPhone.process();
}
```





## 결론

1. 일반적으로 다중 구현용 타입으로는 인터페이스가 가장 적합하다.
2. 복잡한 인터페이스라면 구현하는 수고를 덜어주는 골격 구현을 함께 제공하는 방법을 고려하자.
3. 골격 구현은 가능한 인터페이스의 default 메서드로 제공하여 그 인터페이스를 구현한 모든 곳에서 활용하자.