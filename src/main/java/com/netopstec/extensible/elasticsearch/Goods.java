package com.netopstec.extensible.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Date;

/**
 * @author zhenye 2019/3/11
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "goods", type = "goodsInfo")
@Setting(settingPath = "json/goods_setting.json")
@Mapping(mappingPath = "json/goods_mapping.json")
public class Goods {

    @Id
    private Integer id;
//    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String name;
    private String brand;
    private Date date;

}
