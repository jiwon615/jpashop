package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")  // 테이블 이름을 적어줌. 안그러면 Order가 되버리니..
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")  // foreign key 아이디 값이 member_id이 됨
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  // OrderItem 테이블의 order 에 의해 매핑됨
    private List<OrderItem> orderItems = new ArrayList<>();
    /* cascade 없다면 persist(orderItemA) persist(orderItemB) persist(orderItemC)
       cascade 있다면 persist(order)
    */

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  // CascadeType.ALL : Order가 persist되면 Delivery도 persist
    @JoinColumn(name = "delivery_id")  // 연관관계의 주인을 Order 쪽으로 정했기에 이거 써줌. 주인이 아닌 거울쪽은 mapped by 씀
    private Delivery delivery; // 배송정보

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


    //== 양방향일 때!! : 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem  orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //==비지니스 로직==//
    /**
     *주문 취소
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     *전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return  totalPrice;
    }

}
