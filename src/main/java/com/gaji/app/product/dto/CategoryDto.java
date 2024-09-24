package com.gaji.app.product.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	
	private Long categorySeq;
    private String name;

    public CategoryDto(Long categorySeq, String name) {
        this.categorySeq = categorySeq;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "categorySeq=" + categorySeq +
                ", name='" + name + '\'' +
                '}';
    }

}
