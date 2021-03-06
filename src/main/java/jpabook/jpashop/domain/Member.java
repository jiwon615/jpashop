package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotEmpty
    private String name;

    @Embedded  // 내장타입을 포함했음
    private Address address;

    @JsonIgnore  // 엔티티 직접 노출 시, 양방향 매핑관계일 때 이 어노테이션 써줘야함(안그러면 양쪽 호출하며 무한루프)
    @OneToMany(mappedBy = "member")  // Member 입장에서 1:N 관계이므로 OneToMany. mappedBy= "member"는 Order 테이블에 있는 member 필드에 의해 매핑된 것(읽기전용)
    private List<Order> orders= new ArrayList<>();

}
