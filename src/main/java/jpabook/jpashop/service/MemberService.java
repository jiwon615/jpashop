package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // true를 주면 조회하는 곳에서는 성능 최적화가 되므로 읽을 땐 readOnly = true 넣기
@RequiredArgsConstructor  // final 키워드의 변수를 가진 것에 대해 생성자를 자동으로 생성해줌
public class MemberService {
    /*@Autowired // 스프링이 스프링 빈에 등록되어 있는 memberRepository를 injection 해줌
    private MemberRepository memberRepository;
    */
    private final MemberRepository memberRepository;

    // 위 주석한 것 처럼 바로 주입하는 것이 아닌, setter를 통해 들어온 뒤 주입 됨
    // 장점: 테스트 코드 작성 시 가짜 repository 만들어서 주입이 가능하니 편리함
   /* @Autowired    // @RequiredArgsConstructor가 자동으로 아래 생성자 만듦
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/


    // 회원가입
    @Transactional  // 수정, 삭제 등은 readOnly = true 쓰면 값 변경 안되므로 쓰지 X (default는 false)
    public Long join(Member member) {
        validateDuplicateMember(member);  // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
