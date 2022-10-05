package homework;

import java.util.Scanner;

public class Text2 {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("请一次输入两段字符串" );
        String str1 = myScanner.next();
        String str2 =myScanner.next();
        System.out.println(str1+" 字符串长度是"+str1.length());
        System.out.println(str2+" 字符串长度是"+str2.length());
        String finalStr = str1+" "+str2;
        System.out.println(finalStr+" 字符串长度是"+finalStr.length());
    }

}
