package org.csu.common;

import lombok.Getter;

//通用响应码，方便向前端返回数据
@Getter
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String description;

    ResponseCode(int code,String description){
        this.code = code;
        this.description = description;
    }
}
