package info.kgeorgiy.ja.potekhin.arrayset;

import java.util.*;

// :NOTE: E extends Comparable<E>
public class ArraySet<E extends Comparable<E>> extends AbstractSet<E> implements NavigableSet<E> {
    private static class HeadTailList<T> extends AbstractList<T> implements RandomAccess {
        private final List<T> list;
        private boolean isFromTail = false;

        public HeadTailList(final Collection<T> collection) {
            this.list = List.copyOf(collection);
        }

        public HeadTailList(final HeadTailList<T> headTailList, final boolean isFromTail) {
            this.isFromTail = isFromTail;
            this.list = headTailList.list;
        }

        private int tailIndex(final int index){
            return size() - index - 1;
        }

        @Override
        public T get(final int index) {
            if (isFromTail){
                return list.get(tailIndex(index));
            } else {
                return list.get(index);
            }
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public HeadTailList<T> subList(final int from, final int to) {
            if (isFromTail) {
                return new HeadTailList<>(list.subList(tailIndex(to - 1), tailIndex(to) + 1));
            } else {
                return new HeadTailList<>(list.subList(from, to));
            }
        }
    }

    private final HeadTailList<E> list;
    private final Comparator<? super E> comparator;

    public ArraySet(final Collection<? extends E> collection, final Comparator<? super E> comparator) {
        final NavigableSet<E> set = new TreeSet<>(comparator);
        set.addAll(collection);
        this.list = new HeadTailList<>(set);
        this.comparator = comparator;
    }

    public ArraySet() {
        this(List.of(), null);
    }

    public ArraySet(final Comparator<? super E> comparator) {
        this(Collections.emptyList(), comparator);
    }

    public ArraySet(final Collection<? extends E> collection) {
        this(collection, null);
    }

    private E getByIndexWithException(final int index) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return list.get(index);
    }

    private int indexOf(final E e, final boolean lower, final boolean inclusive) {
        int index = Collections.binarySearch(list, e, comparator);
        if (index >= 0) {
            if (!inclusive) {
                if (lower) {
                    index--;
                } else {
                    index++;
                }
            }
        } else {
            index = -index - 1;
            if (lower) {
                index--;
            }
        }
        return index;
    }

    private E findByIndexWithNull(final int index) {
        if (index >= 0 && index < size()) {
            return list.get(index);
        } else {
            return null;
        }
    }

    @Override
    public E lower(final E e) {
        return findByIndexWithNull(indexOf(e, true, false));
    }

    @Override
    public E floor(final E e) {
        return findByIndexWithNull(indexOf(e, true, true));
    }

    @Override
    public E ceiling(final E e) {
        return findByIndexWithNull(indexOf(e, false, true));
    }

    @Override
    public E higher(final E e) {
        return findByIndexWithNull(indexOf(e, false, false));
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public NavigableSet<E> descendingSet() {
        // :NOTE: n log n
        return new ArraySet<>(new HeadTailList<>(list, true), Collections.reverseOrder(comparator));
    }


    @Override
    public Iterator<E> descendingIterator() {
        return descendingSet().iterator();
    }

    private int compare(final E first, final E second) {
        return comparator == null ? first.compareTo(second) : comparator.compare(first, second);
    }

    private NavigableSet<E> createSubSet(final E fromElement, final boolean fromInclusive, final E toElement, final boolean toInclusive) {
        final int indexFrom = indexOf(fromElement, false, fromInclusive);
        final int indexTo = indexOf(toElement, true, toInclusive);
        if (indexFrom > indexTo) {
            return new ArraySet<E>(comparator);
        } else {
            return new ArraySet<>(list.subList(indexFrom, indexTo + 1), comparator);
        }
    }

    @Override
    public NavigableSet<E> subSet(final E fromElement, final boolean fromInclusive, final E toElement, final boolean toInclusive) {
        if (compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException();
        }
        return createSubSet(fromElement, fromInclusive, toElement, toInclusive);
    }

    @Override
    public NavigableSet<E> headSet(final E toElement, final boolean inclusive) {
        if (isEmpty()){
            return this;
        } else {
            return createSubSet(first(), true, toElement, inclusive);
        }
    }

    @Override
    public NavigableSet<E> tailSet(final E fromElement, final boolean inclusive) {
        if (isEmpty()){
            return this;
        } else {
            return createSubSet(fromElement, inclusive, last(), true);
        }
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(final E fromElement, final E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(final E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(final E fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public E first() {
        return getByIndexWithException(0);
    }

    @Override
    public E last() {
        return getByIndexWithException(size() - 1);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override @SuppressWarnings("unchecked")
    public boolean contains(final Object o) {
        return Collections.binarySearch(list, (E) Objects.requireNonNull(o), comparator) >= 0;
    }
}
