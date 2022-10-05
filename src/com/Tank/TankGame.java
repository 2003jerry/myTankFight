package com.Tank;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame extends JFrame {//继承后等于那个窗口
    MyPanel myPanel = null;//要有一个画板
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        TankGame tankGame = new TankGame();

    }

    public TankGame() {
        System.out.println("请输入您的选择：1：新游戏，2：继续上局游戏");
        String key = scanner.next();
        myPanel = new MyPanel(key);//初始化画板，具体实现看类中的构造函数
        Thread thread = new Thread(myPanel);
        thread.start();
        this.add(myPanel);//加载游戏绘图区域
        this.setSize(1300,750);//设置窗口大小
        this.addKeyListener(myPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//自动结束进程
        this.setVisible(true);//让窗口可视化
        //在JFrame窗口增加响应窗口关闭的操作
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //Recorder.setNum();
                Recorder.setNum();
                System.exit(0);
            }
        });
    }
}
