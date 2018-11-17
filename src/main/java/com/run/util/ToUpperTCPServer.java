package com.run.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author cxy
 */
public class ToUpperTCPServer {
    private ServerSocket serverSocket;
    private Thread serverThread;
    private Class<?> socketTCPUtilClass;

    private ToUpperTCPServer(int port, int connectNum, Class<?> socketTCPUtilClass) throws IOException {
        serverSocket = new ServerSocket(port, connectNum);
        this.socketTCPUtilClass = socketTCPUtilClass;
        System.out.println("服务器启动☺☺☺!!!");
    }

    /**
     * 生成一个服务器
     *
     * @param port       端口号
     * @param connectNum 最大连接数
     * @return true: 成功 false: 失败
     */
    public static ToUpperTCPServer serveRun(int port, int connectNum, Class<?> socketTCPUtilClass) {
        ToUpperTCPServer toUpperTCPServer = null;
        if (!SocketTCPUtil.class.isAssignableFrom(socketTCPUtilClass)) {
            System.out.println("输入的类不是SocketTCPUtil类的子类\n服务器创建失败☹☹☹......");
            return null;
        }
        if (socketTCPUtilClass != null) {
            try {
                toUpperTCPServer = new ToUpperTCPServer(port, connectNum, socketTCPUtilClass);
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return toUpperTCPServer;
    }


    /**
     * 生成一个服务器
     *
     * @param port               端口号
     * @param connectNum         最大连接数
     * @param socketTCPUtilClass
     * @return true: 成功 false: 失败
     */
    public static ToUpperTCPServer serveRunThreadStart(int port, int connectNum, Class<?> socketTCPUtilClass) {
        ToUpperTCPServer toUpperTCPServer = serveRun(port, connectNum, socketTCPUtilClass);
        if (toUpperTCPServer != null) {
            toUpperTCPServer.startThreadServerSocket();
        }
        return toUpperTCPServer;
    }

    /**
     * 启动服务器（占用调用线程）
     *
     * @return
     */
    public Boolean startServerSocket() {
        if (serverSocket == null) {
            return false;
        }
        connectService();
        return true;
    }

    /**
     * 启动服务器线程
     *
     * @return
     */
    public Boolean startThreadServerSocket() {
        if (serverSocket == null) {
            return false;
        }
        serverThread = new Thread(() -> connectService());
        serverThread.start();
        return true;
    }

    /**
     * 停止服务器线程
     */
    public void stopThreadServerSocket() {
        if (serverThread != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverThread.stop();
            System.out.println("服务器关闭😊😊😊！！！");
        }
    }


    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    /**
     * 增加新的连接
     */
    public void connectService() {
        Socket socket = null;
        try {
            while (true) {
                System.out.println("等待中.......");
                socket = serverSocket.accept();
                try {
                    ((SocketTCPUtil) (socketTCPUtilClass.newInstance())).start(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                    stopThreadServerSocket();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


