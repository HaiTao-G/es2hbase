package com.haitao.es2eshbase.entity;


import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseTable;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@HBaseTable(namespaceName = "swx", tableName = "news", defaultFamilyName = "f1")
public class HbaseNews implements Serializable {

    public String adp;

    public Long authoritativeMedia;

    public Integer baseClassType;

    public String boardName;

    public Integer collectNum;

    public Integer commentNum;

    public String content;

    public String contentKeyword;

    public String country;

    public Date createTime;

    public String dataSource;

    public Long floorNum;

    public Long forwardNum;

    public String gChKey;

    public String geoNames;

    public String groupId;

    public String groupName;

    public String hasMedia;

    public String hasPic;

    public String internal;

    public String isRubbish;

    public String languageCode;

    public Long likeNum;

    public String location;

    public Integer mOriginal;

    public String mid;

    public String newsId;

    public String nickName;

    public List<Map<String,Object>> ocr_result;

    public List<Map<String,Object>> ocr_results;

    public String ocr_text;

    public String orderIds;

    public String organizations;

    public String organizationsArray;

    public String pageUrl;

    public String persons;

    public String picUrls;

    public List<Map<String,Object>> picUrlsSource;

    public String picUrlsSourceJson;

    public String posterId;

    public Date publishTime;

    public Long readNum;

    public List<Map<String,Object>> recognition_results;

    public String recognition_types;

    public Double recommendScore;

    public List<Map<String,Object>> resource;

    public Long sentiment;

    public String siteName;

    public String title;

    public String userHomePage;

    public String userNickName;

    @HBaseRowKey
    public Long uuid;

    public String verifiedType;

    public String videoUrls;

    public List<Map<String,Object>> videoUrlsSource;

    public String videoUrlsSourceJson;

    public String messageSummary;

    public String contentHtml;
}
