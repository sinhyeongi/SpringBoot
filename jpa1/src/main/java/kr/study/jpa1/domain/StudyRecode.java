package kr.study.jpa1.domain;

import jakarta.persistence.*;
import kr.study.jpa1.form.StudyForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId; // study_id

    private LocalDate studyDay; // 미래시간 선택x 현재, 과거 선택
    private LocalTime startTime; // 오후 6:10
    private int studyMins; // 40
    @Lob
    private String contents;
    // fk 값을 가지는가? (member_id),  == 연관관계의 주인 == @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member; //fk => member_id

    public static StudyRecode createRecord(StudyForm form, Member member){
        StudyRecode recode = new StudyRecode();
        recode.member = member;
        recode.studyDay = form.getStudyDay();
        recode.startTime = form.getStartTime();
        recode.studyMins = form.getStudyMins();
        recode.contents = form.getContents();
        return recode;
    }
    public static void modifyRecord(StudyForm form,StudyRecode studyRecode){
        studyRecode.studyDay = form.getStudyDay();
        studyRecode.startTime = form.getStartTime();
        studyRecode.studyMins = form.getStudyMins();
        studyRecode.contents = form.getContents();

    }
}
