package com.wjh.mcp.tool;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjh.entity.CreateProductRequest;
import com.wjh.entity.ModifyProductRequest;
import com.wjh.entity.Product;
import com.wjh.entity.QueryProductRequest;
import com.wjh.enums.ListSortEnum;
import com.wjh.enums.PriceCompareEnum;
import com.wjh.mapper.ProductMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@Slf4j
public class ProductTool {

    @Resource
    private ProductMapper productMapper;

    @Tool(description = "创建/新增商品信息记录")
    public String createNewProduct(CreateProductRequest createProductRequest){
        log.info("======== 调用mcp工具：createNewProduct() ========");
        log.info("| 参数 createProductRequest 为：{}",createProductRequest.toString());
        log.info("======== END ========");
        Product product = new Product();
        BeanUtils.copyProperties(createProductRequest,product);

        //生成12位的随机数字
        String id = RandomStringUtils.randomNumeric(12);
        log.info("id:{}",id);
        product.setProductId(id);

        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());

        productMapper.insert(product);

        return "商品信息创建成功";
    }

    @Transactional
    @Tool(description = "根据商品id删除商品记录")
    public String deleteProduct(String productId){
        log.info("======== 调用mcp工具：deleteProduct() ========");
        log.info("| 参数 productId 为：{}",productId);
        log.info("======== END ========");

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);
        productMapper.delete(queryWrapper);

        return "商品信息删除成功";
    }


    @Transactional
    @Tool(description = "把排序（正序/倒序）转换为对应的枚举")
    public ListSortEnum getSorEnum(String sort){
        log.info("======== 调用mcp工具：getSorEnum() ========");
        log.info("| 参数 sort 为：{}",sort);
        log.info("======== END ========");

        if (sort.equalsIgnoreCase(ListSortEnum.ASC.value)){
            return ListSortEnum.ASC;
        }else {
            return ListSortEnum.DESC;
        }
    }

    @Transactional
    @Tool(description = "把商品价格的比较（大于/小于/大于等于/小于等于/高于/低于/不高于/不低于/等于）转换为对应的枚举")
    public PriceCompareEnum getPriceCompareEnum(String priceCompare){
        log.info("======== 调用mcp工具：getPriceCompareEnum() ========");
        log.info("| 参数 priceCompare 为：{}",priceCompare);
        log.info("======== END ========");

        if (priceCompare.equalsIgnoreCase(PriceCompareEnum.GREATER_THAN.value)) {
            return PriceCompareEnum.GREATER_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.LESS_THAN.value)) {
            return PriceCompareEnum.LESS_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.GREATER_THAN_OR_EQUAL_TO.value)) {
            return PriceCompareEnum.GREATER_THAN_OR_EQUAL_TO;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.LESS_THAN_OR_EQUAL_TO.value)) {
            return PriceCompareEnum.LESS_THAN_OR_EQUAL_TO;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.HIGHER_THAN.value)) {
            return PriceCompareEnum.HIGHER_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.LOWER_THAN.value)) {
            return PriceCompareEnum.LOWER_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.NOT_HIGHER_THAN.value)) {
            return PriceCompareEnum.NOT_HIGHER_THAN;
        } else if (priceCompare.equalsIgnoreCase(PriceCompareEnum.NOT_LOWER_THAN.value)) {
            return PriceCompareEnum.NOT_LOWER_THAN;
        } else {
            return PriceCompareEnum.EQUAL_TO;
        }
    }

    @Tool(description = "根据条件查询商品（product）信息")
    public List<Product> queryProductListByCondition(QueryProductRequest queryProductRequest){

        log.info("======== 调用mcp工具：queryProductListByCondition() ========");
        log.info("| 参数 queryProductRequest 为：{}",queryProductRequest.toString());
        log.info("======== END ========");

        String productId = queryProductRequest.getProductId();
        String productName = queryProductRequest.getProductName();
        String brand = queryProductRequest.getBrand();
        Integer price = queryProductRequest.getPrice();
        Integer status = queryProductRequest.getStatus();
        ListSortEnum sortEnum = queryProductRequest.getSortEnum();
        PriceCompareEnum priceCompareEnum = queryProductRequest.getPriceCompareEnum();

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(productId)){
            queryWrapper.eq("product_id",productId);
        }
        if (StringUtils.isNotBlank(productName)) {
            queryWrapper.like("product_name", productName);
        }
        if (StringUtils.isNotBlank(brand)) {
            queryWrapper.like("brand", brand);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        if (price != null && priceCompareEnum != null) {
            if (priceCompareEnum.type.equals(PriceCompareEnum.GREATER_THAN.type)) {
                queryWrapper.gt("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.LESS_THAN.type)) {
                queryWrapper.lt("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.GREATER_THAN_OR_EQUAL_TO.type)) {
                queryWrapper.ge("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.LESS_THAN_OR_EQUAL_TO.type)) {
                queryWrapper.le("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.HIGHER_THAN.type)) {
                queryWrapper.gt("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.LOWER_THAN.type)) {
                queryWrapper.lt("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.NOT_HIGHER_THAN.type)) {
                queryWrapper.le("price", price);
            } else if (priceCompareEnum.type.equals(PriceCompareEnum.NOT_LOWER_THAN.type)) {
                queryWrapper.ge("price", price);
            } else {
                queryWrapper.eq("price", price);
            }
        }

        if (sortEnum != null && sortEnum.type.equals(ListSortEnum.ASC.type)) {
            queryWrapper.orderByAsc("price");
        }
        if (sortEnum != null && sortEnum.type.equals(ListSortEnum.DESC.type)) {
            queryWrapper.orderByDesc("price");
        }

        List<Product> productList = productMapper.selectList(queryWrapper);

        return productList;
    }


    @Tool(description = "根据商品的编号修改商品信息")
    public String modifyProduct(ModifyProductRequest modifyProductRequest){

        log.info("======== 调用mcp工具：modifyProduct() ========");
        log.info("| 参数 modifyProductRequest 为：{}",modifyProductRequest.toString());
        log.info("======== END ========");
        Product product = new Product();
        BeanUtils.copyProperties(modifyProductRequest,product);

        product.setUpdateTime(LocalDateTime.now());

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",product.getProductId());

        int update = productMapper.update(product,queryWrapper);
        if (update <= 0){
            return "商品信息修改失败或商品不存在";
        }else {
            return "商品信息修改成功";
        }
    }
}
