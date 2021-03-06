package com.single.yourme.core.websocket;

import com.alibaba.fastjson.JSON;
import com.single.yourme.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author 1999single
 * @since 2019-12-08
 */
//@Slf4j
//@ServerEndpoint("/im/{userId}")
//@Component
//public class WebSocketServer {
//
//    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static int onlineCount = 0;
//    //旧：concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
//    //private static CopyOnWriteArraySet<ImController> webSocketSet = new CopyOnWriteArraySet<ImController>();
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//    //新：使用map对象，便于根据userId来获取对应的WebSocket
//    private static ConcurrentHashMap<String, WebSocketServer> websocketList = new ConcurrentHashMap<>();
//    //接收sid
//    private String userId = "";
//    /**
//     * 连接建立成功调用的方法*/
//    @OnOpen
//    public void onOpen(Session session, @PathParam("userId") String userId) {
//        this.session = session;
//        System.out.println(session);
//        websocketList.put(userId,this);
//        log.info("websocketList->"+ JSON.toJSONString(websocketList));
//        //webSocketSet.add(this);     //加入set中
//        addOnlineCount();           //在线数加1
//        log.info("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
//        this.userId=userId;
//        try {
//            sendMessage(JSON.toJSONString(Result.builder().success().build()));
//        } catch (IOException e) {
//            log.error("websocket IO异常");
//        }
//    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        if(websocketList.get(this.userId)!=null){
//            websocketList.remove(this.userId);
//            //webSocketSet.remove(this);  //从set中删除
//            subOnlineCount();           //在线数减1
//            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
//        }
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息*/
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("收到来自窗口"+userId+"的信息:"+message);
//        try {
//            if ("xjx".equals(message)) {
//                websocketList.get("syy").sendMessage("syy say hello");
//            } else if ("syy".equals(message)) {
//                websocketList.get("xjx").sendMessage("xjx say hello");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     *
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("发生错误");
//        error.printStackTrace();
//    }
//    /**
//     * 实现服务器主动推送
//     */
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//
//    /**
//     * 群发自定义消息
//     * */
//    /*public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
//        log.info("推送消息到窗口"+userId+"，推送内容:"+message);
//        for (ImController item : webSocketSet) {
//            try {
//                //这里可以设定只推送给这个sid的，为null则全部推送
//                if(userId==null) {
//                    item.sendMessage(message);
//                }else if(item.userId.equals(userId)){
//                    item.sendMessage(message);
//                }
//            } catch (IOException e) {
//                continue;
//            }
//        }
//    }*/
//
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        WebSocketServer.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        WebSocketServer.onlineCount--;
//    }
//}

//@ServerEndpoint("/im/{userId}")
//@Component
@Slf4j
public class WebSocketServer {
    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    private int t = 1;

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        log.info("有新的客户端连接了: {}", session.getId());
        log.info("userId: {}", userId);
        //将新用户存入在线的组
        clients.put(session.getId(), session);
    }

    /**
     * 客户端关闭
     * @param session session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("有用户断开了, id为:{}", session.getId());
        //将掉线的用户移除在线的组里
        clients.remove(session.getId());
    }

    /**
     * 发生错误
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 收到客户端发来消息
     * @param message  消息对象
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("服务端收到客户端发来的消息: {}", message);
        log.info("times: {}", t++);
        this.sendAll(message);
    }

    /**
     * 群发消息
     * @param message 消息内容
     */
    private void sendAll(String message) {
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()) {
            sessionEntry.getValue().getAsyncRemote().sendText(message);
        }
    }
}