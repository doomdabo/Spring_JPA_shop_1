package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") //Single table 이라 저장될 떄 구분될 수 있어야하므로 넣는 값
@Getter @Setter
public class Movie extends Item{

    private String director;
    private String actor;
}
