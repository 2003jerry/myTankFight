package homework;

public class Text {
    public static void main(String[] args) {
        String a = "i hate you";
        Text.myReplace(a);
    }
    public static void myReplace(String string) {//这块该如何加try catch？
        String temp=string.replaceFirst("hate", "love");
        if(temp.equals(string)){
            System.out.println("您的文本中没有您想替换的内容");
            return;
        }
        string = temp;
        System.out.println(string);
    }
}



