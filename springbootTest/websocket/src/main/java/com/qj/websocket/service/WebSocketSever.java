package com.qj.websocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author： qiujie
 * @date： 2023/9/7 16:27
 * @Description： WebSocket操作类
 */
@ServerEndpoint("/websocket/{sid}")
@Component
@Slf4j
@Data
public class WebSocketSever {

    // 虽然spring默认是单例对象,但是ServerEndpoint会给每个连接创建一个对象，所以这里无法注入ApplicationContext
    // @Resource
    // ApplicationContext context;


    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private String sid;

    // session集合,存放对应的session
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();

    // concurrent包的线程安全Set,用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocketSever> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 静态变量，用来记录当前在线连接数，线程安全的类。
     */
    private static AtomicInteger onlineSessionClientCount = new AtomicInteger(0);

    /**
     * 建立WebSocket连接
     *
     * @param session
     * @param sid 用户ID
     */
    @OnOpen
    public void onOpen(@PathParam("sid") String sid, Session session) {
        log.info("WebSocket建立连接中,连接用户ID：{}", sid);
        try {
            Session historySession = sessionPool.get(sid);
            // historySession不为空,说明已经有人登陆账号,应该删除登陆的WebSocket对象
            if (historySession != null) {
                webSocketSet.remove(historySession);
                historySession.close();
            }
        } catch (IOException e) {
            log.error("重复登录异常,错误信息：" + e.getMessage(), e);
        }
        // 建立连接
        this.session = session;
        this.sid = sid;
        webSocketSet.add(this);
        sessionPool.put(sid, session);
        //在线数加1
        onlineSessionClientCount.incrementAndGet();
        log.info("建立连接完成,当前在线人数为：{}", webSocketSet.size());
    }

    /**
     * 发生错误
     *
     * @param throwable e
     */
    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        //在线数减1
        onlineSessionClientCount.decrementAndGet();
        log.info("连接断开,当前在线人数为：{}", webSocketSet.size());
    }

    /**
     * 接收客户端消息
     *
     * @param message 接收的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到客户端发来的消息：{}", message);
        JSONObject jsonObject = JSON.parseObject(message);
        String toSid = jsonObject.getString("sid");
        String msg = jsonObject.getString("message");
        log.info("服务端收到客户端消息 ==> fromSid = {}, toSid = {}, message = {}", sid, toSid, message);

        /**
         * 模拟约定：如果未指定sid信息，则群发，否则就单独发送
         */
        if (toSid == null || "".equals(toSid) || "".equalsIgnoreCase(toSid)) {
            sendAllMessage(msg);
        } else {
            sendMessageByUser(toSid, msg);
        }



    }

    /**
     * 推送消息到指定用户
     *
     * @param toSid  用户ID
     * @param message 发送的消息
     */
    public static void sendMessageByUser(String toSid, String message) {
        log.info("接收消息用户ID：" + toSid + ",推送内容：" + message);
        Session session = sessionPool.get(toSid);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("推送消息到指定用户发生错误：" + e.getMessage(), e);
        }
    }

    /**
     * 群发消息
     *
     * @param message 发送的消息
     */
    public static void sendAllMessage(String message) {
        log.info("发送消息：{}", message);
        for (WebSocketSever webSocket : webSocketSet) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("群发消息发生错误：" + e.getMessage(), e);
            }
        }
    }

}

