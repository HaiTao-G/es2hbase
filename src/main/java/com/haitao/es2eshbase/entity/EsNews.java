package com.haitao.es2eshbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.frameworkset.orm.annotation.Column;
import com.frameworkset.orm.annotation.ESId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class EsNews implements Serializable {

    private String adp;

    private Long authoritativeMedia;

    private Integer baseClassType;

    private String boardName;

    private Integer collectNum;

    private Integer commentNum;

    private String content;

    private String contentKeyword;

    private String country;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(dataformat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String dataSource;

    private Long floorNum;

    private Long forwardNum;

    @ESId(readSet = true,persistent = true)
    private String gChKey;

    private String geoNames;

    private String groupId;

    private String groupName;

    private String hasMedia;

    private String hasPic;

    private String internal;

//    @JsonProperty("isRubbish")
    private String isRubbish;

    private String languageCode;

    private Long likeNum;

    private String location;

    private Integer mOriginal;

    private String mid;

    private String newsId;

    private String nickName;

    private List<Map<String,Object>> ocr_result;

    private List<Map<String,Object>> ocr_results;

    private String ocr_text;

    private String orderIds;

    private String organizations;

    private String organizationsArray;

    private String pageUrl;

    private String persons;

    private String picUrls;

    private List<Map<String,Object>> picUrlsSource;

    private String picUrlsSourceJson;

    private String posterId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(dataformat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    private Long readNum;

    private List<Map<String,Object>> recognition_results;

    private String recognition_types;

    private Double recommendScore;

    private List<Map<String,Object>> resource;

    private Long sentiment;

    private String siteName;

    private String title;

    private String userHomePage;

    private String userNickName;

    private Long uuid;

    private String verifiedType;

    private String videoUrls;

    private List<Map<String,Object>> videoUrlsSource;

    private String videoUrlsSourceJson;

}
