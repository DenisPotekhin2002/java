import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Soundex {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("Soundex.txt");
        Scanner sc = new Scanner(f);
        System.out.println(sc.hasNext());
        int count = 0;
        while(sc.hasNext()){
            count++;
            String temp = sc.next();
            temp = temp.substring(0, 1).toUpperCase() + temp.substring(1, temp.length());
            temp = temp.replaceAll("h", "");
            temp = temp.replaceAll("w", "");
            temp = temp.replace('b', '1');
            temp = temp.replace('f', '1');
            temp = temp.replace('p', '1');
            temp = temp.replace('v', '1');
            temp = temp.replace('c', '2');
            temp = temp.replace('g', '2');
            temp = temp.replace('k', '2');
            temp = temp.replace('j', '2');
            temp = temp.replace('q', '2');
            temp = temp.replace('s', '2');
            temp = temp.replace('x', '2');
            temp = temp.replace('z', '2');
            temp = temp.replace('d', '3');
            temp = temp.replace('t', '3');
            temp = temp.replace('l', '4');
            temp = temp.replace('m', '5');
            temp = temp.replace('n', '5');
            temp = temp.replace('r', '6');
            temp = temp.replaceAll("(1)+", "1");
            temp = temp.replaceAll("(2)+", "2");
            temp = temp.replaceAll("(3)+", "3");
            temp = temp.replaceAll("(4)+", "4");
            temp = temp.replaceAll("(5)+", "5");
            temp = temp.replaceAll("(6)+", "6");
            temp = temp.replaceAll("o", "");
            temp = temp.replaceAll("i", "");
            temp = temp.replaceAll("e", "");
            temp = temp.replaceAll("a", "");
            temp = temp.replaceAll("u", "");
            temp = temp.replaceAll("y", "");
            temp = temp + "0000";
            temp = temp.substring(0, 4);
            System.out.print(temp + " ");
            if (count % 26 == 0){
                System.out.println();
            }
        }
        //System.out.println(count);
    }
}
