package kr.baisc.security.auth;

import kr.baisc.security.Entity.Users;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PricipalDetails implements UserDetails, OAuth2User {
    private Users user;
    private Map<String,Object> attribute;



    public PricipalDetails(Users user){
        this.user = user;
    }
    public PricipalDetails(Users user,Map<String,Object> attribute) {
        this.user = user;
        this.attribute = attribute;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정 잠김여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //비밀번호 기간 지났는지 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정이 활성화 상태 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
