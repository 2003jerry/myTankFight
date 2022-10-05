package homework;

public class Text5 {
    public static void main(String[] args) {
        myFinal myfinal =new myFinal(1, 2, new tools() {
            @Override
            public int cal() {
                return 0;
            }
        });
    Season []s = Season.values();
    }
}
interface tools{
    public  abstract  int cal();
}
class myFinal  implements  tools{
    public int cal(){
        return num;
    }
    final int num;
final int num2;
    public myFinal(int num) {
        this.num = num;
    }
    {
        num2=100;
    }

    public myFinal(int num, int num2,tools a) {//构造函数的时候需要写上这个内容
        this.num = num;

    }
}
enum Season{
    SPRING,SUMMER,AUTUMN,WINTER;

}