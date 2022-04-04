import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class Plane {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String n = sc.next();
        char first = n.charAt(0);
        char last = n.charAt(n.length() - 1);
        if (first > last){
            char temp = first;
            first = last;
            last = temp;
        }
        System.out.print("Window: " + first + " " + last);
        System.out.println();
        System.out.print("Aisle: ");
        char[] arr = new char[51];
        int ind = 0;
        for (int i = 0; i < n.length(); i++){
            if (n.charAt(i) == '.'){
                if (ind == 0 || arr[ind - 1] != n.charAt(i - 1)) {
                    arr[ind] = n.charAt(i - 1);
                    ind++;
                }
                arr[ind] = n.charAt(i + 1);
                ind++;
            }
        }
        Arrays.sort(arr);
        for (int i = arr.length - ind; i < arr.length; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
