package generictree;

import exceptions.WrongElementAccessorException;
import lombok.SneakyThrows;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BinaryTree<T extends Comparable<T>>
        implements Tree<T> {

    public static final String BINARY_TREE_IS_EMPTY = "Binary tree is empty";

    private Node<T> root;

    private Integer size;

    public Integer getSize() {
        return size;
    }

    private static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private abstract static class TreeIterator<E> implements Iterator<E> {

        protected final Queue<E> nodeList;
        protected final Node<E> root;

        private TreeIterator(Node<E> current) {
            this.nodeList = new ArrayDeque<>();
            this.root = current;
        }

        @Override
        public boolean hasNext() {
            return nodeList.size() != 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException(BINARY_TREE_IS_EMPTY);
            }
            return nodeList.poll();
        }
    }

    private final class TreeIteratorOrdered<E> extends TreeIterator<E> {
        public TreeIteratorOrdered(Node<E> current) throws WrongElementAccessorException {
            super(current);
            if (Objects.isNull(current)) {
                throw new WrongElementAccessorException("Tree is empty");
            }
            traverseInOrder(current);
        }

        private void traverseInOrder(Node<E> current) {
            if (Objects.nonNull(current)) {
                traverseInOrder(current.left);
                nodeList.add(current.value);
                traverseInOrder(current.right);
            }
        }

    }
    private final class TreeIteratorOrderedDesc<E> extends TreeIterator<E> {
        public TreeIteratorOrderedDesc(Node<E> current) throws WrongElementAccessorException {
            super(current);
            if (Objects.isNull(current)) {
                throw new WrongElementAccessorException("Tree is empty");
            }
            traverseInOrderDesc(current);
        }
        private void traverseInOrderDesc(Node<E> current) {
            if (Objects.nonNull(current)) {
                traverseInOrderDesc(current.right);
                nodeList.add(current.value);
                traverseInOrderDesc(current.left);
            }
        }
    }

    @Override
    public Stream<T> stream() {
        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator(), Spliterator.SIZED);
        return StreamSupport.stream(spliterator, false);
    }

    @SneakyThrows
    private Iterator<T> reversedIterator() {
        return new TreeIteratorOrderedDesc<>(root);
    }

    @Override
    @SneakyThrows
    public Iterator<T> iterator() {
        return new TreeIteratorOrdered<>(root);
    }

    public BinaryTree() {
        root = null;
        size = 0;
    }

    @Override
    public void add(T value) {
        root = addElementRecursive(root, value);
    }

    private Node<T> addElementRecursive(Node<T> current, T value) {
        if (Objects.isNull(current)) {
            size++;
            return new Node<>(value);
        }

        if (value.compareTo(current.value) < 0) {
            current.left = addElementRecursive(current.left, value);
        } else if (value.compareTo(current.value) > 0) {
            current.right = addElementRecursive(current.right, value);
        }

        return current;
    }

    @Override
    public void delete(T value) {
        root = deleteElementRecursively(root, value);
    }

    private Node<T> deleteElementRecursively(Node<T> current, T value) {
        if (Objects.isNull(current)) {
            return null;
        }

        if (value.equals(current.value)) {
            size--;
            return deleteNode(current);
        }

        if (value.compareTo(current.value) < 0) {
            current.left = deleteElementRecursively(current.left, value);
            return current;
        }

        current.right = deleteElementRecursively(current.right, value);

        return current;
    }

    private Node<T> deleteNode(Node<T> current) {

        if (Objects.isNull(current.left) && Objects.isNull(current.right)) {
            return null;
        }

        // Case 2: only 1 child
        if (Objects.isNull(current.right)) {
            return current.left;
        }

        if (Objects.isNull(current.left)) {
            return current.right;
        }

        // Case 3: 2 children
        T smallestValue = findSmallestValue(current.right);
        current.value = smallestValue;
        current.right = deleteElementRecursively(current.right, smallestValue);
        return current;
    }

    private T findSmallestValue(Node<T> current) {
        return Objects.isNull(current.left) ? current.value : findSmallestValue(current.left);
    }

    @Override
    public void update(T oldValue, T newValue) {
        if (contains(oldValue)) {
            delete(oldValue);
            add(newValue);
        }
    }

    @Override
    public boolean contains(T value) {
        return Objects.nonNull(root) && Objects.nonNull(getNodeByValue(root, value));
    }

    private Node<T> getNodeByValue(Node<T> current, T value) {
        Node<T> result = null;
        if(current.left != null) {
            result = getNodeByValue(current.left, value);
        }

        if(current.value == value)
            return current;
        if(Objects.isNull(result) && Objects.nonNull(current.right))
            result = getNodeByValue(current.right, value);

        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray(reversedIterator()));
    }

    @Override
    public String traverseInOrderDesc() {
        return Arrays.toString(toArray(reversedIterator()));
    }

    private Object[] toArray(Iterator<T> iterator) {
        Object[] array = new Object[size];

        for (int i = 0; iterator.hasNext(); i++) {
            array[i] = iterator.next();
        }
        return array;
    }
}
