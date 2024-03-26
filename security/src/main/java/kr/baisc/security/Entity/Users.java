package kr.baisc.security.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleUser role;
    @CreationTimestamp
    private Timestamp createDay;

    public Users(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = RoleUser.ROLE_USER;
    }

    private String provider; // Naver, google 구분
    private String providerId;
    @Builder
    public Users( String username, String password, String email, RoleUser role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
