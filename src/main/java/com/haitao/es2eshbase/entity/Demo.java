package com.haitao.es2eshbase.entity;

import com.frameworkset.orm.annotation.ESId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Demo implements Serializable {

    @ESId(readSet = true,persistent = false)
    private Long demoId;

    private String testF1;

    private List<String> programming_languages;

    private Integer required_matches;
}
