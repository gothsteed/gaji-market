package com.gaji.app.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyPageDto {

    private String name;
    private double mannerTemp;
    private int onSaleCount;
    private int soldCount;
    private int reservationCount;
    private int likedProductCount;
    private int reviewCount;
    private String address;

}
