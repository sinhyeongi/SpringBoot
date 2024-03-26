package kr.ex.querydsl.repository;

import kr.ex.querydsl.dto.MemberSearchCond;
import kr.ex.querydsl.dto.MemberTeamDto;

import java.util.List;

public interface MemberCunstomRespository {
    public List<MemberTeamDto> searchByBuilder(MemberSearchCond condition);
    public List<MemberTeamDto> search(MemberSearchCond cond);
}
