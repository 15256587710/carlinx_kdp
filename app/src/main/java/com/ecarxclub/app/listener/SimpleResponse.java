package com.ecarxclub.app.listener;

import java.io.Serializable;

public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public int code;
    public String msg;

    public YshuResponse toLzyResponse() {
        YshuResponse yshuResponse = new YshuResponse();
        yshuResponse.code = code;
        yshuResponse.msg = msg;
        return yshuResponse;
    }
}
