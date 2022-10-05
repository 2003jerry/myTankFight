package com.Tank;

public class Shot implements Runnable {
    int x;//子弹x坐标
    int y;//子弹y坐标
    int speed = 5;
    int direction;
    boolean isAlive = true;

    public Shot(int x,int y,int direction){
        this.x=x;
        this.y=y;
        this.direction=direction;
    }
    @Override//panel的不断刷新会使得子弹run被不断调用，因此可以做到子弹的移动
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (direction) {
                case 0: {
                    y -= speed;
                    break;
                }
                case 1: {
                    x += speed;
                    break;
                }
                case 2: {
                    y += speed;
                    break;
                }
                case 3: {
                    x -= speed;
                    break;
                }
            }
            //子弹移动到边界结束线程
            //子弹打中目标后结束进程
            //在panel上即便没显示，子弹线程也可能没有结束，要注意
            if(!(x>0&&x<1000&&y>0&&y<750&&isAlive)){
                isAlive = false;
                break;
            }
        }
    }
}
