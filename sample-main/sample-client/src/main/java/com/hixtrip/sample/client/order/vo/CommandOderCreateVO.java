package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;

/**
 *
 */
@Data
@Builder
public class CommandOderCreateVO {
    private String id;
    private String name;
    private String code;
    private String msg;
}
