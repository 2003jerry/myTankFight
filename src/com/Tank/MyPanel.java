package com.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero = null;//这里不是空对象吗，为什么下面重写key方法时可以直接调用hero的get方法
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Node> nodes = new Vector<>();
    //当坦克被击中时往bombs中加入一个对象
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSizes = 4;
    //定义三张图片用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {//这一步是在什么时候使用的？
        nodes=Recorder.getEnemyDirectionAndNum();
        Recorder.setEnemyTanks(enemyTanks);//如果没有这一行的话Recorder类中的坦克群是一个指向空的内容，会报错
        hero = new Hero(500, 100);//初始化自己的坦克
        hero.setSpeed(6);
        switch (key){
            case "1":{
                for (int i = 0; i < enemyTankSizes; i++) {
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0,2);
                    enemyTank.setDirection(2);
                    enemyTank.setEnemyTanks(enemyTanks);
                    new Thread(enemyTank).start();
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    //创建好用应该往vector中放
                    enemyTanks.add(enemyTank);
                }


            }
                break;
            case "2":{//继续上一把游戏
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(),node.getY(),node.getDirection());
                    //enemyTank.setDirection(2);
                    enemyTank.setEnemyTanks(enemyTanks);//这一段代码是为了将自身的坦克与其他坦克做比较防止重合
                    new Thread(enemyTank).start();//启动线程
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    enemyTank.shots.add(shot);
                    new Thread(shot).start();
                    //创建好用应该往vector中放
                    enemyTanks.add(enemyTank);
                }


            }
                break;
            default:
                System.out.println("您的输入有错误，请重新输入");
        }
        //初始化敌方坦克群
        //初始化炸弹
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/初段爆炸效果.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/中段爆炸效果.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/20190215210241592.png"));
    }
    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font =new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.drawString("您累计击落的敌方坦克",1020,30);
        paintTank(1060,60,g,0,1);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getEnemyTanksNum()+"",1150,100);

    }
    @Override
    public void paint(Graphics g) {//该方法会被自动调用
        super.paint(g);//要保留
        g.fillRect(0, 0, 1000, 750);//填充背景默认是黑色,自己决定的内容
        showInfo(g);
       if(hero.isAlive()&&hero!=null){
           paintTank(hero.getX(), hero.getY(), g, hero.getDirection(), 0);
       }

        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isAlive == true) {
                g.draw3DRect(shot.x, shot.y, 3, 3, false);
            }else{
                hero.shots.remove(shot);
            }
        }
        //这一句话用来防止第一个炸弹效果不触发
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //画出所有炸弹
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }
        //下面是画所有的敌人坦克

        for (int i = 0; i < enemyTanks.size(); i++) {//一开始报错是因为我的size不会变化，后边会指向已被消除的对象

            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isAlive()) {
                paintTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);//得到子弹
                    if (shot.isAlive) {
                        g.draw3DRect(shot.x, shot.y, 3, 3, false);
                    } else {//移除子弹库中已经要销毁的子弹
                        enemyTank.shots.remove(shot);
                    }//不太懂为什么要移除已经销毁的子弹

                }
            }
        }
    }

    //封装一个绘画坦克的方法
    /*
    x指左上角x坐标
    y指左上角y坐标
    dir指画图时的方向
    type指的是是什么类型的坦克
    * */
    public void paintTank(int x, int y, Graphics g, int dir, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.CYAN);
                break;
            case 1:
                g.setColor(Color.YELLOW);
                break;
        }
        switch (dir) {//0上，1右，2下，3左
            case 0:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);//上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//下边轮子
                //画矩形
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //画圆
                g.fillOval(x + 20, y + 10, 20, 20);
                //画炮杆--起点终点
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2:
                //两边轮子
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                //画矩形
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                //画圆
                g.fillOval(x + 10, y + 20, 20, 20);
                //画炮杆--起点终点
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);//上边轮子
                g.fill3DRect(x, y + 30, 60, 10, false);//下边轮子
                //画矩形
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                //画圆
                g.fillOval(x + 20, y + 10, 20, 20);
                //画炮杆--起点终点
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("暂时没有处理");

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirection(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirection(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();

            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirection(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirection(3);
            if (hero.getX() > 0) {
                hero.moveLeft();

            }
        }
        //如果用户按下j那么就启动线程发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J) {
           //if(hero.shot==null||!hero.shot.isAlive){
               hero.shotEnemyTank();
           //}
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
   //run方法的调用来判断哪些东西需要画哪些被击中
    @Override
    public void run() {//刷新绘图区域达到子弹移动的目的
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            if (hero.shot != null && hero.shot.isAlive) {
//                for (int i = 0; i < enemyTanks.size(); i++) {
//                    EnemyTank enemyTank = enemyTanks.get(i);
//                    hitTank(hero.shot, enemyTank);
//                }
//            }
            judgeEnemyHit();//判断我们是否击中敌方坦克
            hitHeroTank();//判断我们坦克是否被敌方击中
            this.repaint();
        }
    }
    public void judgeEnemyHit(){//这一步保证每一颗子弹都能解决掉一台坦克
        for(int i=0;i<hero.shots.size();i++){
            Shot shot = hero.shots.get(i);
            if(shot!=null&& shot.isAlive){
                for(int j = 0;j<enemyTanks.size();j++){
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot,enemyTank);
                }
            }
        }
    }
    //判断坦克是否被子弹击中
    public void hitTank(Shot s, Tank tank) {
        switch (tank.getDirection()) {
            case 0://坦克向上
            case 2://坦克向下
                if (s.x > tank.getX() && s.x < tank.getX() + 40 &&
                        s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isAlive = false;
                    tank.setAlive(false);
                    enemyTanks.remove(tank);
                    if(tank instanceof EnemyTank){
                        Recorder.addEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);

                }
                break;
            case 1://坦克向右
            case 3://坦克向左
                if (s.x > tank.getX() && s.x < tank.getX() + 60 &&
                        s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isAlive = false;
                    tank.setAlive(false);
                    enemyTanks.remove(tank);
                    if(tank instanceof EnemyTank){
                        Recorder.addEnemyTankNum();
                    }
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);

                }
                break;
        }
    }
    //用来具体判断是否被敌方击中，保证敌方的子弹能干掉我
    public void hitHeroTank(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for(int j=0;j<enemyTank.shots.size();j++){
                Shot shot = enemyTank.shots.get(j);
                if(hero.isAlive()&&shot.isAlive){
                    hitTank(shot,hero);
                }
            }
        }
    }

}
