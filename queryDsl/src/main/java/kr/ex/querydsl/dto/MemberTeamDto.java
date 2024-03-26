package kr.ex.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberTeamDto {
    private long memberId;
    private String username;
    private int age;
    private long teamId;
    private String teamName;
    // 해당 dto가  queryDsl 의존하게된다 ==> projection.constructor()


    @QueryProjection
    public MemberTeamDto(Long memberId, String username, int age, Long teamId,
                         String teamName) {
        this.memberId = memberId;
        this.username = username;
        this.age = age;
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
