package com.haitao.es2eshbase.entity;

import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseColumn;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseRowKey;
import com.github.CCweixiao.hbase.sdk.common.annotation.HBaseTable;
import lombok.Data;

import java.util.List;


@Data
@HBaseTable(namespaceName = "test_nn", tableName = "test_table", defaultFamilyName = "f1")
public class CityModel {
    /**
     * @HBaseRowKey注解用于定义某一个属性字段是用作存储rowKey数据的，是必须要设置的
     */
    @HBaseRowKey(rowKey = true)
    public String cityId;
    public String cityName;
    public String cityAddress;
    @HBaseColumn(familyName = "f2")
    public Integer cityArea;
    @HBaseColumn(familyName = "f2", toUpperCase = true)
    public Integer totalPopulation;
    @HBaseColumn(familyName = "f2", columnName = "cityTagList")
    public List<CityTag> cityTagList;

}
