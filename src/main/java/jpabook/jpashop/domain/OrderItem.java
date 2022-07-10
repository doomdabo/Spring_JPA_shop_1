package jpabook.jpashop.domain;


import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name= "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne //하나의 주문이 여러개 오더 아이템 가질 수 있음
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count; //주문 수량
}
