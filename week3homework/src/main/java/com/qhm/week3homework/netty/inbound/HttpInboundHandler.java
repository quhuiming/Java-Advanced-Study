package com.qhm.week3homework.netty.inbound;


import com.qhm.week3homework.netty.filter.HttpRequestFilter;
import com.qhm.week3homework.netty.outbound.OutBoundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    OutBoundHandler outBoundHandler=new OutBoundHandler();
    HttpRequestFilter filter=new HttpRequestFilter();
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        try {

            FullHttpRequest fullRequest=(FullHttpRequest) msg;
            String uri=fullRequest.uri();
            //channelsocket内容读取完成 出站
            outBoundHandler.outboundHandle(fullRequest,ctx,filter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
