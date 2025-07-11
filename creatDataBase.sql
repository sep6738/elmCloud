-- =================================================================
-- ElmCloud Project - Database Initialization Script
-- =================================================================

-- -----------------------------------------------------------------
-- 1. User Service Database (elm_user)
-- -----------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `elm_user`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `elm_user`;

-- 表：user (用户信息表)
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                        `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
                        `password` VARCHAR(255) NOT NULL COMMENT '密码 (BCrypt加密)',
                        `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
                        `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
                        `gender` TINYINT DEFAULT '0' COMMENT '性别: 0-未知, 1-男, 2-女',
                        `status` TINYINT DEFAULT '1' COMMENT '用户状态: 1-正常, 0-禁用',
                        `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';

-- 表：user_address (用户收货地址表)
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
                                `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '地址ID',
                                `user_id` BIGINT NOT NULL COMMENT '用户ID',
                                `contact_name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
                                `contact_phone` VARCHAR(20) NOT NULL COMMENT '收货人电话',
                                `province` VARCHAR(50) DEFAULT NULL COMMENT '省份',
                                `city` VARCHAR(50) DEFAULT NULL COMMENT '城市',
                                `district` VARCHAR(50) DEFAULT NULL COMMENT '区/县',
                                `detail_address` VARCHAR(255) NOT NULL COMMENT '详细地址',
                                `longitude` DOUBLE DEFAULT NULL COMMENT '经度',
                                `latitude` DOUBLE DEFAULT NULL COMMENT '纬度',
                                `is_default` TINYINT DEFAULT '0' COMMENT '是否为默认地址: 1-是, 0-否',
                                `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                                PRIMARY KEY (`id`),
                                KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户收货地址表';


-- -----------------------------------------------------------------
-- 2. Store Service Database (elm_store)
-- -----------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `elm_store`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `elm_store`;

-- 表：store (商家信息表)
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商家ID',
                         `name` VARCHAR(100) NOT NULL COMMENT '商家名称',
                         `logo` VARCHAR(255) DEFAULT NULL COMMENT '商家LOGO',
                         `description` TEXT COMMENT '商家描述',
                         `announcement` TEXT COMMENT '商家公告',
                         `address` VARCHAR(255) DEFAULT NULL COMMENT '商家地址',
                         `phone` VARCHAR(50) DEFAULT NULL COMMENT '联系电话',
                         `longitude` DOUBLE DEFAULT NULL COMMENT '经度',
                         `latitude` DOUBLE DEFAULT NULL COMMENT '纬度',
                         `open_time` TIME DEFAULT NULL COMMENT '营业开始时间',
                         `close_time` TIME DEFAULT NULL COMMENT '营业结束时间',
                         `min_delivery_amount` DECIMAL(10,2) DEFAULT '0.00' COMMENT '起送价',
                         `delivery_fee` DECIMAL(10,2) DEFAULT '0.00' COMMENT '配送费',
                         `rating` DECIMAL(3,2) DEFAULT '5.00' COMMENT '平均评分',
                         `monthly_sales` INT DEFAULT '0' COMMENT '月销量',
                         `avg_price` DECIMAL(10,2) DEFAULT '0.00' COMMENT '人均消费',
                         `category_id` BIGINT DEFAULT NULL COMMENT '商家分类ID',
                         `status` TINYINT DEFAULT '1' COMMENT '商家状态: 0-休息中, 1-营业中, 2-已关闭',
                         `tags` JSON DEFAULT NULL COMMENT '商家特色标签',
                         `delivery_range` DOUBLE DEFAULT NULL COMMENT '配送范围(公里)',
                         `estimated_delivery_time` INT DEFAULT NULL COMMENT '预估送达时间(分钟)',
                         `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家信息表';

