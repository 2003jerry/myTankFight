package com.api;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException {
        String location = "src\\wode.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(location));
        BufferedReader br = new BufferedReader(new FileReader(location));
        bw.write("1.我觉得你说的有点失去偏薄");
        bw.newLine();
        bw.write("2.我觉得你说的有点失去偏薄");
        bw.newLine();
        bw.write("3.我觉得你说的有点失去偏薄");
        bw.newLine();
        if (bw != null) {
            bw.close();
            System.out.println("bw填写完毕");
        }
        //连接服务器，内容是ip和端口
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        System.out.println("客户端" + socket.getClass());
        //通过socket获得一个输出流
        OutputStream outputStream = socket.getOutputStream();//让我方端口与输出流绑定
        // outputStream.write("客户端向服务端问好hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh".getBytes());//需要getBytes方法，不然会报错
      String line="";
       while((line = br.readLine())!=null){
           outputStream.write(line.getBytes());
       }
       br.close();
        outputStream.close();//关闭流和端口防止资源的浪费，会自动关闭吗，如果进程结束还会存在吗
        socket.close();
        System.out.println("客户端退出");
    }
}
