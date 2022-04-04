package queue;

public class ArrayQueueMyTest {
    public static void fill(ArrayQueueWorking queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(ArrayQueueWorking queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " +
                    queue.element() + " " + queue.dequeue());
        }
    }

    public static void main(String[] args) {
        ArrayQueueWorking queue = new ArrayQueueWorking();
        fill(queue);
        dump(queue);
        queue.clear();
        fill(queue);
        dump(queue);
        fill(queue);
        queue.clear();
        fill(queue);
        queue.clear();
        System.out.println(queue.size());
        fill(queue);
        System.out.println(queue.size());
        System.out.println(queue.peek());
        System.out.println(queue.element());
        System.out.println(queue.remove());
        System.out.println(queue.peek());
    }
}