-- 表：product_category (商品分类表)
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
                                    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
                                    `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
                                    `icon` VARCHAR(255) DEFAULT NULL COMMENT '分类图标',
                                    `store_id` BIGINT NOT NULL COMMENT '所属商家ID',
                                    `sort_order` INT DEFAULT '99' COMMENT '排序序号',
                                    `status` TINYINT DEFAULT '1' COMMENT '状态: 0-禁用, 1-启用',
                                    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- 表：product (商品信息表)
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品ID',
                           `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
                           `description` TEXT COMMENT '商品描述',
                           `image` VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
                           `price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',
                           `original_price` DECIMAL(10,2) DEFAULT NULL COMMENT '原价',
                           `store_id` BIGINT NOT NULL COMMENT '商家ID',
                           `category_id` BIGINT NOT NULL COMMENT '分类ID',
                           `sales_count` INT DEFAULT '0' COMMENT '销量',
                           `stock_count` INT DEFAULT '0' COMMENT '库存数量',
                           `status` TINYINT DEFAULT '1' COMMENT '商品状态: 0-下架, 1-上架',
                           `sort_order` INT DEFAULT '99' COMMENT '排序序号',
                           `tags` JSON DEFAULT NULL COMMENT '商品标签',
                           `specs` JSON DEFAULT NULL COMMENT '商品规格',
                           `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                           PRIMARY KEY (`id`),
                           KEY `idx_store_id_category_id` (`store_id`, `category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品信息表';


-- -----------------------------------------------------------------
-- 3. Order Service Database (elm_cloud_order)
-- -----------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `elm_cloud_order`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `elm_cloud_order`;

-- 表：orders (订单表)
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
                          `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                          `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
                          `user_id` BIGINT NOT NULL COMMENT '用户ID',
                          `store_id` BIGINT NOT NULL COMMENT '商家ID',
                          `store_name` VARCHAR(100) DEFAULT NULL COMMENT '商家名称',
                          `status` TINYINT NOT NULL COMMENT '订单状态: 0-待支付, 1-待接单, 2-备餐中, 3-待取餐, 4-配送中, 5-已送达, 6-已完成, 7-已取消',
                          `product_amount` DECIMAL(10,2) NOT NULL COMMENT '商品总价',
                          `package_fee` DECIMAL(10,2) DEFAULT '0.00' COMMENT '包装费',
                          `delivery_fee` DECIMAL(10,2) DEFAULT '0.00' COMMENT '配送费',
                          `discount_amount` DECIMAL(10,2) DEFAULT '0.00' COMMENT '优惠金额',
                          `actual_amount` DECIMAL(10,2) NOT NULL COMMENT '实际支付金额',
                          `payment_method` TINYINT DEFAULT NULL COMMENT '支付方式: 1-微信, 2-支付宝, 3-余额',
                          `payment_time` DATETIME DEFAULT NULL COMMENT '支付时间',
                          `address_id` BIGINT DEFAULT NULL COMMENT '收货地址ID',
                          `contact_name` VARCHAR(50) DEFAULT NULL COMMENT '收货人姓名',
                          `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '收货人电话',
                          `delivery_address` VARCHAR(255) DEFAULT NULL COMMENT '收货地址',
                          `remark` TEXT COMMENT '用户备注',
                          `estimated_delivery_time` DATETIME DEFAULT NULL COMMENT '预估送达时间',
                          `actual_delivery_time` DATETIME DEFAULT NULL COMMENT '实际送达时间',
                          `finish_time` DATETIME DEFAULT NULL COMMENT '完成时间',
                          `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_order_no` (`order_no`),
                          KEY `idx_user_id` (`user_id`),
                          KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 表：order_item (订单商品表)
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
                              `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单商品ID',
                              `order_id` BIGINT NOT NULL COMMENT '订单ID',
                              `product_id` BIGINT NOT NULL COMMENT '商品ID',
                              `product_name` VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
                              `product_image` VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
                              `product_spec` VARCHAR(100) DEFAULT NULL COMMENT '商品规格',
                              `unit_price` DECIMAL(10,2) NOT NULL COMMENT '商品单价',
                              `quantity` INT NOT NULL COMMENT '购买数量',
                              `subtotal` DECIMAL(10,2) NOT NULL COMMENT '小计金额',
                              `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`),
                              KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单商品表';

-- 表：shopping_cart (购物车表)
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
                                 `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
                                 `user_id` BIGINT NOT NULL COMMENT '用户ID',
                                 `store_id` BIGINT NOT NULL COMMENT '商家ID',
                                 `product_id` BIGINT NOT NULL COMMENT '商品ID',
                                 `product_name` VARCHAR(100) DEFAULT NULL COMMENT '商品名称',
                                 `product_image` VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
                                 `product_spec` VARCHAR(100) DEFAULT NULL COMMENT '商品规格',
                                 `unit_price` DECIMAL(10,2) NOT NULL COMMENT '商品单价',
                                 `quantity` INT NOT NULL COMMENT '购买数量',
                                 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_user_id_store_id` (`user_id`, `store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='购物车表';


-- -----------------------------------------------------------------
-- 4. Payment Service Database (elm_cloud_payment)
-- -----------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS `elm_cloud_payment`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `elm_cloud_payment`;

-- 表：payment (支付记录表)
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '支付记录ID',
                           `payment_no` VARCHAR(50) NOT NULL COMMENT '支付单号',
                           `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
                           `user_id` BIGINT NOT NULL COMMENT '用户ID',
                           `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
                           `payment_method` TINYINT DEFAULT NULL COMMENT '支付方式: 1-支付宝, 2-微信, 3-银行卡',
                           `status` TINYINT NOT NULL COMMENT '支付状态: 1-待支付, 2-支付中, 3-成功, 4-失败, 5-已退款',
                           `third_party_trade_no` VARCHAR(100) DEFAULT NULL COMMENT '第三方支付流水号',
                           `paid_at` DATETIME DEFAULT NULL COMMENT '支付完成时间',
                           `remark` TEXT COMMENT '备注',
                           `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `uk_payment_no` (`payment_no`),
                           KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付记录表';

-- 表：refund (退款记录表)
DROP TABLE IF EXISTS `refund`;
CREATE TABLE `refund` (
                          `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '退款记录ID',
                          `refund_no` VARCHAR(50) NOT NULL COMMENT '退款单号',
                          `payment_no` VARCHAR(50) NOT NULL COMMENT '支付单号',
                          `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
                          `user_id` BIGINT NOT NULL COMMENT '用户ID',
                          `amount` DECIMAL(10,2) NOT NULL COMMENT '退款金额',
                          `reason` TEXT COMMENT '退款原因',
                          `status` TINYINT NOT NULL COMMENT '退款状态: 1-退款中, 2-成功, 3-失败',
                          `third_party_refund_no` VARCHAR(100) DEFAULT NULL COMMENT '第三方退款流水号',
                          `refunded_at` DATETIME DEFAULT NULL COMMENT '退款完成时间',
                          `remark` TEXT COMMENT '备注',
                          `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `deleted` TINYINT DEFAULT '0' COMMENT '逻辑删除: 0-未删除, 1-已删除',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_refund_no` (`refund_no`),
                          KEY `idx_payment_no` (`payment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款记录表';

-- =================================================================
-- End of Script
-- =================================================================