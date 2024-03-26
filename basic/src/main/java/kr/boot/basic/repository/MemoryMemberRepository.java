package kr.boot.basic.repository;

import kr.boot.basic.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long,Member> store = new HashMap<>();
    private static long squene = 0L;
    @Override
    public Member save(Member member) {
        member.setId(++squene);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {

        return store.values().stream()
                .filter(m -> m.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
