package kr.baisc.security.Config.oauth;

import kr.baisc.security.Entity.RoleUser;
import kr.baisc.security.Entity.Users;
import kr.baisc.security.Repository.MemberRepository;
import kr.baisc.security.auth.PricipalDetails;
import kr.baisc.security.auth.provider.GoogleUserInfo;
import kr.baisc.security.auth.provider.NaverUserInfo;
import kr.baisc.security.auth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PricopalOauth2UserSerivce extends DefaultOAuth2UserService {
    private final MemberRepository repository;
    private final BCryptPasswordEncoder encoder;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //userRequest -> google 에서 받아온 코드
        System.out.println("userRequest.getClientRegistration = " + userRequest.getClientRegistration());
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken());
        OAuth2User auth2User = super.loadUser(userRequest);
        System.out.println("auth2User.getAttributes = " + auth2User.getAttributes());
        String provider = userRequest.getClientRegistration().getClientId();
        String providerId = auth2User.getAttribute("sub");
        System.err.println("provider = " + provider+"\nproviderId = " + providerId);
        String username = provider +"_"+providerId;
        String password = encoder.encode("test");
        String email = auth2User.getAttribute("email");
        Users user = repository.findByUsername(username);
        if(user == null){
            user = Users.builder()
                    .email(email)
                    .password(password)
                    .provider(provider)
                    .providerId(providerId)
                    .username(username)
                    .build();
            repository.save(user);
        }
        return new PricipalDetails(user,auth2User.getAttributes());
    }
    private OAuth2User processOAuthUser(OAuth2UserRequest userRequest,OAuth2User oAuth2User){
                // Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("요청 실패 ");
        }
        //System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
        //System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
        Optional<Users> userOptional =
                repository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        Users user;
        if (!userOptional.isPresent()) {
            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
            user = Users.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .role(RoleUser.ROLE_USER)
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            repository.save(user);
        }else{
            user = userOptional.get();
        }

        return new PricipalDetails(user, oAuth2User.getAttributes());
    }
}
