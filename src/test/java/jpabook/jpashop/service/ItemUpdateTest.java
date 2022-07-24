package jpabook.jpashop.service;

import jdk.jfr.StackTrace;
import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L);

        //TX
        book.setName("adf");
        //트랜잭션 안에서는 이름 바꿔치기 하고 커밋하면 jpa가 변경문 찾아서 업데이트 쿼리 자동으로 생성해서 반영
        //--> 변경 감지 == dirty checking

    }
}
