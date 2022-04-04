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
public class ArrayQueue extends AbstractQueue implements Queue{
    private Object[] elements = new Object[5];
    private int front = -1;

    private int getRear(){
        if (front == -1){
            return -1;
        }
        return (front + size - 1) % elements.length;
    }

    @Override
    protected void enqueueImpl(Object element) {
        ensureCapacity(size + 1);
        if (front < 0){
            front = 0;
        }
        elements[(getRear() + 1) % elements.length] = element;
    }

    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            int tempRear = getRear();
            Object[] tempElements = elements;
            elements = new Object[2 * capacity];
            //Arrays.fill(elements, 0);
            if (front < tempRear) {
                System.arraycopy(tempElements, front, elements, 0, size);
            } else {
                System.arraycopy(tempElements, front, elements, 0,
                        tempElements.length - front);
                System.arraycopy(tempElements, 0, elements,
                        tempElements.length - front, tempRear + 1);
            }
            front = 0;
        }
    }

    @Override
    protected void dequeueImpl() {
        elements[front] = null;
        if (size == 1){
            front = -1;
        }
        else {
            front = (front + 1) % elements.length;
        }
    }

    @Override
    protected Object elementImpl() {
        return elements[front];
    }

    public void clearImpl(){
        elements = new Object[5];
        front = -1;
    }

    @Override
    protected void pushImpl(Object element) {
        ensureCapacity(size() + 1);
        if (front < 0){
            front = 0;
        }
        front = (front - 1 + elements.length) % elements.length;
        elements[front] = element;
    }

    @Override
    protected Object peekImpl() {
        return elements[getRear()];
    }

    @Override
    protected Object removeImpl() {
        Object res = elements[getRear()];
        elements[getRear()] = null;
        if (size == 1){
            front = -1;
        }
        return res;
    }
}
