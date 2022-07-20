package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //한 테이블에 다 때려 박음
@DiscriminatorColumn(name="dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name= "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스로직==//
    //재고 늘리고 줄이기 여기에 필요
    //엔티티 자체가 해결할 수 있는건 엔티티 안에 로직을 넣는 것이 좋다.
    //데이터 가지고 있는데서 비지니스 로직 나가는게 가장 응집도가 높다.
    /**
     * stock 증가
     * */
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }
    /**
     * stock 감소
     * */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
