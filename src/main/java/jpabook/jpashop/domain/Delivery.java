package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy="delivery")
    private Order order; //one to one에서는 foreign 키 어디다 둬도 상관 없는데, 접근 많이 하는 곳에 foreign키 두는걸 선호하심.

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //string으로 꼭 써야한다. ordinal으로 넣으면 숫자로 들어간다. 중간에 다른 상태 생기면 1, 4, 2 이런식으로 되어 망한다.
    private DeliveryStatus status; //READY, ORDER
}
