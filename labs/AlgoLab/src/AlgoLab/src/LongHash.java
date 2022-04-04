import java.util.HashSet;

public class LongHash {
    public static void main(String[] args){
        HashSet<Long> hS = new HashSet<>();
        for (int i = 0; i < 10000; i++){
            long k = (long) (Math.random() * Integer.MAX_VALUE);
            long t = (long) (k + (k << 32));
            System.err.println(Long.toBinaryString(t));
            hS.add(t);
            //hS.add(k);
        }
    }
}
