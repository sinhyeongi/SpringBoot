package kr.ex.querydsl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchCond {
    //회원명 팀명 나이 gt,lt
    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
}
