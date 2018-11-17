package com.run.mysocket;


import com.run.util.SocketTCPUtil;
import com.run.util.ToUpperTCPServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author cxy
 */
public class StartRun {

    /**
     * spring启动完成运行
     */
    @PostConstruct

    public void  init(){
        System.out.println("spring启动完成");
        ToUpperTCPServer.serveRunThreadStart(15263, 10, ConnectRun.class);
    }

    /**
     * spring关闭运行
     */
    @PreDestroy
    public void  doStory(){
        System.out.println("spring已关闭");
    }
}
