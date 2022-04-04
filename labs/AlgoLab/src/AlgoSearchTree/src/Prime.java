public class Prime {
    public static void main(String[] args) {
        int a = 10000000;
        int a1 = a/2;
        for (int i = a1; i < a; i++){
            int temp = 0;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    temp = 1;
                }
            }
            if (temp == 0){
                int temp2 = 0;
                for (int j2 = 2; j2 < a - i; j2++) {
                    if ((a - i) % j2 == 0) {
                        temp2 = 1;
                    }
                }
                if (temp2 == 0){
                    System.out.println(i + " " + (a - i));
                }
            }
        }
    }
}
