package com.Tank;

import java.util.Vector;

public class Hero extends Tank{
    Shot shot = null;//表示一个射击行为(线程)
    Vector<Shot>shots=new Vector<>();
    private boolean isLive ;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Hero(int x, int y) {
        super(x, y);
    }
    public void shotEnemyTank(){
        if(shots.size()==5){
            return;
        }
        switch (getDirection()){
            case 0:{
                shot = new Shot(getX()+20,getY(),0);
                break;
            } case 1:{
                shot = new Shot(getX()+60,getY()+20,1);
                break;
            } case 2:{
                shot = new Shot(getX()+20,getY()+60,2);
                break;
            } case 3:{
                shot = new Shot(getX(),getY()+20,3);
                break;
            }
        }
        shots.add(shot);
        //启动我们的shot线程
        new Thread(shot).start();
    }
}
