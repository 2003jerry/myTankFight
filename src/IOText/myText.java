package IOText;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class myText {
    public static void main(String[] args) {
        PrintStream ps = System.out;
        ps.println("nihaomeilihuashi");
        System.out.println("你好");
        ps.close();//为什么上面那一行代码不用关闭，oop在此处是怎样一个绑定关系
        //为什么编译异常try catch一下就可以过了
        BufferedWriter bff= null;
        try {
             bff = new BufferedWriter(new FileWriter("D:\\haha.txt"));//使用绝对路径
            bff.write("hihao");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bff!=null){//这步什么意思我不大懂
                    bff.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
