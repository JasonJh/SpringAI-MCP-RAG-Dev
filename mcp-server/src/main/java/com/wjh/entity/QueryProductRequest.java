package com.wjh.entity;

import com.wjh.enums.ListSortEnum;
import com.wjh.enums.PriceCompareEnum;
import lombok.Data;
import lombok.ToString;
import org.springframework.ai.tool.annotation.ToolParam;

@Data
@ToString
public class QueryProductRequest {
    /**
     * required = true会默认填充数据，所以查询的时候改成false
     */
    @ToolParam(description = "商品的编号",required = false)
    private String productId;
    @ToolParam(description = "商品的名称",required = false)
    private String productName;
    @ToolParam(description = "商品的品牌",required = false)
    private String brand;

    @ToolParam(description = "具体商品的价格的大小",required = false)
    private Integer price;
    @ToolParam(description = "商品的状态，1表示上架，0表示下架，预售状态的值为2",required = false)
    private Integer status;

    @ToolParam(description = "查询列表的排序",required = false)
    private ListSortEnum sortEnum;

    @ToolParam(description = "比较价格的大小",required = false)
    private PriceCompareEnum priceCompareEnum;
}
