package kr.boot.basic;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.boot.basic.domain.Hello;
import kr.boot.basic.domain.QHello;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class BasicApplicationTests {
    @Autowired
    EntityManager em;
    @Test
    void contextLoads() {
        Hello hello = new Hello();
        em.persist(hello);

        JPAQueryFactory query = new JPAQueryFactory(em);
        Hello result = query
                .selectFrom(QHello.hello)
                .fetchOne();
        assertThat(result).isEqualTo(hello);
    }

}
