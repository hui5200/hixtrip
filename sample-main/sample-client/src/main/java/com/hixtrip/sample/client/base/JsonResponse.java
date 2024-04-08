package com.hixtrip.sample.client.base;

import lombok.Data;

@Data
public final class JsonResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    private JsonResponse(T data) {
        this.code = 0;
        this.msg = "";
        this.data = data;
    }

    private JsonResponse(int code, String message) {
        this.code = code;
        this.msg = message;
        this.data = null;
    }

    public static <T> JsonResponse<T> getInstance(T data) {
        return new JsonResponse<>(data);
    }

    public static <T> JsonResponse<T> getInstance(int code, String message) {
        return new JsonResponse<>(code, message);
    }
}