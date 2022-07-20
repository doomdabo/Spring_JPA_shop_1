package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") //주문한 회원에 대한 정보 매핑
    private Member member; //멤버랑 관계 설정 - 다대일 관계 ; 반대로 멤버 입장에서는 하나의 회원이 여러개 상품 주문--> 일대다 관계

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     *     persist(orderItemA)
     *     persist(orderItemB)
     *     persist(orderItemC)
     *     를 일일히 다 해줘야 하는데, cascade를 쓰면
     *     persist를 전파시킨다. 따라서
     *     persist(order)하나만 쓰면 된다.
     */

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
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
    //복잡한 생성은 별도의 생성 메소드가 있으면 좋다.
    public static Order createOrder(Member member, Delivery delivery,OrderItem... orderItems){
        //...쓰면 여러개 넘길 수 있다
        Order order =  new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    } //밖에서 set하는 방식이 아니라 생성할때부터 무조건 createOrder 호출해서 값을 넣어서
    // 생성 메소드에서 주문 생성에 대한 복잡한 비지니스를 완결. 응집성 있게!
    //주문 생성과 관련된건 여기만 고치면 된다
    //==비지니스로직==//
    /**
     * 주문 취소
     * 이미 배송된 상품 주문취소못하는 로직이 엔티티 안에 있다.
     * */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();

        }
    }
    //==조회로직==//
    /**
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice(){
        int totalPrice =0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
