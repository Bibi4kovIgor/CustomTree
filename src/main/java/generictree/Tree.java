package generictree;

import java.util.stream.Stream;

public interface Tree<T> extends Iterable<T> {
    void add(T value);
    void delete(T value);
    void update(T oldValue, T newValue);
    boolean contains(T value);
    String traverseInOrderDesc();
    Stream<T> stream();
}
