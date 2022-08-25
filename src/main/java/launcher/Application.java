package launcher;

import generictree.BinaryTreeImplementation;

public class Application {
    public static void main(String[] args) {
        BinaryTreeImplementation<Integer> binaryTree = new BinaryTreeImplementation<>();
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
    }

    private static <T> void printFormatTree(T value) {
        System.out.printf("%s ", value);
    }
}
