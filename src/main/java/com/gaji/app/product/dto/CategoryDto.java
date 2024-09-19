package com.gaji.app.product.dto;

public class CategoryDto {
	
	private Long categorySeq;
    private String name;

    public CategoryDto(Long categorySeq, String name) {
        this.categorySeq = categorySeq;
        this.name = name;
    }

    // Getters and Setters
    public Long getCategorySeq() {
        return categorySeq;
    }

    public void setCategorySeq(Long categorySeq) {
        this.categorySeq = categorySeq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
