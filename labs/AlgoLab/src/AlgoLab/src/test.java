import java.io.*;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class test {

    public static void main(String[] args) throws IOException {
        for (int k = 1; k < 1000; k++) {
            PrintWriter writer = new PrintWriter("input.txt");
            int i = (int) (Math.random() * 1000000 % 12) + 1;
            writer.print(i + " ");
            int j = (int) (Math.random() * 1000000 % 12) + 1;
            writer.print(j);
            writer.println();
            System.err.println(i + " " + j);
            for (int c1 = 0; c1 < i; c1++){
                for (int c2 = 0; c2 < j; c2++){
                    int t = (int) (Math.random() * 1000000 % 10);
                    if (t == 0){
                        writer.print('X');
                        System.err.print('X');
                    } else {
                        writer.print('.');
                        System.err.print('.');
                    }
                }
                writer.println();
                System.err.println();
            }
            writer.close();
            System.err.println();
            I.main(null);
            Scanner reader = new Scanner(new File("output.txt"));
            StringBuilder stringOne = new StringBuilder();
            while (reader.hasNext()){
                stringOne.append(reader.next());
            }
            /*BufferedReader reader = new BufferedReader(new FileReader(new File("num2part.out")));
            StringBuilder stringOne = new StringBuilder(reader.readLine());
            reader.close();
             */
            //System.err.println("'" + stringOne + "' 1");
            I_1.main(null);
            Scanner readerTwo = new Scanner(new File("output.txt"));
            StringBuilder stringTwo = new StringBuilder();
            while (readerTwo.hasNext()){
                stringTwo.append(readerTwo.next());
            }
            //System.err.println("'" + stringTwo + "' 2");
            if (stringOne.length() > 0 && stringTwo.length() > 0 && !stringOne.toString().equals(stringTwo.toString())){
                throw new AssertionError(stringOne + " vs " + stringTwo);
            }
        }
    }
}
