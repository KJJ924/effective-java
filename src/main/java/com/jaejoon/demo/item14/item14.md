**ITEM 14**

# **Comparable을 구현할지 고려하라**



Comparable는 compareTo 메소드만 가지고있는 인터페이스다.

compareTo() 메소드는 equals() 메소드와 두가지 성격을 제외하고는 같다.



**compareTo() 는 단순 동치성비교에 더해 순서까지 비교가능 / 제네릭하다**



어떠한 클래스가 Comparable 을 구현하고 있다면  그 클래스는 자연적인 순서가 있음을 알 수 있다.

따라서 알파벳,숫자,연대 같이 순서가 명확한 값 클래스를 작성한다면 반드시 Comparable 인터페이스를 구현하자.



## compareTo() 메서드 규약



- this < other  :   -1 을 반환한다.
- this == other  :    0 을 반환한다.
- this > other  :    1 을 반환한다.



compareTo 메서드로 수행한 동치성 테스트 결과가 equals와 같아야함 (지켜지지 않는경우 반드시 명시해줘야함)

지켜지지 않는 경우  컬렉션 구현 인터페이스 (Collection,Set,Map) 에서 오작동이 발생한다.

정렬된 컬렉션들은 동치성을 비교할때 equals 를 이용하지 않고 compareTo 를 이용하기 때문이다.

```java
@Test
void compareToCollectionTest(){
    Set<BigDecimal> treeSet = new TreeSet<>();
    Set<BigDecimal> hashSet = new HashSet<>();
    BigDecimal decimal = new BigDecimal("1.0");
    BigDecimal decimal1 = new BigDecimal("1.00");

    treeSet.add(decimal);
    treeSet.add(decimal1);

    hashSet.add(decimal);
    hashSet.add(decimal1);

    // TreeSet 은  compareTo 를 이용
    assertThat(treeSet.size()).isEqualTo(1);
    
    // HashSet 은 equals() 를 이용
    assertThat(hashSet.size()).isEqualTo(2);
}
```





또한 앞서 equals(item 10)  규약과 똑같이 대칭성, 추이성, 반사성 을 충족해야한다.

그래서 주의해야 할 점도 같다.



## compareTo 메서드 작성 요령



- Comparable은 타입을 인수를 받는 제네릭 인터페이스이므로 compareTo 메서드의 인수타입은 **컴파일 타임에** 정해짐

  ->입력 인수의 타입을 확인하거나 형변환 할필요없음 / 타입이 잘못 입력되었다면 컴파일 시점에 잡을수 있음



- compareTo 메서드는 필드의 동치가아니라 순서를 비교한다

  ->(Comparable 을 구현하지 않은 필드나 표준이 아닌 순서로 비교해야한다면 Comparator 를 사용)



- compareTo 메서드를 구현시 관계연산자(<,>) 를 사용하지말고 박싱된 기본타입 클래스에 자바7 이상부터 추가된 정적 메서드 compare를 이용하자.

```java
public class Apple implements Comparable<Apple> {

    private int weight;  //Integer
    private Color color; //Enum.compare
    private String variety; //String.compare

    @Override
    public int compareTo(Apple o) {
        return Integer.compare(o.weight,weight); //박싱된 클래스에 있는 compare 를 이용하자
    }
}
```



- 클래스에 핵심필드가 여러개라면 핵심 필드부터 우선 비교하자.

  -> Apple 클래스에서 weight 가 핵심이여서 먼저 정렬 -> 만약 무게가 같으면 -> 두번쨰로 중요한 Color 필드를 비교 -> 색깔도 같다면 - > variety 필드 비교

```java
@Override
public int compareTo(Apple o) {
    int result = Integer.compare(o.weight, weight);
    if(result==0){
        result = color.compareTo(o.color);
    }
    if(result==0){
        result= variety.compareTo(o.variety);
    }
    return result;
}
```



- 자바 8 이상부터는 Compartor 인터페이스가 일련의 비교자 생성 메서드와 팀을 꾸려 메서드 연쇄방식으로 비교자를 생성 할수 있다.

  ->약간의 성능 저하가 있음

```java
@Override
public int compareTo(Apple o) {
    Comparator<Apple> comparator = Comparator.comparingInt(Apple::getWeight)
            .thenComparing(Apple::getColor)
            .thenComparing(Apple::getVariety);
    return comparator.compare(this,o);
}
```



- 값의 차를 이용한 compareTo, compare 메서드를 사용 하지말자

  -> 오버플로우 , 부동소수점 계산 방식에서 오류가 발생할 수 있다.

