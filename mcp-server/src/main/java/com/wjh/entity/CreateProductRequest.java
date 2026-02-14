package com.wjh.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.ai.tool.annotation.ToolParam;

@Data
@ToString
public class CreateProductRequest {
    @ToolParam(description = "商品的名称")
    private String productName;
    @ToolParam(description = "商品的品牌")
    private String brand;
    @ToolParam(description = "商品的简介(非必填)")
    private String description;

    @ToolParam(description = "商品的价格")
    private Integer price;
    @ToolParam(description = "商品的库存数量")
    private Integer stock;
    @ToolParam(description = "商品的状态，1表示上架，0表示下架，预售状态的值为2")
    private Integer status;
}
