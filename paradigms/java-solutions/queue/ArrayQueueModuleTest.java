package queue;

import static queue.ArrayQueueModule.*;

public class ArrayQueueModuleTest {
    public static void fill() {
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void dump() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    size() + " " +
                            ArrayQueueModule.element() + " " +
                            ArrayQueueModule.dequeue()
            );
        }
    }

    public static void main(String[] args) {
        fill();
        dump();
        fill();
        clear();
        fill();
        clear();
        System.out.println(size());
        fill();
        System.out.println(size());
        System.out.println(peek());
        System.out.println(element());
        System.out.println(remove());
        System.out.println(peek());
    }
}