package com.run.mysocket;

import com.run.util.DateUtils;
import com.run.util.SocketTCPUtil;
import com.run.util.WebSocketUtils;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author cxy
 */
public class ConnectRun extends SocketTCPUtil {

    //超时时间
    private Integer connectOvertime = 5;
    public String className;

    private Boolean overtimeDetectionRun() {
        Executor executor = Executors.newSingleThreadExecutor();
        FutureTask<String> future = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return receptionStr();
            }
        });
        sendStringMessage("123456");
        executor.execute(future);
        try {
            String result = future.get(connectOvertime, TimeUnit.SECONDS);
            System.out.println(result);
            if (!result.equals("")) {
                return false;
            }
            storageAllConnectObj.put("", this);
        } catch (Exception e) {
            System.out.println("校验失败！！！");
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        className = "Name_" + DateUtils.acquiescenceDateFormatTurnString(System.currentTimeMillis()) + "时连接";
        storageAllConnectObj.put(className, this);
        while(true) {
            try {
                WebSocketUtils.sendOutMessage(className + "：" + receptionStr());
            }catch (IOException ex){
                ex.printStackTrace();
                close();
                break;
            }
        }
        storageAllConnectObj.remove(className);
//        if (!overtimeDetectionRun()) {
//            close();
//            return;
//        }
    }
}
