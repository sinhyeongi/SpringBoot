package kr.boot.basic.service;

import jakarta.transaction.Transactional;
import kr.boot.basic.domain.Member;
import kr.boot.basic.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository repository;
    public MemberService(MemberRepository repository){
        this.repository = repository;
    }
    public String Join(Member m){
        String t = "<script>alert('이미존재하는 회원입니다.'); location.href='/members/new'</script>";
        if(m.getName() == null || m.getName().length() < 3){
            t = "<script>alert('이름이 너무 짧습니다.'); location.href='/members/new'</script>";
            return t;
        }else if(validateDuplicateMember(m)){
            return t;
        }
        repository.save(m);
        t = "<script>alert('회원가입 완료'); location.href='/members'</script>";
        return t;
    }
    public List<Member> findMembers(){
        return repository.findAll();
    }
    public Optional<Member> findOneMember(Long id){
        return repository.findById(id);
    }
    private boolean validateDuplicateMember(Member member){
        //repository.findById(member.getId())
        //         .ifPresent(n -> {throw new IllegalArgumentException("이미 존재하는 회원입니다.");});
        return repository.findByName(member.getName()).isPresent();
    }

}
