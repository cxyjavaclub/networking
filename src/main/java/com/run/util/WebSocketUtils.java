package com.run.util;

import com.run.websocket.WebSocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketUtils {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全HashMap，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，其中Key可以为用户标识
    private static final Map<String, WebSocket> webSocketSets = new ConcurrentHashMap<String, WebSocket>();

    /**
     * 当有人进入时调用的方法
     * @param webSocket
     */
    public static synchronized void ononOpen(WebSocket webSocket) {
        String userName = webSocket.getMyUserName();
        System.out.println("链接用户的名字为：" + userName);
        webSocketSets.put(userName, webSocket);     //加入set中

        //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }


    /**
     * 当用户连接关闭时调用的方法
     * @param webSocket
     */
    public static void onClose(WebSocket webSocket) {
        webSocketSets.remove(webSocket.getMyUserName());  //从MAP中删除webSocket但不包括User的信息有可能是同一个用户想更改头像或名字
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 倒计时
     * @param second
     */
    public static void setTimeout(Integer second){
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("运行");
                timer.cancel();
            }
        }, second * 1000);
    }

    /**
     * 接收到消息时调用的方法
     * 此方法是用websocket提交信息是调用的方法而我们的消息时有controller处理的所以这个方法一般不用
     * 这里做一个群发功能(包含自己)
     * @param webSocket
     * @param message
     */
    public static void onMessage(WebSocket webSocket, String message){
        onMessage(webSocket, message, true);
    }

    /**
     * 发生错误时调用
     *
     * @param webSocket
     * @param error
     */
    public static void onError(WebSocket webSocket, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    /**
     * 群发功能
     * @param webSocket
     * @param message
     * @param flag 是否给自己发送消息
     */
    private static void onMessage(WebSocket webSocket, String message, boolean flag){
        System.out.println("消息:" + message);
        //群发消息
        Set<String> keys = webSocketSets.keySet();

        for (String s : keys) {
            try {
                if(!flag && !ifNull(webSocket) && webSocket.getMyUserName().equals(s)) {
                   continue;
                }
                webSocketSets.get(s).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 判断UserName是否存在
     * @param key
     * @return
     */
    public static boolean containsUserName(String key){
        return webSocketSets.containsKey(key);
    }

    /**
     * 群发功能（不包含自己）
     *
     * @param message
     */
    public static void sendOutMessage(WebSocket webSocket, String message) {
        onMessage(webSocket, message, false);
    }

    /**
     * 群发功能（群发所有人）
     *
     * @param message
     */
    public static void sendOutMessage(String message) {
        onMessage(null, message, true);
    }

    /**
     * 指定用户发送消息(不包括发件人)
     * @param userName
     * @param message
     */
    public static void theSpecifiedUserHairMessage(String userName, String message){
        WebSocket webSocket = findKeyWebSocket(userName);
        try {
            webSocket.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 通过用户名获得WebSocket对象
     * @param key
     * @return
     */
    public static WebSocket findKeyWebSocket(String key){
        if(ifNull(key)) {
            return null;
        }
        return webSocketSets.get(key);
    }

    /**
     * 检测参数是否为空
     * @param obj
     * @return
     */
    public static boolean ifNull(Object obj){
        return obj == null ? true : false;
    }



    public static synchronized int getOnlineCount() {
        return webSocketSets.size();
    }

}
