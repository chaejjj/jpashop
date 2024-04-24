package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        Assertions.assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다!!!
        //java.lang.IllegalStateException: 이미 존재하는 회원입니다. <- 이 에러가 뜸
        // 아래 코드처럼 작성하면 지저분.. 그래서 Test 어노테이션에 expected 를 통해 해당 에러가 발생한다면 정상이야! 를 표시
/*        try {
            memberService.join(member2); // 예외가 발생해야 한다!!!
        } catch (IllegalStateException e) {
            return;
        }*/

        //then

        //fail() -> 코드가 여기까지 오면 안되는데 와버렸으면 그걸 알려주기 위함!!
        fail("예외가 발생해야 한다.");
    }



}