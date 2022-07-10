package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name="member_id") //id이름 그대로 되게 하지 않기 위해
    private Long id;
    private String name;

    @Embedded //embeddable 둘중하나만 있어도 되는데 보통 둘다 씀
    private Address address;

    @OneToMany(mappedBy = "member") //멤버랑 주문사이 관계:멤버 입장에서는 하나의 회원이 여러개 상품 주문--> 일대다 관계
    //주인이 아니라 거울이다!를 적어준다.--> mappedBy 써주고, order 테이블의 member에 의해 매핑 된것.
    private List<Order> orders= new ArrayList<>();
}
