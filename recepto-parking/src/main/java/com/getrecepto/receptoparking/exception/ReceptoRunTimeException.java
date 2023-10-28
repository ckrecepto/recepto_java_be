package com.getrecepto.receptoparking.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ReceptoRunTimeException extends RuntimeException {
    private int statusCode;
    private String msg;

    public ReceptoRunTimeException(int statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ReceptoRunTimeException{" + "statusCode=" + statusCode + ", msg='" + msg + '\'' + '}';
    }
}
