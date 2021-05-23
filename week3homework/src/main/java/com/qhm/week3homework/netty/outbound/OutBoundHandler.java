package com.qhm.week3homework.netty.outbound;


import com.qhm.week3homework.netty.filter.HttpRequestFilter;
import com.qhm.week3homework.netty.filter.HttpResponseFilter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class OutBoundHandler {
    HttpResponseFilter responseFilter=new HttpResponseFilter();
    public void outboundHandle(FullHttpRequest fullRequest, ChannelHandlerContext ctx, HttpRequestFilter filter){
        //设置请求头
        filter.filter(fullRequest,ctx);
        //后台地址
        String url="http://localhost:8802";
        fetchGet(fullRequest,ctx,url);

    }
    public void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpGet.setHeader("week3",inbound.headers().get("week3"));
        try {
            HttpResponse response=(HttpResponse) httpClient.execute(httpGet);
            handleResp(inbound,ctx,response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void handleResp(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpResponse response){
        FullHttpResponse fullHttpResponse=null;
        try {
            byte[] body = EntityUtils.toByteArray(response.getEntity());
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().setInt("Content-Length", Integer.parseInt(response.getFirstHeader("Content-Length").getValue()));
            //设置响应头
            responseFilter.filter(fullHttpResponse);
        } catch (IOException e) {
            e.printStackTrace();
            fullHttpResponse = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        }finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    //返回相应
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(fullHttpResponse);
                }
            }
            ctx.flush();

        }
    }
}
