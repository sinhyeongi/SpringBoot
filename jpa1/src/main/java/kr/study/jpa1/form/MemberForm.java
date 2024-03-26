package kr.study.jpa1.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
// 화면(프레젠테이션 레벨) 받아온 member data
public class MemberForm {
    private String id;
    private String pw;
    private String name;

}
