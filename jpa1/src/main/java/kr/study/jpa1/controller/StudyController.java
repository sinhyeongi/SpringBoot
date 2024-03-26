package kr.study.jpa1.controller;

import kr.study.jpa1.domain.Member;
import kr.study.jpa1.domain.StudyRecode;
import kr.study.jpa1.form.StudyForm;
import kr.study.jpa1.repository.MemberRepository;
import kr.study.jpa1.service.MemberService;
import kr.study.jpa1.service.StudyRecodeSerivce;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/study")
public class StudyController {
    @Autowired
    MemberService serivce;
    @Autowired
    StudyRecodeSerivce studyRecodeSerivce;

    @GetMapping
    public String addForm(Model model){
        model.addAttribute("members", serivce.getList());
        return "study/addForm";
    }
    @PostMapping
    public String addStudyRocord(StudyForm form){
        log.trace("form ={}" , form);
        log.trace("localDate={}, localTime={}", LocalDate.now(), LocalTime.now());
        Member member = serivce.findById(form.getMemberLoginId());
        if(member == null){
            log.error(" {} 로그인 아이디가 존재하지 않음",form.getMemberLoginId());
            return "redirect:/";
        }
        studyRecodeSerivce.saveRecord(form,member);
        return "redirect:/";
    }
    @GetMapping("/records")
    public String getAllList(Model model){
        model.addAttribute("list",studyRecodeSerivce.getAllList());
        return "study/list";
    }
    @GetMapping("/{StudyId}")
    public String UpdateForm(@PathVariable("StudyId") Long StudyId, Model model){
        StudyRecode recode = studyRecodeSerivce.getById(StudyId);
        if(recode == null){
            return "redirect:/";
        }
        model.addAttribute("curdate",LocalDate.now());
        model.addAttribute("record",recode);
        return "study/updateForm";
    }
    @PostMapping("/update")
    public String updateRecode(@ModelAttribute StudyForm form ,@RequestParam Long id){
        StudyRecode recode = studyRecodeSerivce.getById(id);
        log.error("recode={}",recode.getStudyId());
        studyRecodeSerivce.UpdateRecord(form,recode);
        return "redirect:/study/records";
    }
    @DeleteMapping("/{id}")
    @ResponseBody
    public String DeleteRecord(@PathVariable Long id){
        return studyRecodeSerivce.DeleteRecord(id)?"ok":"no";
    }
    @Transactional
    public void deleteAllRecordByMember(Member member){

    }
}
