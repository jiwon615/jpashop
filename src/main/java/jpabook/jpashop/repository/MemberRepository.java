package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository  // 메인 메소드 실행시 컴포넌트 스캔 대상이 되서 빈으로 등록 다 됨
@RequiredArgsConstructor
public class MemberRepository {

    /*
    * @PersistenceContext
    * private EntityManager em;
    * */
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);  // 멤버객체 넣고 트랜잭션이 커밋되는 시점에 디비에 반영 됨
    }

    public  Member findOne(Long id) {

        return em.find(Member.class, id);  // 단건조회
    }

    public List<Member> findAll() {
                                    // JPA ql에서는 from의 대상이 테이블이 아닌 엔티티
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 특정 회원 찾기
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
