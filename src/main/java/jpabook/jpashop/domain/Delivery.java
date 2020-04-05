package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // enum 타입은 @Enumerated 을 넣어야함
    @Enumerated(EnumType.STRING)  // ORDINAL(default)은 1,2,3,4 숫자로 들어감(문제: 0,1로 들어가는데 중간에 새로운게 추가되면 망하므로 STRING으로 쓸 것)
    private DeliveryStatus status; // READY, COMP
}
