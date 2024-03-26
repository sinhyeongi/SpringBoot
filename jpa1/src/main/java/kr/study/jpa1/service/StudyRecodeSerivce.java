package kr.study.jpa1.service;

import kr.study.jpa1.domain.Member;
import kr.study.jpa1.domain.StudyRecode;
import kr.study.jpa1.form.StudyForm;
import kr.study.jpa1.repository.StudyRecodeRepositroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyRecodeSerivce {
    private final StudyRecodeRepositroy studyRecodeRepositroy;

    @Transactional
    public void saveRecord(StudyForm form, Member member){
        StudyRecode s =  StudyRecode.createRecord(form,member);
        studyRecodeRepositroy.save(s);

    }
    public List<StudyRecode> getAllList(){
        return studyRecodeRepositroy.selectAll();
    }
    public StudyRecode getById(Long StudyId){
        Optional<StudyRecode> recode = studyRecodeRepositroy.findById(StudyId);
        return recode.isPresent()?recode.get():null;
    }
    @Transactional
    public void UpdateRecord(StudyForm form,StudyRecode recode){
        StudyRecode.modifyRecord(form,recode);
        studyRecodeRepositroy.save(recode);
    }
    public List<StudyRecode> getUserList(Member member){
        return studyRecodeRepositroy.searchStudyRecodeByMember(member.getId());
    }
    @Transactional
    public boolean DeleteRecord(Long id){
        studyRecodeRepositroy.deleteById(id);
        return !studyRecodeRepositroy.existsById(id);
    }

}
