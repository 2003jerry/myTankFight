package com.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Sever {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        //这里的socket是客户端的socket还是
        //ServerSocket可以创建多个socket
        System.out.println("服务端在9999接口监听，等待连接");
        Socket socket =serverSocket.accept();//如果监听到会返回一个socket
        //如果一直没有监听到内容，则会停滞在这个位置
        System.out.println("socket="+socket.getClass());
        //通过流和端口向服务端传入内容
        InputStream inputStream =socket.getInputStream();
        //这块输出很奇怪，得上网看看文件读取的原理
        byte buf[]=new byte[2048];
        int bufLen = 0;
        while((bufLen=inputStream.read(buf))!=-1){
            System.out.println(new String(buf,0,bufLen));
        }
        //这块需要考虑关闭的顺序吗
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
