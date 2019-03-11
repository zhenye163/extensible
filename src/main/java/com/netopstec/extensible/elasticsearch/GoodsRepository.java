package com.netopstec.extensible.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhenye 2019/3/11
 */
@Repository
public interface GoodsRepository extends ElasticsearchRepository<Goods, Integer>{

    List<Goods> findByName(String name);
}
