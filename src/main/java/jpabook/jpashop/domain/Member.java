package jpabook.jpashop.domain;

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
                                    // mappedBy= "member"는 Order 테이블에 있는 member 필드에 의해 매핑된 것(읽기전용)
    @OneToMany(mappedBy = "member")  // Member 입장에서 1:N 관계이므로 OneToMany
    private List<Order> orders= new ArrayList<>();

}
