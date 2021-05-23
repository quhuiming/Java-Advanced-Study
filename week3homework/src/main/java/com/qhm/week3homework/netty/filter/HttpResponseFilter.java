package com.qhm.week3homework.netty.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpResponseFilter {

    public void filter(FullHttpResponse response) {
        response.headers().set("homework", "getway");
    }
}
