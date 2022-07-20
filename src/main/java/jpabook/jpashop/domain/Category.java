package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name="category_id")

    private Long id;
    private String name;

    @ManyToMany //예제일 뿐 필드. 다대다는 더 추가하는 거 안됨. 실무에선 복잡한거 많음.
    @JoinTable(name="category_item",joinColumns = @JoinColumn(name="category_id"), inverseJoinColumns = @JoinColumn(name="item_id"))
    //inverse는 category_item 테이블의 아이템 쪽으로 들어가는 것 매핑해줌
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent; //카테고리의 계층 구조->셀프로 양방향 연관관계

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}
