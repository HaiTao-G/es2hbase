package com.haitao.es2eshbase.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.github.CCweixiao.hbase.sdk.common.model.ColumnFamilyDesc;
import com.github.CCweixiao.hbase.sdk.common.model.HTableDesc;
import com.github.CCweixiao.hbase.sdk.common.model.NamespaceDesc;
import com.github.CCweixiao.hbase.sdk.template.IHBaseAdminTemplate;
import com.github.CCweixiao.hbase.sdk.template.IHBaseTableTemplate;
import com.haitao.es2eshbase.entity.EsNews;
import com.haitao.es2eshbase.entity.HbaseNews;
import lombok.extern.slf4j.Slf4j;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.tran.CommonRecord;
import org.frameworkset.tran.DataRefactor;
import org.frameworkset.tran.DataStream;
import org.frameworkset.tran.ExportResultHandler;
import org.frameworkset.tran.config.ImportBuilder;
import org.frameworkset.tran.context.Context;
import org.frameworkset.tran.plugin.custom.output.CustomOupputConfig;
import org.frameworkset.tran.plugin.custom.output.CustomOutPut;
import org.frameworkset.tran.plugin.es.input.ElasticsearchInputConfig;
import org.frameworkset.tran.schedule.TaskContext;
import org.frameworkset.tran.task.TaskCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MigrateService implements CommandLineRunner {

    @Autowired
    @Qualifier("bbossESStarterLogs")
    private BBossESStarter bbossESStarterLogs;

    @Autowired
    private IHBaseTableTemplate tableTemplate;

    @Autowired
    private IHBaseAdminTemplate adminTemplate;

    @Override
    public void run(String... args) {
        createHbaseTableName();
        ImportBuilder importBuilder = new ImportBuilder();
        importBuilder.setBatchSize(1000) //设置批量从源Elasticsearch中拉取的记录数
                .setFetchSize(5000); //设置批量写入目标Elasticsearch记录数
        /**
         * 输入端配置
         */
        ElasticsearchInputConfig elasticsearchInputConfig = new ElasticsearchInputConfig();
        elasticsearchInputConfig.setSourceElasticsearch("default");
        elasticsearchInputConfig.setDslFile("dsl.xml") //指定从源dbdemo表检索数据的dsl语句配置文件名称，可以通过addParam方法传递dsl中的变量参数值
                .setDslName("scrollAll") //指定从源dbdemo表检索数据的dsl语句名称，可以通过addParam方法传递dsl中的变量参数值
                .setScrollLiveTime("10m") // 指定scroll查询context有效期，这里是10分钟
//				.setDslName("scrollSliceAll") //指定从源dbdemo表检索数据的slice scroll检索对应的dsl语句名称，可以通过addParam方法传递dsl中的变量参数值
//				.setSliceQuery(true) // 指定scroll查询为slice查询
//				.setSliceSize(5) // 指定slice数量，与索引debdemo的shard数量一致即可
                .setQueryUrl("news_index_100340_new-2023.03.10/_search");// 指定从dbdemo索引表检索数据
        importBuilder.setInputConfig(elasticsearchInputConfig);

        /**
         * 重新设置es数据结构
         */
        importBuilder.setDataRefactor(new DataRefactor() {

            @Override
            public void refactor(Context context) throws Exception {
//
            }
        });

        /**
         * 设置数据bulk导入任务结果处理回调函数，对每次bulk任务的结果进行成功和失败反馈，然后针对失败的bulk任务通过error方法进行相应处理
         */
        importBuilder.setExportResultHandler(new ExportResultHandler<List, String>() {
            @Override
            public void success(TaskCommand<List, String> taskCommand, String result) {
                log.info("打印成功结果：{}", result);//打印成功结果
            }

            @Override
            public void error(TaskCommand<List, String> taskCommand, String result) {
                //具体怎么处理失败数据可以自行决定,下面的示例显示重新导入失败数据的逻辑：
                // 从result中分析出导入失败的记录，然后重新构建data，设置到taskCommand中，重新导入，
                // 支持的导入次数由getMaxRetry方法返回的数字决定
                // String failDatas = ...;
                //taskCommand.setDatas(failDatas);
                //taskCommand.execute();
                log.info("执行的批量数据:{}", JSON.toJSONString(taskCommand.getDatas()));//执行的批量数据
                log.info("打印失败结果：{}", result);//打印失败结果
            }

            @Override
            public void exception(TaskCommand<List, String> taskCommand, Throwable exception) {
                //任务执行抛出异常，失败处理方法,特殊的异常可以调用taskCommand的execute方法重试
                //if(need retry)
//                taskCommand.execute();
            }

            /**
             * 如果对于执行有错误的任务，可以进行修正后重新执行，通过本方法
             * 返回允许的最大重试次数
             * @return
             */
            @Override
            public int getMaxRetry() {
                return -1;
            }
        });

        /**
         * 全局删除字段
         */
        importBuilder.addIgnoreFieldMapping("_class");
        importBuilder.addIgnoreFieldMapping("addTime");
        importBuilder.addIgnoreFieldMapping("alarmScore");
        importBuilder.addIgnoreFieldMapping("boardId");
        importBuilder.addIgnoreFieldMapping("cluster1_ss");
        importBuilder.addIgnoreFieldMapping("clusterId");
        importBuilder.addIgnoreFieldMapping("contain_image");
        importBuilder.addIgnoreFieldMapping("contain_video");
        importBuilder.addIgnoreFieldMapping("contentTrans");
        importBuilder.addIgnoreFieldMapping("dataId");
        importBuilder.addIgnoreFieldMapping("dataType");
        importBuilder.addIgnoreFieldMapping("doc");
        importBuilder.addIgnoreFieldMapping("editTime");
        importBuilder.addIgnoreFieldMapping("esIndex");
        importBuilder.addIgnoreFieldMapping("fileUrls");
        importBuilder.addIgnoreFieldMapping("geoNamesArray");
        importBuilder.addIgnoreFieldMapping("guang_yiclasstype");
        importBuilder.addIgnoreFieldMapping("id");
        importBuilder.addIgnoreFieldMapping("organizationsArray");
        importBuilder.addIgnoreFieldMapping("personsArray");
        importBuilder.addIgnoreFieldMapping("postParentId");
        importBuilder.addIgnoreFieldMapping("query");
        importBuilder.addIgnoreFieldMapping("recommend");
        importBuilder.addIgnoreFieldMapping("tags");
        importBuilder.addIgnoreFieldMapping("titleTrans");
        importBuilder.addIgnoreFieldMapping("topic");
        importBuilder.addIgnoreFieldMapping("updateTime");
        importBuilder.addIgnoreFieldMapping("word_list");
        importBuilder.addIgnoreFieldMapping("@ingest_timestamp");

        /**
         * 自定义输出端es+hbase配置
         */
        CustomOupputConfig customOupputConfig = new CustomOupputConfig();
        customOupputConfig.setCustomOutPut(new CustomOutPut() {
            @Override
            public void handleData(TaskContext taskContext, List<CommonRecord> datas) {
                List<EsNews> esNewsList = new ArrayList<>();
                List<HbaseNews> hbaseNewsList = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    Map<String, Object> intputDatas = datas.get(i).getDatas();
                    if (intputDatas.containsKey("createTime") && Objects.nonNull(intputDatas.get("createTime"))){
                        Date createTime = DateUtil.parse(intputDatas.get("createTime").toString());
                        createTime = DateUtil.offsetHour(createTime,8);
                        intputDatas.put("createTime",createTime);
                    }
                    if (intputDatas.containsKey("publishTime") && Objects.nonNull(intputDatas.get("publishTime"))){
                        Date publishTime = DateUtil.parse(intputDatas.get("publishTime").toString());
                        publishTime = DateUtil.offsetHour(publishTime,8);
                        intputDatas.put("publishTime",publishTime);
                    }
                    EsNews esNews = BeanUtil.fillBeanWithMap(intputDatas,new EsNews(),false);
                    HbaseNews hbaseNews = BeanUtil.fillBeanWithMap(intputDatas,new HbaseNews(),false);
                    esNewsList.add(esNews);
                    hbaseNewsList.add(hbaseNews);
                }
                /**
                 * 新增es
                 */
                ClientInterface clientUtil = bbossESStarterLogs.getRestClient();
                clientUtil.addDocuments("news_hbase_index_100340",//索引表
                        "_doc",
                        esNewsList);
                /**
                 * 新增hbase
                 */
                tableTemplate.saveBatch(hbaseNewsList);
            }
        });
        importBuilder.setOutputConfig(customOupputConfig);

        /**
         * 并行执行
         */
//        importBuilder.setParallel(true);//设置为多线程并行批量导入,false串行
//        importBuilder.setQueue(10);//设置批量导入线程池等待队列长度
//        importBuilder.setThreadCount(5);//设置批量导入线程池工作线程数量
//        importBuilder.setContinueOnError(false);//任务出现异常，是否继续执行作业：true（默认值）继续执行 false 中断作业执行
//        importBuilder.setAsyn(false);//是否同步等待每批次任务执行完成后再返回调度程序，true 不等待所有导入作业任务结束，方法快速返回；false（默认值） 等待所有导入作业任务结束，所有作业结束后方法才返回

        /**
         * 作业执行
         */
        DataStream dataStream = importBuilder.builder();
        dataStream.execute();
    }

    public void createHbaseTableName(){
        if (!adminTemplate.namespaceIsExists("swx")){
            NamespaceDesc namespaceDesc = new NamespaceDesc();
            namespaceDesc.setNamespaceName("swx");
            adminTemplate.createNamespaceAsync(namespaceDesc);
        }
        if (!adminTemplate.tableExists("swx:news")){
            ColumnFamilyDesc f1 = new ColumnFamilyDesc.Builder()
                    .familyName("f1")
                    .build();
            HTableDesc news = new HTableDesc.Builder()
                    .defaultTableDesc("swx:news")
                    .addColumnFamilyDesc(f1)
                    .build();
            adminTemplate.createTable(news);
        }
    }
}
