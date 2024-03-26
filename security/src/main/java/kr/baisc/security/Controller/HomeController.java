package kr.baisc.security.Controller;

import kr.baisc.security.Entity.RoleUser;
import kr.baisc.security.Entity.Users;
import kr.baisc.security.Repository.MemberRepository;
import kr.baisc.security.auth.PricipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository repository;
    private final BCryptPasswordEncoder encoder;
    @RequestMapping("/")
    public String Home(){
        return "index";
    }
    @GetMapping("/loginForm")
    public String Login(){
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String Join(){
        return "JoinForm";
    }
    @PostMapping("/join")
    public String JoinPro(@ModelAttribute Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        log.trace("user={}",user);
        Users u = repository.save(user);
        log.trace("u={}",u);
        return "redirect:/loginForm";
    }
    @PostMapping("/login")
    public String loginPro(){
        return null;
    }
    @GetMapping("/admin")
    @ResponseBody
    public String AdminHome(){
        return "AMin Home";
    }
    @GetMapping("/manager")
    @ResponseBody
    public String MangerHome(){
        return "MangerHome";
    }
    @GetMapping("/user")
    @ResponseBody
    public String userHome(){
        return "userHome";
    }
    @GetMapping("/auth/login")
    public @ResponseBody String Login(String error,String exception){
        log.error("error = {}, exception = {}",error,exception);
        return exception.toString();
    }
    @GetMapping("/test")
    public @ResponseBody PricipalDetails test(@AuthenticationPrincipal PricipalDetails pricipalDetails){
        if(pricipalDetails == null){
            Users u = new Users("admin","admin","admin");

            pricipalDetails = new PricipalDetails(u);
            return pricipalDetails;
        }

        return pricipalDetails;
    }
   @GetMapping("/test/oauth")
    public @ResponseBody  OAuth2User testLogin(Authentication authentication,
                                               @AuthenticationPrincipal OAuth2User oauth){
        if(authentication == null){
            return null;
        }
        OAuth2User oAuth2User2 = (OAuth2User)authentication.getPrincipal();
        log.error("oauth = {}" ,oauth);
        return oAuth2User2;
    }
}
