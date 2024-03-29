package launcher;

import generictree.BinaryTree;

public class Application {

    public static void main(String[] args) {
        BinaryTree<Integer> binaryTree = new BinaryTree<>();
        binaryTree.add(6);
        binaryTree.add(4);
        binaryTree.add(8);
        binaryTree.add(3);
        binaryTree.add(5);
        binaryTree.add(7);
        binaryTree.add(9);

        binaryTree.forEach(Application::printFormatTree);
        System.out.println("\tSize -> " + binaryTree.getSize());

        binaryTree.delete(3);
        binaryTree.forEach(Application::printFormatTree);
        System.out.println("\tSize -> " + binaryTree.getSize());

        binaryTree.update(4, 10);
        binaryTree.forEach(Application::printFormatTree);
        System.out.println("\tSize -> " + binaryTree.getSize());

        System.out.println(binaryTree.contains(3));
        System.out.println(binaryTree.contains(10));

        binaryTree.stream().forEach(Application::printFormatTree);

        System.out.println();

        binaryTree.stream()
                .map(String::valueOf)
                .forEach(Application::printFormatTree);

        System.out.println();

        System.out.println(binaryTree.traverseInOrderDesc());

    }

    private static <T> void printFormatTree(T value) {
        System.out.printf("%s ", value);
    }
}
