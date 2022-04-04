package queue;
import java.util.*;

public class ArrayQueueRandomTest {
    public static void main(String[] args) {
        ArrayQueueWorking queue = new ArrayQueueWorking();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(9);
        queue.enqueue(10);
        queue.enqueue(11);
        queue.enqueue(12);
        System.out.println(queue.peek());
        System.out.println(queue.element());
        queue.clear();
        long start = Calendar.getInstance().getTimeInMillis();
        int els = 0;
        for (int i = 1; i <= 1000;i++){
            int k = (int)(Math.random() * 100) % 4;
            if (k == 0 || els == 0){
                queue.enqueue(i);
                els++;
            } else if (k == 1){
                Object p = queue.element();
                Object d = queue.dequeue();
                assert p == d;
                els--;
            } else if (k == 2){
                queue.push(i);
                els++;
            } else {
                Object p = queue.peek();
                Object d = queue.remove();
                assert p == d;
                els--;
            }
            System.out.println("Running test " + i);
        }
        long fin = Calendar.getInstance().getTimeInMillis();
        long time = fin - start;
        System.out.println("Test completed in " + time + "ms");
    }
}
