package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em; //롤백이지만 db에 쿼리 남기는거 보고싶다! 그러면 이거 선언 후
    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception{
        //given 이러이러한게 주어졌을때
        Member member = new Member();
        member.setName("kim");
        //when 이렇게 하면
        Long savedId = memberService.join(member);
        //데베 트랜잭션이 커밋될때 flush 되면서 디비 insert 쿼리가 쫙 나가는 것.
        //근데 스프링에서는 기본적으로 커밋이 아닌 롤백을 해버린다. 따라서 @Rollback(false)를 주면 등록 쿼리 다 볼 수 있다.
        //then 이렇게 된다
        em.flush(); //
        Assert.assertEquals(member, memberRepository.findOne(savedId));
    }
    @Test(expected =  IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1= new Member();
        member1.setName("kim");
        Member member2= new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다.
        //then
        fail("예외가 발생해야 한다."); //코드가 돌다가 여기 오면 안됨
    }

}