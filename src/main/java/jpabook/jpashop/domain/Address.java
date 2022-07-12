package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // JPA의 내장 타입이라는 것
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address(){
    }
    public Address(String city, String street, String zipcode) { //생성할때만 값 세팅, Setter 제공 안하는 게 좋음
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
