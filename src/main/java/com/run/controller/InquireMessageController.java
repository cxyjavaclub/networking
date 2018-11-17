package com.run.controller;

import com.run.configuration.OverallConfiguration;
import com.run.util.SocketTCPUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/inquire")
public class InquireMessageController {

    /**
     * 获取TCP/IP服务器端口号
     * @return
     */
    @RequestMapping("/getServerPort")
    @ResponseBody
    public String getServerPort(){
        return OverallConfiguration.serverPort.toString();
    }

    /**
     * 获取TCP/IP服务器连接人数
     * @return
     */
    @RequestMapping("/getServerConnectNum")
    @ResponseBody
    public String getServerConnectNum(){
        return SocketTCPUtil.serverConnectNum.toString();
    }
}
