package queue;

/*
    queue.Model:
        [a1 ... an]
        ^-front  ^-rear

        (
        [a(i+1), a(i+2), ... an, ..., a1, a2, ... ai]
                      rear----^        ^---front         )

        n - размер очереди
        front - индекс первого элемента или -1, если n == 0
        rear - индекс последнего элемента или -1, если n == 0

    Inv:
        n >= 0
        forall i = 1..n: a[i] != null

    Immutable: queue.front..queue.rear = queue.front'..queue.rear'
    (front..rear = a[front], a[front + 1], ..., a[rear] if rear >= front
    or a[front], a[front + 1], ... a[a.length - 1], a[0], a[1], ..., a[rear] else)
 */
public class ArrayQueueADT {
    private /*static*/ Object[] elements = new Object[5];
    private /*static*/ int front = -1;
    private /*static*/ int rear = -1;

    //Pred: queue != null && element != null
    //Post: queue.n = queue.n' + 1 && queue.a[rear] = element && Immutable
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert queue != null && element != null;

        ensureCapacity(queue,size(queue) + 1);
        if (queue.front < 0){
            queue.front = 0;
        }
        queue.rear = (queue.rear + 1) % queue.elements.length;
        queue.elements[queue.rear] = element;
    }

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity > queue.elements.length) {
            Object[] tempElements = queue.elements;
            queue.elements = new Object[2 * capacity];
            if (queue.front < queue.rear) {
                System.arraycopy(tempElements, queue.front, queue.elements, 0,
                        queue.rear - queue.front + 1);
                queue.rear -= queue.front;
            } else {
                System.arraycopy(tempElements, queue.front, queue.elements, 0,
                        tempElements.length - queue.front);
                System.arraycopy(tempElements, 0, queue.elements,
                        tempElements.length - queue.front, queue.rear + 1);
                queue.rear += tempElements.length - queue.front;
            }
            queue.front = 0;
        }
    }

    //Pred: queue != null && queue.n > 0
    //Post: queue.n = queue.n' - 1 && R = queue.a'[queue.front'] && Immutable
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue != null && size(queue) > 0;

        Object first = queue.elements[queue.front];
        queue.elements[queue.front] = null;
        if (queue.front == queue.rear){
            queue.front = -1;
            queue.rear = -1;
        }
        else {
            queue.front = (queue.front + 1) % queue.elements.length;
        }
        return first;
    }

    //Pred: queue != null && n > 0
    //Post: R == queue.a[queue.front] && queue.n == queue.n' && Immutable
    public static Object element(ArrayQueueADT queue) {
        assert queue != null && size(queue) > 0;

        return queue.elements[queue.front];
    }

    //Pred: queue != null
    //Post: R == queue.n && Immutable
    public static int size(ArrayQueueADT queue) {
        assert queue != null;

        if (queue.front < 0){
            return 0;
        }
        return (queue.rear - queue.front + queue.elements.length) % queue.elements.length + 1;
    }

    //Pred: queue != null
    //Post: R == (queue.n == 0) && Immutable
    public static boolean isEmpty(ArrayQueueADT queue) {
        assert queue != null;

        return size(queue) == 0;
    }

    //Pred: queue != null
    //Post: queue.n == 0
    public static void clear(ArrayQueueADT queue){
        assert queue != null;

        queue.elements = new Object[5];
        queue.front = -1;
        queue.rear = -1;
    }

    //Pred: queue != null && element != null
    //Post: queue.n = queue.n' + 1 && queue.a[queue.front] = queue.element && Immutable
    public static void push(ArrayQueueADT queue, Object element) {
        assert queue != null && element != null;

        ensureCapacity(queue,size(queue) + 1);
        if (queue.front < 0){
            queue.front = 0;
            queue.rear = queue.elements.length - 1;
        }
        queue.front = (queue.front - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.front] = element;
    }

    //Pred: queue != null && n > 0
    //Post: R == queue.a[queue.rear] && queue.n == queue.n' && Immutable
    public static Object peek(ArrayQueueADT queue) {
        assert queue != null && size(queue) > 0;

        return queue.elements[queue.rear];
    }

    //Pred: queue != null && n > 0
    //Post: queue.n = queue.n' - 1 && R = queue.a'[queue.rear'] && Immutable
    public static Object remove(ArrayQueueADT queue) {
        assert queue != null && size(queue) > 0;

        Object last = queue.elements[queue.rear];
        queue.elements[queue.rear] = null;
        if (queue.front == queue.rear){
            queue.front = -1;
            queue.rear = -1;
        }
        else {
            queue.rear = (queue.rear - 1 + queue.elements.length) % queue.elements.length;
        }
        return last;
    }
}
