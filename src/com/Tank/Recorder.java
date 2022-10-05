package com.Tank;

import java.io.*;
import java.util.Vector;

/*
 * 记录相关信息，和文件交互
 * */
public class Recorder {
    //定义变量，记录我方击毁的敌方坦克数量
    private static int enemyTanksNum = 0;
    //定义IO对象，准备将数据写到文件中去
    private static FileWriter fw = null;
    private static BufferedReader br = null;
    private static BufferedWriter bw = null;
    private static String recordFile = "D:\\myRecord.txt";//要使用绝对路径
    //oop,定义Vector，指向MyPanel中的敌人坦克对象
    private static Vector<EnemyTank> enemyTanks = null;
    //定义一个Node的Vector，用于保存从文件中读取到的上一把的敌人信息
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //为什么返回类型是Vector<Node>
    //在MyPanel类中启动该方法
    public static Vector<Node> getEnemyDirectionAndNum() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            enemyTanksNum = Integer.parseInt(br.readLine());
            String line = "";
            while ((line = br.readLine()) != null) {
                String xyd[] = line.split(" ");
                //人工手动将获取的信息按照需求分割好并且存入到相应的位置中去
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(br!=null){//这一步的判断的目的是什么
                    br.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return nodes;
    }

    /*
     * 其中会保留上一把打掉的坦克和未被打掉的坦克的坐标
     * */
    public static void setNum() {

        try {

            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(Recorder.enemyTanksNum + "\r\n");
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isAlive()) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirection();
                    bw.write(record + "\r\n");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static int getEnemyTanksNum() {
        return enemyTanksNum;
    }

    public static void setEnemyTanksNum(int enemyTanksNum) {
        Recorder.enemyTanksNum = enemyTanksNum;
    }

    public static void addEnemyTankNum() {
        enemyTanksNum++;
    }
}
