package kr.ex.querydsl.entity;

import static org.assertj.core.api.Assertions.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest // 통합 테스트 |
@Transactional  // Transactional을 사영해야 db 값 전달 가능
class HelloTest {
    @Autowired
    EntityManager em;
    @Test
    void firstTest(){
        Hello h = new Hello();
        Hello h2 = new Hello();
        Hello h3 = new Hello();

        em.persist(h);
        em.persist(h2);
        em.persist(h3);
        List<Hello> list = em.createQuery("select h from Hello h", Hello.class).getResultList();
        list.forEach(n-> System.out.println(n));
    }
    @Test
    void Test(){
        Hello h = new Hello();
        Hello h2 = new Hello();
        Hello h3 = new Hello();

        em.persist(h);
        em.persist(h2);
        em.persist(h3);
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QHello qh = new QHello("hello");
        List<Hello> result = queryFactory.select(QHello.hello).from(QHello.hello).fetch();
        result.forEach(n-> System.out.println(n));
        Hello first = queryFactory.selectFrom(QHello.hello).fetchFirst();
        System.out.println("First = "+first);
        System.out.println("================== end ===============");
        assertThat(first).isEqualTo(result.get(0));
    }
}