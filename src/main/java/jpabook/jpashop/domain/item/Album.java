package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("A") //Single table 이라 저장될 떄 구분될 수 있어야하므로 넣는 값
public class Album extends Item{
    private String artist;
    private String etc;
}
