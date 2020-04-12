package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable  // 어딘가에 내장 될 수 있음
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 기본생성자 없이 사용자정의 생성자만 넣으면 에러남
    protected Address() {
        // public보다는 protected로 설정하는 것이 그나마 더 안전
    }

    // @Setter를 제거하고 생성자에서 값을 초기화하여 변경불가능한 클래스 만들 것
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
