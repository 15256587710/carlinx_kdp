package com.ecarxclub.app.listener;

import java.io.Serializable;

public class YshuResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int code;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "YshuResponse{\n" +//
               "\tcode=" + code + "\n" +//
               "\tmsg='" + msg + "\'\n" +//
               "\tdata=" + data + "\n" +//
               '}';
    }
}
