package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@TableName(value = "sample", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderMainDO {
    /**
     * 自增ID
     */
    @TableId
    private Long id;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 买家ID
     */
    private Long buyerId;
    /**
     * 卖家ID
     */
    private Long sellerId;
    /**
     * 购买金额
     */
    private BigDecimal money;
    /**
     * 支付时间
     */
    private LocalDateTime payTime;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 订单更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 删除标志：0-存在，1-已删除
     */
    private Integer delFlag;
}
