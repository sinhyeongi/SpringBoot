package kr.study.jpa1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
//        System.out.println(" ==== home ====");
//        log.trace(" trace ");
//        log.debug(" debug ");
//        log.info(" info ");
//        log.warn(" warn ");
//        log.error(" error ");

        return "home/home";
    }
}
