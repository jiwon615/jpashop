//package jpabook.jpashop;
//
//import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//
//import javax.transaction.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////@RunWith(SpringRunner.class) => Junit4 사용시 추가, Junit5 사용시 생략.
//@SpringBootTest
//public class MemberRepositoryTest {
//
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    @Transactional  // 이거 없으면 다음과 같은 에러: No EntityManager with actual transaction available for current thread
//    @Rollback(false)  // 이게 없으면 @Transactional은 바로 실행한 뒤 데이터는 rollback 해버리므로, rollback 원치 않을 시 추가
//    public void testMember() throws Exception {
//        // given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        // when
//        Long savedId = memberRepository.save(member);
//        Member findMember = memberRepository.find(savedId);
//
//        // then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
//
//        // 같은 트랜잭션 안에서 조회하면 영속성 context가 동일하므로 아이디 값이 같다면 같은 entity로 보므로 true 나옴
//        System.out.println("findMember == member: " + (findMember == member));
//    }
//
//
//}