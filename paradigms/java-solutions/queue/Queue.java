package queue;

/*
    Model:
        [a1 ... an]
        ^-front  ^-rear

        (
        [a(i+1), a(i+2), ... an, ..., a1, a2, ... ai]
                      rear----^        ^---front         )

        n - размер очереди
        front - индекс первого элемента
        rear - индекс последнего элемента

    Inv:
        n >= 0
        forall i = 1..n: a[i] != null

    Immutable: front..rear = front'..rear'
    (front..rear = a[front], a[front + 1], ..., a[rear] if rear >= front
    or a[front], a[front + 1], ... a[a.length - 1], a[0], a[1], ..., a[rear] else)
 */

public interface Queue {
    //Pred: element != null
    //Post: n = n' + 1 && a[rear] = element && Immutable
    void enqueue(Object element);

    //Pred: n > 0
    //Post: n = n' - 1 && R = a'[front'] && Immutable
    Object dequeue();

    //Pred: n > 0
    //Post: R == a[front] && n == n' && Immutable
    Object element();

    //Pred: true
    //Post: R == n && Immutable
    int size();

    //Pred: true
    //Post: R == (n == 0) && Immutable
    boolean isEmpty();

    //Pred: true
    //Post: n == 0
    void clear();

    //Pred: element != null
    //Post: n = n' + 1 && a[front] = element && Immutable
    void push(Object element);

    //Pred: n > 0
    //Post: R == a[rear] && n == n' && Immutable
    Object peek();

    //Pred: n > 0
    //Post: n = n' - 1 && R = a'[rear'] && Immutable
    Object remove();


    //k = min{j: a[j] == element || j == n}

    //Pred: element != null
    //Post: R = (k < n) && Immutable
    boolean contains(Object element);

    //Pred: element != null
    //Post: R = (k < n);
    //a: [a'1 a'2 ... a'(k-1) a'(k + 1) ... a'n]
    boolean removeFirstOccurrence(Object element);
}
