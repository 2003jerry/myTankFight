package homework;

import java.util.Scanner;

public class Text3 {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("请输入数字");
        double num = myScanner.nextDouble();
        if (num == 0) {
            System.out.println("输入不能为0请重新输入");
            num = myScanner.nextDouble();
        }
        double y = 1.0 / num;
        System.out.println("num =" + num + ",y =" + y + ",num * y=" + num * y);
        System.out.println("(num * y)-1 = " + ((num * y) - 1));
    }
}
