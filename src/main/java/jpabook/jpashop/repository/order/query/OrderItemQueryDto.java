package jpabook.jpashop.repository.order.query;

import lombok.Data;

@Data
public class OrderItemQueryDto {
    private Long orderId;
    private String itemId;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemId, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
