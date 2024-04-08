package com.hixtrip.sample.domain.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    CREATED("CREATED", "创建"),
    SUCCESS("SUCCESS", "支付成功"),
    FAILURE("FAILURE", "支付失败"),
    DUPLICATE("DUPLICATE", "重复支付"),
    ;

    private final String key;

    private final String value;

}