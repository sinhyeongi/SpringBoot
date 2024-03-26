package kr.ex.querydsl.Controller;

import jakarta.annotation.Nullable;
import kr.ex.querydsl.dto.MemberSearchCond;
import kr.ex.querydsl.dto.MemberTeamDto;
import kr.ex.querydsl.entity.Member;
import kr.ex.querydsl.repository.MemberRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberRepositoryJPA repository;
    @GetMapping("/")
    public String Home(){
        return "memberHome!";
    }

}
