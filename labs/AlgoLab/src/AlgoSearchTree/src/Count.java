import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class Count {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("Soundex.txt");
        Scanner sc = new Scanner(f);
        System.out.println(sc.hasNext());
        int count = 0;
        TreeMap<String, Integer> map = new TreeMap<>();
        while(sc.hasNext()){
            count++;
            String temp = sc.next();
            if (map.containsKey(temp)) {
                map.put(temp, map.get(temp) + 1);
            } else {
                map.put(temp, 1);
            }
        }
        int max = -1;
        String maxStr = null;
        for (String str: map.keySet()
             ) {
            if (map.get(str) > max){
                max = map.get(str);
                maxStr = str;
            }
        }
        System.out.println(maxStr + " " + max);
        System.out.println();
        for (String str: map.keySet()
        ) {
            System.out.println(str + " " + map.get(str));
        }
    }
}
