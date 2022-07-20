package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;
    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
            //item은 jpa에 저장하기 전까지 id값이 없음
            //새로 생성하는 객체. 신규로 등록.
        } else{
            em.merge(item);
            //이미 DB에 등록된 객체 가져오는 것
            //쉽게 말해 update의 역할
        }
    }
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
