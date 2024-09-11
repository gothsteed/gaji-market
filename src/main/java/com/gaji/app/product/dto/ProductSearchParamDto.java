package com.gaji.app.product.dto;

import com.gaji.app.product.domain.CompleteStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSearchParamDto {
    private Integer page;
    private Integer size;
    private String title;
    private Integer minPrice;
    private Integer maxPrice;
    private String category;
    private Long fkMemberSeq;
    private List<CompleteStatus> completeStatus;
    private String sortType;


}
