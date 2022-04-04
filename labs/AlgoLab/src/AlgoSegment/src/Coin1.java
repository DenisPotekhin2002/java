import java.util.Stack;

public class Coin1 {
    public static void main(String[] args) {
        int n = 100000;
        int res = 0;
        for (int i = 0; i < n; i++){
            Stack<Integer> stack = new Stack<>();
            stack.push((int)(Math.random() * 2));
            stack.push((int)(Math.random() * 2));
            int k = 2;
            //int k = 1;
            while(true){
                k++;
                stack.push((int)(Math.random() * 2));
                int a1 = stack.pop();
                int a2 = stack.pop();
                int a3 = stack.pop();
                if (a1 == 0 && a2 == 1 && a3 == 0){
                    res += k;
                    break;
                }
                stack.push(a3);
                stack.push(a2);
                stack.push(a1);
            }
        }
        System.out.println(res / n);
    }
}
