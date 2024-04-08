CREATE TABLE IF NOT EXISTS `order_main` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `order_id` VARCHAR(36) NOT NULL COMMENT '订单ID',
    `buyer_id` BIGINT UNSIGNED NOT NULL COMMENT '买家ID',
    `seller_id` BIGINT UNSIGNED NOT NULL COMMENT '卖家ID',
    `money` DECIMAL(10, 2) NOT NULL COMMENT '购买金额',
    `pay_time` DATETIME COMMENT '支付时间',
    `pay_status` TINYINT UNSIGNED NOT NULL COMMENT '订单状态：CREATED-创建，SUCCESS-支付成功，FAILURE-支付失败，DUPLICATE-重复支付',
    `create_by` VARCHAR(255) COMMENT '创建人',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
    `update_by` VARCHAR(255) COMMENT '修改人',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
    `del_flag` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '删除标志：0-存在，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_order_id` (`order_id`),
    INDEX `idx_buyer_id` (`buyer_id`),
    INDEX `idx_seller_id` (`seller_id`),
    INDEX `idx_create_time` (`create_time`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

CREATE TABLE IF NOT EXISTS `order_detail` (
    `detail_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单详情ID',
    `order_id` VARCHAR(36) NOT NULL COMMENT '订单ID',
    `sku_id` VARCHAR(255) COMMENT 'SkuId',
    `amount` INT UNSIGNED COMMENT '购买数量',
    PRIMARY KEY (`detail_id`),
    INDEX `idx_order_id` (`order_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';


--表设计
--考虑到订单量较大，订单和详情应该是一对多的关系，故拆分两张表存储

--主表
-- 分库：根据买家ID（buyer_id）通过模算法进行分库，可以实现买家频繁查询我的订单时进行水平扩展和负载均衡。
-- 买家的订单数据被分散存储在不同的数据库中，使得数据库负载分散，提高了查询效率和系统的可伸缩性。
-- 分表：根据订单创建时间（create_time）基于实际情况按月或季度进行分表，以便实现按时间范围快速查询。
-- 订单数据根据创建时间被分散存储到不同的表中，使得按时间范围进行查询时可以避免全表扫描，
-- 索引设计：
-- 买家ID（buyer_id）、卖家ID（seller_id）、订单id（order_id）、订单创建时间（create_time）
-- 买家ID和卖家ID的索引可以提升根据买家或卖家进行订单查询的速度。
-- 订单创建时间的索引可以提升按时间范围进行查询的速度

--详情表
--分表：由于订单详情表通常与订单主表关联查询，可以考虑根据订单ID（order_id）进行分表
--分库：目前详情表相对较小，可以先不考虑分库

--实现上可以采用ShardingSphere进行实现
--除了分库分表外，也可以考虑采用ES方式进行查询