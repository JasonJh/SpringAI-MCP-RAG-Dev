package com.wjh.entity;

import com.wjh.enums.ListSortEnum;
import com.wjh.enums.PriceCompareEnum;
import lombok.Data;
import lombok.ToString;
import org.springframework.ai.tool.annotation.ToolParam;

@Data
@ToString
public class ModifyProductRequest {
    @ToolParam(description = "商品的编号",required = false)
    private String productId;
    @ToolParam(description = "商品的名称",required = false)
    private String productName;
    @ToolParam(description = "商品的品牌",required = false)
    private String brand;
    @ToolParam(description = "商品的简介",required = false)
    private String description;
    @ToolParam(description = "具体商品的价格的大小",required = false)
    private Integer price;
    @ToolParam(description = "商品的库存数量",required = false)
    private Integer stock;
    @ToolParam(description = "商品的状态，1表示上架，0表示下架，预售状态的值为2",required = false)
    private Integer status;

}
