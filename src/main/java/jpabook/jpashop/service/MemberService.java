package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    // 생성자에서 제대로 injection이 되었는지 체크해주기 위해 final을 붙임. 컴파일 시점에서 체크 가능.
    private final MemberRepository memberRepository;

    //스프링 최근 버전에서는 생성자가 하나만 있는 경우에는 autowired injection을 해줌
    //@AllArgsConstructor 는 모든 필드를 생성해주는 어노테이션.
    //@RequiredArgsConstructor 는 final이 붙은 필드를 생성해주는 어노테이션.
/*    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /* 회원 가입 */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        //EXCEPTION
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    /* 회원 전체 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 회원 단건 조회 */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }


}
