package com.haitao.es2eshbase.entity;

import lombok.Data;

@Data
public class CityTag {

    public CityTag(String tagName) {
        this.tagName = tagName;
    }

    private String tagName;

}
