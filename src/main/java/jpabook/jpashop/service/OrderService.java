package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /**
     * 주문
     * */
    @Transactional //데이터 변경하는 거라 필요
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();

        delivery.setAddress(member.getAddress());//회원의 배송지로 주소 설정

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);
        //다른 방식으로 생성 못하도록 @NoArgsConstructor(access = AccessLevel.PROTECTED)을 OrderItem에 추가

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        //원래는 deliveryRepository 만들어서 dR.save() 해서 넣어주고 order도 그렇게 해야한다.
        //그런데 cascade = CascadeType.ALL 떄문에 order persist하면 orderItem도 다 persist된다.
        //delivery도 cascade 걸어놔서 하나만 저장해도 orderItem, delivery가 자동으로 Persist 된것이다.

        //cascade 범위는 보통 order가 delivery를 관리, order가 orderItem을 관리. delivery, orderItem은
        //order만 참조해서 쓴다. 라이프사이클에 대해 동일하게 관리, private 오너인 경우 유용하다.
        //다른데서도 참조하고 갖다 쓰는 경우 cascade 막쓰면 안된다. 하나 지우면 다지워질 수도 있다.

        return order.getId();
    }
    /**
     * 주문취소
     * */
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
        //JPA를 활용하면 데이터만 바꾸면 JPA가 알아서 dutty checking 해서 변경내용 찾아서 쿼리 업데이트가 알아서 된다.
    }
    //검색
    /*public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }*/
}
