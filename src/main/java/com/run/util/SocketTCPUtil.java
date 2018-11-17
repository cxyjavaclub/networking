package com.run.util;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cxy
 */
public class SocketTCPUtil extends Thread{

    public static Map<String, SocketTCPUtil> storageAllConnectObj = new HashMap<>(); //存储所有的连接的对象
    public static Long serverConnectNum = 0L;

    protected Socket socket;
    protected BufferedInputStream bis;
    protected InputStream is;
    protected OutputStream out;

    /**
     * 向所有连接的Socket发送信息
     * @param message
     */
    public static void sendOutMessageAll(String message){
        for(String key : storageAllConnectObj.keySet()){
            storageAllConnectObj.get(key).sendStringMessage(message);
        }
    }
    
    /**
     * 关闭socket连接
     * @throws IOException
     */
    public synchronized void close(){
        try {
            serverConnectNum--;
            System.out.println("连接断开, 剩余连接数为：" + serverConnectNum);
            is.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新输出流
     * @return
     */
    public Boolean flushOut(){
        try {
            out.flush();
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 发送字符串信息
     * @param str
     * @return
     */
    public Boolean sendStringMessage(String str){
        PrintWriter pw = new PrintWriter(out);
        pw.print(str);
        pw.flush();
        return true;
    }

    /**
     * 发送字节信息
     * @param bytes
     * @return
     */
    public Boolean sendByteMessage(byte[] bytes) {
        return sendByteMessage(bytes, 0, bytes.length);
    }

    /**
     * 发送字节信息并刷新流
     * @param bytes
     * @return
     */
    public Boolean sendByteMessageFlush(byte[] bytes){
        return sendByteMessageFlush(bytes, 0, bytes.length);
    }

    /**
     * 发送字节信息
     * @param bytes
     * @param off
     * @param len
     * @return
     */
    public Boolean sendByteMessage(byte[] bytes, int off, int len){
        return sendByteMessage(bytes, off, len, false);
    }

    /**
     * 发送字节信息并刷新流
     * @param bytes
     * @param off
     * @param len
     * @return
     */
    public Boolean sendByteMessageFlush(byte[] bytes, int off, int len){
        return sendByteMessage(bytes, off, len, true);
    }

    /**
     * 发送字节信息
     * @param bytes
     * @param off
     * @param len
     * @return
     */
    public Boolean sendByteMessage(byte[] bytes, int off, int len, boolean flag){
        try {
            out.write(bytes, off, len);
            if(flag){
                out.flush();
            }
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 发送字节信息(抛异常)
     * @param bytes
     * @throws IOException
     */
    public void sendByteMessageThrows(byte[] bytes) throws IOException{
        sendByteMessageThrows(bytes, 0, bytes.length);
    }

    /**
     * 发送字节信息(抛异常)
     * @param bytes
     * @param off
     * @param len
     * @throws IOException
     */
    public void sendByteMessageThrows(byte[] bytes, int off, int len) throws IOException{
        out.write(bytes, off, len);
    }

    /**
     * 读取一段字符串，小于等于1024个字节
     * @return
     * @throws IOException
     */
    public String receptionStr() throws IOException{
        int len;
        is = socket.getInputStream();
        byte[] data = new byte[1024];
        len = is.read(data);
        if (len < 0) {
            throw new IOException();
        }
        return new String(data, 0, len);
    }

    /**
     * 读取字节
     * @param bytes
     * @return
     * @throws IOException
     */
    public int receptionByte(byte[] bytes) throws IOException{
        int len;
        len = is.read(bytes);
        return len;
    }


    /**
     * 启动
     * @param socket
     */
    public synchronized void start(Socket socket){
        if(socket == null){
            throw new RuntimeException("socket为空");
        }
        this.socket = socket;
        try {
            serverConnectNum++;
            System.out.println("连接数为：" + serverConnectNum);
            is = socket.getInputStream();
            bis = new BufferedInputStream(is);
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.start();
    }
}
