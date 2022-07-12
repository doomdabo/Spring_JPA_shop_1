package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.*;
import java.util.List;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em; //스프링이 엔티티 메니저 만들어서 주입해줌

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select  m from Member m", Member.class).getResultList();
        //jpql과 sql의 차이점이 좀 있다. jpql은 entity 객체를 대상으로 쿼리 진행
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }

}
