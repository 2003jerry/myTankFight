package homework;

public class Text4 {
    public static void main(String[] args) {
        Num num1=new Num(10);
        Num num2=new Num(120);
        if(num1.equals(num2)){
            System.out.println("二者相等");
        }
        else{
            System.out.println("二者不相等");
        }
    }
}
class Num{
    public int num;

    public Num(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object obj) {
           int num1 =((Num) obj).getNum();

       if(this.num==num1)
           return true;
       else{
           return false;
       }
    }
}
