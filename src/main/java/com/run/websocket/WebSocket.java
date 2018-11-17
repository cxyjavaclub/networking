package com.run.websocket;

import com.run.util.WebSocketUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{userName}")
final public class WebSocket {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //当前websocket的用户名
    private String myUserName;
    /**
     * 连接建立成功调用的方法
     * Tomcat9处理了字符问题try {
     * userName = new String(userName.getBytes("ISO-8859-1"), "UTF8");
     * } catch (UnsupportedEncodingException e) {
     * e.printStackTrace();
     * }
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        this.session = session;
        this.myUserName = userName;
        WebSocketUtils.ononOpen(this);
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("userName") String userName) {
        WebSocketUtils.onClose(this);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        WebSocketUtils.onMessage(this, message);
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
//        System.out.println("发生错误");
//        error.printStackTrace();
        WebSocketUtils.onError(null, error);
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * 发送信息方法
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        System.out.println("发送的信息为：" + message);
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 返回只有值session
     * @return
     */
    public Session getSession() {
        return session;
    }

    /**
     * 返回只有值myUserName
     * @return
     */
    public String getMyUserName() {
        return myUserName;
    }
}