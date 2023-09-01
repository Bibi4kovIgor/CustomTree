package generictree;

import java.util.Iterator;

public interface Tree<T> extends Iterable<T> {
    void add(T value);
    void delete(T value);
    void update(T oldValue, T newValue);
    boolean contains(T value);
    T[] toArray();
    Iterator<T> iterator();
}
