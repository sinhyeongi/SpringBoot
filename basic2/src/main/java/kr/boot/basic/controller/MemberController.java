package kr.boot.basic.controller;

import kr.boot.basic.domain.Member;
import kr.boot.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
    @Autowired
    MemberService service;
    @GetMapping("")
    public String list(Model model){
        List<Member> list = service.findMembers();
        model.addAttribute("members",list);
        return "members/memberList";
    }
    @GetMapping("/new")
    public String Join(){
        return "members/createMemberForm";
    }
    @PostMapping("/new")
    @ResponseBody
    public String Create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        return service.Join(member);
    }
}
