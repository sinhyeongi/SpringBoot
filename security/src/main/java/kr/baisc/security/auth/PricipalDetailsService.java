package kr.baisc.security.auth;

import kr.baisc.security.Entity.Users;
import kr.baisc.security.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PricipalDetailsService implements UserDetailsService {
    private final MemberRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users userEntity = repository.findByUsername(username);
        if(userEntity != null){
            System.err.println("userEntity = " + userEntity);
            return new PricipalDetails(userEntity);
        }
        throw new UsernameNotFoundException("no User");
    }
}
