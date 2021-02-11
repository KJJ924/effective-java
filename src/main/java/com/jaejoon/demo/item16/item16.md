**ITEM 16**

# **public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라**



## 서론

public 필드를 통한 데이터의 접근은 캡슐화의 이점을 제공해주지 못한다.

또한 불변식을보장할 수 없으며 외부에서 필드에 접근할 때 부수 작업을 수행 할 수도 없다.





## 접근자메서드를 통해 접근하자!



public 클래스라면 패키지 바깥에서 접근할 수 있는 getter 메서드를 만들어주자.



만약 public 필드로 노출하게 되면 다음과 같은 문제점이 발생한다.

- 캡슐화의 이점을 제공해줄수 없다.
- API를 수정하지 않고는 내부 표현을 바꿀수 없다.(해당 문구는 Setter를 이용하면 유연하게 변경할 수 있다는 뜻 인것 같다.)
- 불변식을 보장할 수 없다.(이 부분도 public 필드로 들어갈 값들에 대한 검증을 할 수 없다. 하지만 Setter 메서드를 사용한다면 필드값들에 대해 어느정도 제약을 줄 수 있다.)
- 외부에서 필드에 접근할 때 부수작업을 추가하지 못한다.





package-private 클래스 혹은 private 중첩 클래스라면 데이터 필드를 노출해도 하등의 문제가 없다.

- package-private 클래스인 경우 패키지 내부에서 사용하는 코드이다.(즉 패키지 바깥 코드는 전혀 손대지 않고 데이터 표현방식을 변경가능)
- private 중첩 클래스 인경우 더욱 수정범위가 좁아 이 클래스를 포함하는 외부 클래스까지로 제한된다.



 

## 결론

public 클래스는 절대 가변 필드를 직접 노출해서는 안된다.

불변 필드라면 노출해도 덜 위험하지만 완전히 안심할 수는 없다.

하지만 package-private 클래스나 private 중첩 클래스에서는 종종 필드를 노출하는 편이 나을 때도 있다.

## 예시코드

```java
public class User { //public class 일때.

    private String name; //해당 클래스의 필드의 맴버를 private 으로 감추자.
    private int age;
    private String phone;


    public String getName() { // getter 메서드를 통해 데이터를 주자.
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        //기존 저장형식이 01012345678 인 경우
        // 클라이언트에게 넘겨줄때는 010-0000-0000으로 넘겨줄수있다.
        return this.phone.replaceFirst("(^[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    }

    public void setName(String name) {
        this.name = name;
    }

    // setter 를 쓰게되면 불변객체로 보장을 해주지못한다는데..?
    // 하지만 제공하지 않아도 얼마든지  클라이언트 측에서 리플렉션을 이용하여 얼마든지 조작이가능하긴한데?
    // 해당 주제에서는 벗어나니 일단지나가자.
    public void setAge(int age) {
        if(age<0) {
            throw new IllegalArgumentException("음수값을 가질 수 없습니다"); //해당코드 처럼 불변식을 보장해줄수 있다
        }
        this.age = age;
    }

    public void setPhone(String phone) {
        if(!phone.matches("[0-9]{11}")){
            throw new IllegalArgumentException("11자리수를 맞춰주세요"); //간단하게 자리수 만 확인..
        }
        this.phone = phone;
    }
}
```



## Test 코드

```java
class Item16Test {
    @Test
    void userExceptionTest(){
        User user = new User();

        assertThatThrownBy(() -> user.setAge(-10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("음수값을 가질 수 없습니다");
    }

    @Test
    void userPhone(){
        User user = new User();
        assertThatThrownBy(()->user.setPhone("010"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("11자리수를 맞춰주세요");
    }

    @Test
    void userGetPhone(){
        User user = new User();
        user.setPhone("01000000000");

        assertThat(user.getPhone()).isEqualTo("010-0000-0000");
    }
}
```