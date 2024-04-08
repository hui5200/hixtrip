package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 订单详情表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@TableName(value = "sample", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderMainDetailDO {
    /**
     * 订单详情ID，数据库自动生成
     */
    @TableId
    private Long detailId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * SkuId
     */
    private String skuId;
    /**
     * 购买数量
     */
    private Integer amount;
}
