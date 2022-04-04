package info.kgeorgiy.ja.potekhin.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Class IterativeParallelism
 * Performs operations with lists ({@link List}) in multiple threads ({@link Thread})
 */
public class IterativeParallelism implements ListIP {

    private <T> List<List<? extends T>> splitForThreads(final int threads, final List<? extends T> values) {
        final List<List<? extends T>> list = new ArrayList<>();
        final int size = values.size() / threads;
        int rest = values.size() % threads;
        int start = 0;
        for (int i = 0; i < threads; i++) {
            int end = start + size + (rest-- > 0 ? 1 : 0);
            if (end > start) {
                list.add(values.subList(start, end));
            }
            start = end;
        }
        return list;
    }

    private <T, U> List<U> doInThreads(final Function<List<? extends T>, ? extends U> f, final List<List<? extends T>> values) {
        final List<U> res = new ArrayList<>(Collections.nCopies(values.size(), null));
        final List<Thread> threads = IntStream
                .range(0, values.size())
                .mapToObj(i -> {
                    final Thread t = new Thread(() -> res.set(i, f.apply(values.get(i))));
                    t.start();
                    return t;
                })
                .collect(Collectors.toList());

        for (final Thread thread : threads) {
            boolean joined = false;
            while (!joined) {
                try {
                    thread.join();
                    joined = true;
                } catch (InterruptedException ignored) {

                }
            }
        }
        return res;
    }

    private <T, U> U doSmth(
            final int threads,
            final List<? extends T> values,
            final Function<List<? extends T>, ? extends U> function,
            final Function<List<? extends U>, ? extends U> collector
    ) throws InterruptedException {
        if (values == null) {
            throw new InterruptedException("List shouldn't be null");
        }
        if (threads <= 0) {
            throw new InterruptedException("Number of threads should be positive");
        }
        return collector.apply(doInThreads(function, splitForThreads(threads, values)));
    }

    @Override
    public String join(final int threads, final List<?> values) throws InterruptedException {
        return doSmth(
                threads,
                values,
                s -> s.stream().map(Object::toString).collect(Collectors.joining()),
                s -> String.join("", s)
        );
    }

    @Override
    public <T> List<T> filter(final int threads, final List<? extends T> values, final Predicate<? super T> predicate) throws InterruptedException {
        return doSmth(
                threads,
                values,
                s -> s.stream().filter(predicate).collect(Collectors.toList()),
                getCollector()
        );
    }

    @Override
    public <T, U> List<U> map(final int threads, final List<? extends T> values, final Function<? super T, ? extends U> f) throws InterruptedException {
        return doSmth(
                threads,
                values,
                toStreamAndCollect(f),
//                toStreamAndCollect(s -> s.map(f)),
                getCollector()
        );
    }

    private <T, U> Function<List<? extends T>, List<U>> toStreamAndCollect(Function<? super T, ? extends U> f) {
        return s -> s.stream().map(f).collect(Collectors.toList());
    }

    private <U> Function<List<? extends List<U>>, List<U>> getCollector() {
        return s -> s.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    @Override
    public <T> T maximum(final int threads, final List<? extends T> values, final Comparator<? super T> comparator) throws InterruptedException {
        return doSmth(
                threads,
                values,
                s -> s.stream().max(comparator).orElse(null),
                s -> s.stream().max(comparator).orElse(null)
        );
    }

    @Override
    public <T> T minimum(final int threads, final List<? extends T> values, final Comparator<? super T> comparator) throws InterruptedException {
        return maximum(threads, values, comparator.reversed());
    }

    @Override
    public <T> boolean any(final int threads, final List<? extends T> values, final Predicate<? super T> predicate) throws InterruptedException {
        return doSmth(
                threads,
                values,
                s -> s.stream().anyMatch(predicate),
                s -> s.stream().anyMatch(Boolean::booleanValue)
        );
    }

    @Override
    public <T> boolean all(final int threads, final List<? extends T> values, final Predicate<? super T> predicate) throws InterruptedException {
        return !any(threads, values, predicate.negate());
    }
}
