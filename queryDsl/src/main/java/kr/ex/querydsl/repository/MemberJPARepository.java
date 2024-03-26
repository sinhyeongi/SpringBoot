package kr.ex.querydsl.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.ex.querydsl.dto.MemberSearchCond;
import kr.ex.querydsl.dto.MemberTeamDto;
import kr.ex.querydsl.dto.QMemberTeamDto;
import kr.ex.querydsl.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.ex.querydsl.entity.QMember.member;
import static kr.ex.querydsl.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberJPARepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    @Transactional
    public void save(Member membmer){
        em.persist(membmer);
    }
    public Optional<Member> findById(Long id){
        Member findmember = em.find(Member.class,id);
        return Optional.ofNullable(findmember);
    }
    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class).getResultList();
    }
    public List<Member> findByUsername(String userName){
        return em.createQuery("select m from Member m where m.username=:userName",Member.class).
                setParameter("userName",userName)
                .getResultList();
    }
    public List<Member> findAll_QueryDsl(){
        return queryFactory.selectFrom(member).fetch();
    }
    public List<Member> findByUserName_QueryDsl(String username){
        return queryFactory.selectFrom(member).where(member.username.eq(username)).fetch();
    }
    public List<Member> findByUser_QueryDsl(Long id,String username){
        return queryFactory.selectFrom(member)
                .where(QueryDslBuilder(id,username))
                .fetch();
    }
    private BooleanBuilder QueryDslBuilder(Long id,String username){
        BooleanBuilder builder = new BooleanBuilder();
        if(id != null){
            builder.and(member.id.eq(id));
        }
        if(username != null && username.length() > 3){
            builder.and(member.username.eq(username));
        }
        return builder;
    }
    //Builder 사용 -> 동적쿼리  -> DTO로 조회
//회원명, 팀명, 나이(ageGoe, ageLoe)
    public List<MemberTeamDto> searchByBuilder(MemberSearchCond condition) {
        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getUsername())) {
            builder.and(member.username.eq(condition.getUsername()));
        }
        if (hasText(condition.getTeamName())) {
            builder.and(team.name.eq(condition.getTeamName()));
        }
        if (condition.getAgeGoe() != null) {
            builder.and(member.age.goe(condition.getAgeGoe()));
        }
        if (condition.getAgeLoe() != null) {
            builder.and(member.age.loe(condition.getAgeLoe()));
        }

        return queryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name))
                .from(member)
                .leftJoin(member.team, team)
                .where(builder)
                .fetch();


    }

}
