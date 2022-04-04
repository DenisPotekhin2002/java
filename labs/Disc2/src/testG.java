import java.math.BigDecimal;
import java.util.Scanner;

public class testG {
    public static strictfp void main(String[] args){
        for (int xz = 0; xz < 1000; xz++) {
            //Scanner sc = new Scanner(System.in);
            //int n = sc.nextInt();
            int n = (int)(Math.random() * 25) + 1;
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < n; i++) {
                sb.append((char) ('a' + i));
            }
            int[] arr = new int[n];
            //StringBuilder str = new StringBuilder(sc.next());
            StringBuilder str = new StringBuilder("");
            int z = (int)(Math.random() * 99) + 1;
            for (int y = 0; y < z; y++){
                char w = (char)('a' + Math.random() * n);
                str.append(w);
            }
            System.err.println(n);
            System.err.println(str);
            for (int i = 0; i < str.length(); i++){
                char c = str.charAt(i);
                int k = sb.indexOf(Character.toString(c));
                arr[k]++;
            }
            System.out.println(n);
            int sum = str.length();
            BigDecimal[] array = new BigDecimal[n + 1];
            array[0] = new BigDecimal(0);
            for (int i = 0; i < n; i++){
                System.out.print(arr[i] + " ");
                array[i + 1] = new BigDecimal(arr[i]).divide(new BigDecimal(sum), 10000, 0).add(array[i]);
            }
            System.out.println();
            BigDecimal l = new BigDecimal(0);
            BigDecimal r = new BigDecimal(1);
            for (int i = 0; i < str.length(); i++){
                char c = str.charAt(i);
                BigDecimal rtemp = r;
                BigDecimal ltemp = l;
                r = ltemp.add(array[sb.indexOf(Character.toString(c)) + 1].multiply(rtemp.subtract(ltemp)));
                l = ltemp.add(array[sb.indexOf(Character.toString(c))].multiply(rtemp.subtract(ltemp)));
            }
        if (l.equals(r)){
            while (true){

            }
        }
            long q = 1;
            long p = 0;
            while (true){
                BigDecimal temp = new BigDecimal(Math.pow(2, q));
                p = temp.multiply(l).longValue();
                if (p < temp.multiply(l).doubleValue()){
                    p++;
                }
                if (p <= temp.multiply(r).doubleValue()){
                    break;
                }
                q++;
            }
            StringBuilder ans = new StringBuilder(Long.toBinaryString(p));
            while (ans.length() < q){
                ans.insert(0, 0);
            }
            System.out.println(ans);
        }
    }
}
