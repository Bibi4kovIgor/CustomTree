package generictree;

import java.util.Iterator;

public interface TreeIterator<T> extends Iterator<T> {
    @Override
    boolean hasNext();

    @Override
    T next();
}
