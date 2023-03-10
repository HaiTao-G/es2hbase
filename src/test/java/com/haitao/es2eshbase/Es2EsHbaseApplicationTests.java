package com.haitao.es2eshbase;

import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.template.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseSqlTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseTableTemplate;
import com.haitao.es2eshbase.entity.CityModel;
import com.haitao.es2eshbase.entity.CityTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class Es2EsHbaseApplicationTests {

    @Autowired
    private IHBaseTableTemplate tableTemplate;
    @Autowired
    private IHBaseAdminTemplate adminTemplate;
    @Autowired
    private IHBaseSqlTemplate sqlTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public void createNameSpace() {
        NamespaceDesc namespaceDesc = new NamespaceDesc();
        namespaceDesc.setNamespaceName("test_nn");
        namespaceDesc.addNamespaceProp("createdBy", "leojie");
        adminTemplate.createNamespaceAsync(namespaceDesc);
    }

    @Test
    public void createNameSpace1() {

    }

    @Test
    public void testCreateTable() {
        ColumnFamilyDesc f1 = new ColumnFamilyDesc.Builder()
                .familyName("f1")
                .build();
        ColumnFamilyDesc f2 = new ColumnFamilyDesc.Builder()
                .familyName("f2")
                .timeToLive(3600)
                .versions(3)
                .build();
        HTableDesc tableDesc = new HTableDesc.Builder()
                .defaultTableDesc("test_nn:test_table")
                .maxFileSize(51400000L)
                .addTableProp("hbase.hstore.block.storage.policy", "HOT")
                .addColumnFamilyDesc(f1)
                .addColumnFamilyDesc(f2)
                .build();

        HTableDesc hot = new HTableDesc.Builder()
                .defaultTableDesc("test_nn:leo_test1")
                .addTableProp("hbase.hstore.block.storage.policy", "HOT")
                .addColumnFamilyDesc(f1)
                .build();
        adminTemplate.createTable(hot);
    }


    @Test
    public void testSaveUser() {
        CityModel cityModel = new CityModel();
        cityModel.setCityId("10078");
        cityModel.setCityName("上海");
        cityModel.setCityAddress("上海市");
        cityModel.setCityArea(10000);
        cityModel.setTotalPopulation(200000);
        CityTag cityTag = new CityTag("123");
        cityModel.setCityTagList(new ArrayList<CityTag>() {{
            add(cityTag);
        }});
        tableTemplate.save(cityModel);
    }

    @Test
    public void testToSave() {
        Map<String, Object> data = new HashMap<>();
        data.put("info1:addresses", Arrays.asList("广州", "深圳"));
        data.put("info1:username", "leo");
        data.put("info1:age", 18);
        data.put("INFO2:IS_VIP", true);
        data.put("info1:pay", 10000.1d);
        data.put("info1:create_by", "tom");
        data.put("info1:create_time", System.currentTimeMillis());
        Map<String, Object> contactInfo = new HashMap<>(2);
        contactInfo.put("email", "2326130720@qq.com");
        contactInfo.put("phone", "18739577988");
        contactInfo.put("address", "浦东新区");
        data.put("info1:contact_info", contactInfo);
        tableTemplate.save("TEST:123", "10002", data);
        System.out.println("用户数据保存成功！");
    }

    @Test
    public void testToSaveBatch() {
        Map<String, Map<String, Object>> data = new HashMap<>();

        Map<String, Object> data1 = new HashMap<>();
        data1.put("f1:username", "kangkang");
        data1.put("f1:age", 18);
        data1.put("f1:IS_VIP", true);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("f1:username", "jane");
        data2.put("f1:age", 18);
        data2.put("f1:IS_VIP", false);

        data.put("12036", data1);
        data.put("11016", data2);

        tableTemplate.saveBatch("test_nn:leo_test", data);
        System.out.println("用户数据批量保存成功！");
    }


}
