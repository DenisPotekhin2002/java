package queue;

public class LinkedQueue extends AbstractQueue implements Queue{
    private Node front;
    private Node rear;

    private static class Node {
        private final Object value;
        private Node next;
        private Node prev;

        public Node(Object value, Node prev, Node next) {
            assert value != null;

            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    protected void enqueueImpl(Object element) {
        rear = new Node(element, rear, null);
        if (rear.prev != null) {
            rear.prev.next = rear;
        }
        if (rear.prev == null || front == null){
            front = rear;
        }
    }

    @Override
    protected void dequeueImpl() {
        front = front.next;
        if (front != null){
            front.prev = null;
        }
    }

    @Override
    protected Object elementImpl() {
        return front.value;
    }

    @Override
    protected void clearImpl() {
        front = null;
        rear = null;
    }

    @Override
    protected void pushImpl(Object element) {
        front = new Node(element, null, front);
        if (front.next != null) {
            front.next.prev = front;
        }
        if (front.next == null || rear == null){
            rear = front;
        }
    }

    @Override
    protected Object peekImpl() {
        return rear.value;
    }

    @Override
    protected Object removeImpl() {
        Object res = rear.value;
        rear = rear.prev;
        if (rear != null){
            rear.next = null;
        }
        return res;
    }
}














