package com.netopstec.extensible;

import com.netopstec.extensible.elasticsearch.Goods;
import com.netopstec.extensible.elasticsearch.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhenye 2019/3/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private GoodsRepository goodsRepository;

    @Test
    public void addIndexTest() {
        // 创建索引(json/goods_setting.json中的设置开始生效)
        template.createIndex(Goods.class);
        // 设置索引对应字段的分词规则(json/goods_mapping.json中的设置开始生效)
        template.putMapping(Goods.class);
    }

    @Test
    public void deleteIndexTest () {
        // 删除索引
        template.deleteIndex(Goods.class);
    }

    @Test
    public void addDataTest() {
        // 添加一些测试数据
        List<Goods> goodsList = new ArrayList<>();
        Goods goods1 = new Goods(1,"智能機器人","小米", new Date());
        Goods goods2 = new Goods(2,"智能牙刷","小米", new Date());
        Goods goods3 = new Goods(3,"測試機器","測試", new Date());
        Goods goods4 = new Goods(4,"测试电脑","测试", new Date());
        Goods goods5 = new Goods(5,"杯子","测试", new Date());
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        goodsList.add(goods4);
        goodsList.add(goods5);
        goodsRepository.saveAll(goodsList);
    }

    @Test
    public void simpleSearchTest() {
        // 进行一些简单搜索的测试
        Iterable<Goods> allGoods = goodsRepository.findAll();
        allGoods.forEach(goods -> log.info(goods.toString()));

        String name = "测试";
        /*
         * 这里的`findByName`我们不能简单得理解为“精确搜索”或“模糊搜索”。
         * 返回的结果依赖于存储和搜索时各自采用的分词规则。
         * 根据goods_mapping.json中name的设置可知，存储和检索都是ik_max_word。
         * 因此，这里`findByName("测试")`，就是找  字段值的分词后有"测试"  的文档。
         */
        List<Goods> goodsByName = goodsRepository.findByName(name);
        log.info(goodsByName.toString());
    }

    @Test
    public void complexSearchTest() {
        // ElasticsearchRepository接口中，还有一个search()方法，允许我们灵活组装搜索条件
        /*
         * 以下面一个需求为例：
         * 1. 检索的goods中的name和brand字段，排序结果主要依赖name的匹配度
         * 2. 匹配brand时，支持简体/繁体关键字匹配
         * 3. 匹配name时，支持简体/繁体/中文拼音等关键字匹配
         */

//        String keywords = "";
//        String keywords = "測試哈哈哈 啦啦啦";
        String keywords = "ceshi";
//        String keywords = "测试";
        DisMaxQueryBuilder disMaxQueryBuilder = QueryBuilders.disMaxQuery();
        QueryBuilder queryBuilder1 = QueryBuilders.matchQuery("name", keywords).boost(2f);
        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("name.pinyin", keywords).boost(0.5f);
        disMaxQueryBuilder.add(queryBuilder1);
        disMaxQueryBuilder.add(queryBuilder2);
        SearchQuery searchQuery = new NativeSearchQuery(disMaxQueryBuilder);
        Page<Goods> goodsPage = goodsRepository.search(searchQuery);
        System.out.println("keywords = ["+ keywords + "]的检索结果为：" + goodsPage.getContent());
    }
}
