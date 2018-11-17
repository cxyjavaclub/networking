package com.run.controller;

import com.run.util.JsonResponseUtils;
import com.run.util.SocketTCPUtil;
import com.run.util.WebSocketUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cxy
 */

@Controller
@RequestMapping("/socketOperation")
public class SocketOperationController {

    /**
     *  获取所有的socket连接
     *  @return
     */
    @RequestMapping("/getAllSocket")
    @ResponseBody
    public JsonResponseUtils getAllSocket(){
        return JsonResponseUtils.ok(SocketTCPUtil.storageAllConnectObj.keySet());
    }

    /**
     * 向所有的Socket发送信息
     * @param message
     * @return
     */
    @RequestMapping("/sendMessageAll")
    @ResponseBody
    public Integer towardsSocketSendMessage(String message){
        if(SocketTCPUtil.serverConnectNum == 0){
            return 201;
        }
        SocketTCPUtil.sendOutMessageAll(message);
        return 200;
    }
}
