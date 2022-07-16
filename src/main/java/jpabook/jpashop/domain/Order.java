package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id") //주문한 회원에 대한 정보 매핑
    private Member member; //멤버랑 관계 설정 - 다대일 관계 ; 반대로 멤버 입장에서는 하나의 회원이 여러개 상품 주문--> 일대다 관계

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    //private OrderStatus status; //주문 상태 [ORDER, CANCEL]
}
