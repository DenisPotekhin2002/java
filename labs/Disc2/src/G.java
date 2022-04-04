import java.math.BigDecimal;
import java.util.Scanner;

public class G {
    public static strictfp void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < n; i++){
            sb.append((char)('a' + i));
        }
        int[] arr = new int[n];
        StringBuilder str = new StringBuilder(sc.next());
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
            array[i + 1] = new BigDecimal(arr[i]).divide(new BigDecimal(sum), 545, 0).add(array[i]);
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
        }/*
        if (l == r){
            while (true){

            }
        }*/
        long q = 1;
        long p = 0;
        while (true){
            BigDecimal temp = BigDecimal.valueOf(Math.pow(2, q));
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