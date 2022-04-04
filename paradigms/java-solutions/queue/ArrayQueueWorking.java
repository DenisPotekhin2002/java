package queue;

/*
    Model:
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

    Immutable: front..rear = front'..rear'
    (front..rear = a[front], a[front + 1], ..., a[rear] if rear >= front
    or a[front], a[front + 1], ... a[a.length - 1], a[0], a[1], ..., a[rear] else)
 */
public class ArrayQueueWorking {
    private Object[] elements = new Object[5];
    private int front = -1;
    private int rear = -1;

    //Pred: element != null
    //Post: n = n' + 1 && a[rear] = element && Immutable
    public void enqueue(Object element) {
        assert element != null;

        ensureCapacity(size() + 1);
        if (front < 0){
            front = 0;
        }
        rear = (rear + 1) % elements.length;
        elements[rear] = element;
    }

    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            Object[] tempElements = elements;
            elements = new Object[2 * capacity];
            //Arrays.fill(elements, 0);
            if (front < rear) {
                System.arraycopy(tempElements, front, elements, 0,
                        rear - front + 1);
                rear -= front;
            } else {
                System.arraycopy(tempElements, front, elements, 0,
                        tempElements.length - front);
                System.arraycopy(tempElements, 0, elements,
                        tempElements.length - front, rear + 1);
                rear += tempElements.length - front;
            }
            front = 0;
        }
    }

    //Pred: n > 0
    //Post: n = n' - 1 && R = a'[front'] && Immutable
    public Object dequeue() {
        assert size() > 0;

        Object first = elements[front];
        elements[front] = null;
        if (front == rear){
            front = -1;
            rear = -1;
        }
        else {
            front = (front + 1) % elements.length;
        }
        return first;
    }

    //Pred: n > 0
    //Post: R == a[front] && n == n' && Immutable
    public Object element() {
        assert size() > 0;

        return elements[front];
    }

    //Pred: true
    //Post: R == n && Immutable
    public int size() {
        if (front < 0){
            return 0;
        }
        return (rear - front + elements.length) % elements.length + 1;
    }

    //Pred: true
    //Post: R == (n == 0) && Immutable
    public boolean isEmpty() {
        return size() == 0;
    }

    //Pred: true
    //Post: n == 0
    public void clear(){
        elements = new Object[5];
        front = -1;
        rear = -1;
    }

    //Pred: element != null
    //Post: n = n' + 1 && a[front] = element && Immutable
    public void push(Object element) {
        assert element != null;

        ensureCapacity(size() + 1);
        if (front < 0){
            front = 0;
            rear = elements.length - 1;
        }
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
    }

    //Pred: n > 0
    //Post: R == a[rear] && n == n' && Immutable
    public Object peek() {
        assert size() > 0;

        return elements[rear];
    }

    //Pred: n > 0
    //Post: n = n' - 1 && R = a'[rear'] && Immutable
    public Object remove() {
        assert size() > 0;

        Object last = elements[rear];
        elements[rear] = null;
        if (front == rear){
            front = -1;
            rear = -1;
        }
        else {
            rear = (rear - 1 + elements.length) % elements.length;
        }
        return last;
    }
}
