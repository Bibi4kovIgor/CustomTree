package generictree;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BinaryTreeImplementation<T extends Comparable<T>>
        implements Tree<T> {

    public static final String BINARY_TREE_IS_EMPTY = "Binary tree is empty";

    private Node<T> root;


    private Integer size = 0;

    public Integer getSize() {
        return size;
    }

    private static class Node <T> {
        T value;
        Node<T> left;
        Node<T> right;

        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new TreeItr<>(root);
    }

    private static class TreeItr<T> implements TreeIterator<T> {

        private final Queue<T> nodeList;

        public TreeItr(Node<T> root) {
            this.nodeList = new ArrayDeque<>();
            traverseInOrder(root);
        }

        private void traverseInOrder(Node<T> current) {
            if (Objects.nonNull(current)) {
                traverseInOrder(current.left);
                nodeList.add(current.value);
                traverseInOrder(current.right);
            }
        }

        @Override
        public boolean hasNext() {
            return nodeList.size() != 0;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException(BINARY_TREE_IS_EMPTY);
            }
            return nodeList.poll();
        }
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
        deleteElementRecursively(root, value);
    }

    private Optional<Node<T>> deleteElementRecursively(Node<T> current, T value) {
        if (Objects.isNull(current)) {
            return Optional.empty();
        }

        if (value.equals(current.value)) {
            size--;
            return deleteNode(current);
        }

        if (value.compareTo(current.value) < 0) {
            current.left = deleteElementRecursively(current.left, value).orElse(null);
            return Optional.of(current);
        }

        current.right = deleteElementRecursively(current.right, value).orElse(null);

        return Optional.of(current);
    }

    private Optional<Node<T>> deleteNode(Node<T> current) {
        // Case 1: no children
        if (Objects.isNull(current.left) && Objects.isNull(current.right)) {
            return Optional.empty();
        }

        // Case 2: only 1 child
        if (Objects.isNull(current.right)) {
            return Optional.of(current.left);
        }

        if (Objects.isNull(current.left)) {
            return Optional.of(current.right);
        }

        // Case 3: 2 children
        T smallestValue = findSmallestValue(current.right);
        current.value = smallestValue;
        current.right = deleteElementRecursively(current.right, smallestValue).orElse(null);
        return Optional.of(current);
    }

    private T findSmallestValue(Node<T> current) {
        return Objects.isNull(current.left) ? current.value : findSmallestValue(current.left);
    }

    @Override
    public void update(T oldValue, T newValue) {
        if (contains(oldValue)) {
            Node<T> node = getNodeByValue(root, oldValue);
            node.value = newValue;

        }
    }



    @Override
    public boolean contains(T value) {
        return Objects.nonNull(getNodeByValue(root, value));
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

}
