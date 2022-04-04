package queue;

public abstract class AbstractQueue implements Queue {
    protected int size;

    @Override
    public void enqueue(Object element) {
        assert element != null;

        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    @Override
    public Object dequeue() {
        assert size() > 0;

        Object first = element();
        dequeueImpl();
        size--;
        return first;
    }

    protected abstract void dequeueImpl();

    @Override
    public Object element() {
        assert size() > 0;

        return elementImpl();
    }

    protected abstract Object elementImpl();

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    @Override
    public void push(Object element) {
        assert element != null;
        pushImpl(element);
        size++;
    }

    protected abstract void pushImpl(Object element);

    @Override
    public Object peek() {
        assert size() > 0;

        return peekImpl();
    }

    protected abstract Object peekImpl();

    @Override
    public Object remove() {
        assert size() > 0;

        Object last = removeImpl();
        size--;
        return last;
    }

    protected abstract Object removeImpl();

    private boolean contRem(Object element, int flag) {
        Object[] temp = new Object[size];
        int ind = 0;
        while (size > 0) {
            if (element().equals(element)) {
                if (flag == 0) {
                    dequeue();
                }
                repush(temp, ind);
                return true;
            }
            temp[ind] = dequeue();
            ind++;
        }
        repush(temp, ind);
        return false;
    }

    private void repush(Object[] temp, int ind) {
        int index = ind - 1;
        while (index >= 0) {
            push(temp[index]);
            index--;
        }
    }

    @Override
    public boolean removeFirstOccurrence(Object element) {
        return contRem(element, 0);
    }

    @Override
    public boolean contains(Object element) {
        return contRem(element, 1);
    }
}
