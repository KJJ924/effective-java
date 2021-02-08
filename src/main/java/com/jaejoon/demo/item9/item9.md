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