package com.jaejoon.demo.item86;

import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SerializableTest {

    @Test
    @DisplayName("객체 직렬화")
    void serializable() throws IOException {
        Member member = new Member("KJJ", 26, "서울 어딘가");

        byte[] serializedMember;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(member);
                // serializedMember -> 직렬화된 member 객체
                serializedMember = baos.toByteArray();
            }
        }
        // 바이트 배열로 생성된 직렬화 데이터를 base64로 변환
        System.out.println(Base64.getEncoder().encodeToString(serializedMember));
    }

    @Test
    @DisplayName("역 직렬화")
    void deserializable() throws IOException, ClassNotFoundException {
        String base64Member = "rO0ABXNyAB5jb20uamFlam9vbi5kZW1vLml0ZW04Ni5NZW1iZXIAAAAAAAAAAQIAA0kAA2FnZUwAB2FkZHJlc3N0ABJMamF2YS9sYW5nL1N0cmluZztMAARuYW1lcQB+AAF4cAAAABp0ABDshJzsmrgg7Ja065SY6rCAdAADS0pK";

        byte[] serializedMember = Base64.getDecoder().decode(base64Member);
        Member member;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                // 역직렬화된 Member 객체를 읽어온다.
                Object objectMember = ois.readObject();
                member = (Member) objectMember;
            }
        }
        assertThat(member.getName()).isEqualTo("KJJ");
        assertThat(member.getAge()).isEqualTo(26);
        assertThat(member.getAddress()).isEqualTo("서울 어딘가");
        System.out.println(member);
    }

    private String getSerializedMember() throws IOException {
        Member member = new Member("KJJ", 26, "서울 어딘가");

        byte[] serializedMember;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(member);
                // serializedMember -> 직렬화된 member 객체
                serializedMember = baos.toByteArray();
            }
        }
        // 바이트 배열로 생성된 직렬화 데이터를 base64로 변환
        return Base64.getEncoder().encodeToString(serializedMember);
    }
}