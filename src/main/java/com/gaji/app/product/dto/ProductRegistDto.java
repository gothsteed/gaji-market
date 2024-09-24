package com.gaji.app.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRegistDto {
    private Long productSeq;        // PRODUCTSEQ
    private Long fkCategorySeq;    // FKCATEGORYSEQ 카테고리 외래 키
    private CategoryDto category; // CategoryDto 객체 추가
    private String title;          // TITLE
    private String content;        // CONTENT
    private Integer price;         // PRICE
    private String negoStatus;     // NEGOSTATUS (boolean 대신 String으로 처리)
    private String salesType;      // SALESTYPE
    private String completeStatus;  // COMPLETESTATUS
    private String writeDate;      // WRITEDATE (String으로 처리, 필요시 변환)
    private Integer reuploadCount; // REUPLOADCOUNT
    private Integer viewCount;     // VIEWCOUNT
    private String address;        // ADDRESS
    private String detailaddress;
    private Long fkMemberSeq;      // FKMEMBERSEQ (nullable이므로 Long)
    private String startDateTime;  // STARTDATETIME (String으로 처리, 필요시 변환)
    private String endDateTime;    // ENDDATETIME (String으로 처리, 필요시 변환)
    private Integer likeCount;     // LIKECOUNT
    private String keyword;        // KEYWORD
}
