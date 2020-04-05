package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(false)  // 이게 있으면 rollback을 안하고 commit을 하므로, 디비에 insert까지 될 수 있음
    public void 회원가입() throws Exception {
        // given  (이런게 주어졌을 때)
        Member member = new Member();
        member.setName("kim");

        // when  (이렇게 하면)
        Long savedId = memberService.join(member);

        // test  (이렇게 된다)
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);

        try {
            memberService.join(member2);  // 예외가 발생해야 한다!!
        } catch (IllegalStateException e) {
            return;
        }

        // test
        fail("예외가 발생해야 한다.");
    }

}