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