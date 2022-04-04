import java.rmi.ServerError;

public class Prostye {
    public static void main(String[] args) {
        int a = 7500197;
        int counter = 0;
        for (int i = 2; i < a; i++){
            int temp = 0;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    temp = 1;
                }
            }
            if (temp == 0){
                //System.err.println("1 - " + i);
                for (int i2 = 2; i2 < a - i - 1; i2++){
                    int temp2 = 0;
                    for (int j2 = 2; j2 < i2; j2++) {
                        if (i2 % j2 == 0) {
                            temp2 = 1;
                        }
                    }
                    if (temp2 == 0){
                        //System.err.println("2 - " + i2);
                        int temp3 = 0;
                        for (int j3 = 2; j3 < a - i - i2; j3++) {
                            if ((a - i - i2) % j3 == 0) {
                                temp3 = 1;
                            }
                        }
                        if (temp3 == 0){
                            //System.err.println("3 - " + (a - i - i2));
                            System.out.println(i + " " + i2 + " " + (a - i - i2));
                            counter++;
                        }
                    }
                }
            }
        }
        System.out.println("counter = " + counter);
    }
}
