import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class F {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < 26; i++){
            map.put(i, Character.toString('a' + i));
        }
        int len = 26;
        StringBuilder s = new StringBuilder();
        int n = sc.nextInt();
        StringBuilder temp = new StringBuilder("");
        for (int i = 0; i < n; i++){
            int t = sc.nextInt();
            if (t >= len){
                StringBuilder SbTemp = new StringBuilder(temp);
                SbTemp.append(temp.charAt(0));
                map.put(len, SbTemp.toString());
                len++;
                System.out.print(map.get(t));
                temp = new StringBuilder(map.get(t));
                continue;
            }
            if (t < len){
                System.out.print(map.get(t));
            }
            int u = 0;
            StringBuilder tempSb = new StringBuilder(temp);
            if (temp.length() > 0) {
                tempSb.append(map.get(t).charAt(u));
                while (map.get(len - 1).equals(tempSb.toString())) {
                    //u++;
                    tempSb.append(map.get(t).charAt(u));
                }
                if (!map.get(len - 1).equals(tempSb.toString())) {
                    map.put(len, tempSb.toString());
                    len++;
                }
            }
            temp = new StringBuilder(map.get(t));
        }
    }
}