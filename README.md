# effective-java

:pencil2: 이펙티브 자바 3판 정리

만약.. 같이 진행하고 싶다면 얼마든지 환영입니다.
dkansk924@naver.com 으로 메일 보내주세요

---
## 아이템 정리규칙
아이템 15번 이후 부터는 다음과 같은 방식으로 정리하고자 한다.
1. 서론(각 아이템이 말하고자 하는것 간략 정리)
2. 본론(각 아이템이 소개하고 있는 장점,단점  자세히 정리)
3. 용어(책을 읽고 잘모르는 용어 정리)
4. 이해하지 못한 부분(책을 읽어도 잘 이해가 되지않는 부분을 마킹)
---
## 객체 생성자 파괴

1. [생성자 대신 정적팩터리 메서드를 고려하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item1/item1.md)
2. [생성자에 매개변수가 많다면 빌더를 고려하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item2/item2.md)
3. [private 생성자나 열거 타입으로 싱클턴임을 보증하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item3/item3.md)
4. [인스턴스화를 막으려거든 private 생성자를 사용하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item4/item4.md)
5. [자원을 직접 명시하지 말고 의존 객체 주입을 사용하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item5/item5.md)
6. [불필요한 객체 생성을 피하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item6/item6.md)
7. [다 쓴 객체 참조를 해제하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item7/item7.md)
8. finalizer와 cleaner 사용을 피하라 (pass)
9. [Try-finally 보다는 try-with-resources를 사용하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item9/item9.md)



## 모든 객체의 공통 메서드

10. [equals는 일반규약을 지켜 재정의하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item10/item10.md)
11. [equals를 재정의하려거든 hashcode도 재정의하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item11/item11.md)
12. [toString을 항상 재정의하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item12/item12.md)
13. [clone 재정의는 주의해서 진행하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item13/item13.md)
14. [Comparable을 구현할지 고려하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item14/item14.md)
15. [클래스와 맴버의 접근 권한을 최소화하라](https://github.com/KJJ924/effective-java/blob/master/src/main/java/com/jaejoon/demo/item15/item15.md)












