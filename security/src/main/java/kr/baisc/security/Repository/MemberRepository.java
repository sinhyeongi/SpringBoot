package kr.baisc.security.Repository;

import kr.baisc.security.Entity.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
    Optional<Users> findByProviderAndProviderId(String provider,String providerId);
}
