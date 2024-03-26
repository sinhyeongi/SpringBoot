package kr.ex.querydsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class MemberDTO {
    private String username;
    private int age;
    public MemberDTO() {
    }
    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
