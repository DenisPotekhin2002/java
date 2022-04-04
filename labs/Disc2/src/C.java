import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class C {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.next();
        int n = s.length();
        StringBuilder sb = new StringBuilder(s);
        StringBuilder[] arr = new StringBuilder[n];
        StringBuilder[] arr2 = new StringBuilder[n];
        for (int j = 0; j < n; j++){
            arr[j] = new StringBuilder(Character.toString(sb.charAt(j)));
            arr2[j] = new StringBuilder(Character.toString(sb.charAt(j)));
        }
        Arrays.sort(arr);
        /*for (int j = 0; j < n; j++){
            System.err.print(arr[j]);
        }
        System.err.println();
        for (int j = 0; j < n; j++){
            System.err.print(arr2[j]);
        }
        System.err.println();*/
        int[] used = new int[n];
        int[] d = new int[n];
        for (int j = 0; j < n; j++){
            for (int k = 0; k < n; k++){
                if (arr[k].toString().equals(arr2[j].toString()) && used[k] == 0){
                    used[k] = 1;
                    d[j] = k;
                    break;
                }
            }
        }
        /*for (int j = 0; j < n; j++){
            System.err.print(d[j]);
        }
        System.err.println();
        for (int i = 1; i < n; i++){
            for (int j = 0; j < n; j++){
                arr[j].insert(0, sb.charAt(j));
            }
            Arrays.sort(arr);
        }*/
        for (int i = 1; i < n; i++){
            StringBuilder[] arrTemp = new StringBuilder[n];
            for (int j = 0; j < n; j++){
                arrTemp[d[j]] = new StringBuilder(arr[j]);
                arrTemp[d[j]].insert(0, sb.charAt(j));
            }
            arr = Arrays.copyOf(arrTemp, n);
        }
        System.out.println(arr[0]);
    }
}
