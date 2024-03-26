package kr.ex.querydsl.repository;


import kr.ex.querydsl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepositoryJPA extends JpaRepository<Member,Long> , MemberCunstomRespository {
    List<Member> findByUsername(String name);
}
