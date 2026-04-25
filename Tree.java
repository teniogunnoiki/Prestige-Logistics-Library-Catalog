public interface Tree<E> extends Iterable<E> {

    // Search for an element in the tree
    boolean search(E e);

    // inserting an element into the tree.
    boolean insert(E e);

    // Delete an element from the tree
    boolean delete(E e);

    // Print elements using inorder traversal
    void inorder();

    // returns the number of nodes in the tree.
    int getSize();

    // Remove all elements from the tree
    void clear();
}
