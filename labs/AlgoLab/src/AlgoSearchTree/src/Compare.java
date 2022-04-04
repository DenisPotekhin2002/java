import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Compare {
    public static void main(String[] args) throws FileNotFoundException {
        File o = new File("out1.txt");
        File t = new File("out2.txt");
        Scanner sc1 = new Scanner(o);
        Scanner sc2 = new Scanner(t);
        int line = 1;
        int check = 0;
        while (sc1.hasNext() && sc2.hasNext()){
            line++;
            String temp1 = sc1.next();
            String temp2 = sc2.next();
            if (!temp1.equals(temp2)){
                check ++;
                System.out.println("*\n" + temp1 + "\n\n" + temp2 + "\n*");
                System.out.println("checking line " + line);
                //throw new AssertionError();
            }
        }
        System.out.println("finished");
        if (check >= 1){
            System.out.println(check);
        }
    }
}
