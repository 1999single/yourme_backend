package com.single.yourme.core.nettywebsocket;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 1999single
 * @date: 2020/8/28 21:29
 */
@Slf4j
public class NioWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！");
        NioWebSocketChannelPool.channels.add(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");
        NioWebSocketChannelPool.channels.remove(ctx.channel());
        super.channelInactive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //首次连接是FullHttpRequest，处理参数 by zhengkai.blog.csdn.net
        if (null != msg && msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();

            Map paramMap=getUrlParams(uri);
            System.out.println("接收到的参数是："+JSON.toJSONString(paramMap));
            //如果url包含参数，需要处理
            if(uri.contains("?")){
                String newUri=uri.substring(0,uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }

        }else if(msg instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)msg;
            System.out.println("客户端收到服务器数据：" +frame.text());
            NioWebSocketChannelPool.channels.writeAndFlush(new TextWebSocketFrame(frame.text()));
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
//        log.info("数据类型：{}", frame.getClass());
//        if (frame instanceof PingWebSocketFrame) {
//            // pingWebSocketFrameHandler(ctx, (PingWebSocketFrame) frame);
//            log.info("二进制：{}", frame.toString());
//        } else if (frame instanceof TextWebSocketFrame) {
//            // textWebSocketFrameHandler(ctx, (TextWebSocketFrame) frame);
//            ctx.writeAndFlush(((TextWebSocketFrame) frame).text() + "\n");
//            System.out.println(frame.toString());
//        } else if (frame instanceof CloseWebSocketFrame) {
//            // closeWebSocketFrameHandler(ctx, (CloseWebSocketFrame) frame);
//            log.info("关闭：{}", frame.toString());
//        }
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info("数据类型：{}", msg.getClass());
//        if (msg instanceof FullHttpRequest) {
//            System.out.println("首次连接！");
//            fullHttpRequestHandler(ctx, (FullHttpRequest) msg);
//        }
//        super.channelRead(ctx, msg);
//    }

    /**
     * 处理连接请求，客户端WebSocket发送握手包时会执行这一次请求
     *
     * @param ctx
     * @param request
     */
    private void fullHttpRequestHandler(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        log.debug("接收到客户端的握手包：{}", ctx.channel().id());
        Map<String, String> params = getUrlParams(uri);
        log.debug("客户端请求参数：{}", params);
    }

    private static Map getUrlParams(String url){
        Map<String,String> map = new HashMap<>();
        url = url.replace("?",";");
        if (!url.contains(";")){
            return map;
        }
        if (url.split(";").length > 0){
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr){
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key,value);
            }
            return  map;

        }else{
            return map;
        }
    }
}

