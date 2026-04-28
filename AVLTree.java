import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;

public class AVLTree<E extends Comparable<E>> implements Tree<E> {
    private Comparator<E> comparator;
    protected TreeNode<E> root;
    protected int size = 0;

    public AVLTree(){}

    public AVLTree(E[] objects) {
        for (E object : objects) {
            insert(object);
        }
    }

    // Tree interface implementation

    @Override
    public boolean search(E e) {
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) current = current.left;
            else if (e.compareTo(current.element) > 0) current = current.right;
            else return true;
        }
        return false;
    }

    @Override
    public boolean insert(E e) {
        if (root == null) {
            root = new AVLTreeNode<>(e);
        } else {
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                } else {
                    return false; // Duplicate node
                }
            }
            if (e.compareTo(parent.element) < 0) parent.left = new AVLTreeNode<>(e);
            else parent.right = new AVLTreeNode<>(e);
        }
        size++;
        balancePath(e);
        return true;
    }

    @Override
    public boolean delete(E element) {
        if (root == null) return false;

        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null) {
            if (element.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (element.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else break;
        }

        if (current == null) return false; // Element not in tree

        if (current.left == null) {
            if (parent == null) root = current.right;
            else {
                if (element.compareTo(parent.element) < 0) parent.left = current.right;
                else parent.right = current.right;
                balancePath(parent.element);
            }
        } else {
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;
            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            current.element = rightMost.element;
            if (parentOfRightMost.right == rightMost) parentOfRightMost.right = rightMost.left;
            else parentOfRightMost.left = rightMost.left;
            balancePath(parentOfRightMost.element);
        }
        size--;
        return true;
    }

    @Override
    public int getSize() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public void clear() { root = null; size = 0; }

 //balancing

    private void balancePath(E e) {
        ArrayList<TreeNode<E>> path = getPath(e);
        for (int i = path.size() - 1; i >= 0; i--) {
            AVLTreeNode<E> A = (AVLTreeNode<E>) path.get(i);
            updateHeight(A);
            AVLTreeNode<E> parentOfA = (A == root) ? null : (AVLTreeNode<E>) path.get(i - 1);

            switch (balanceFactor(A)) {
                case -2:
                    if (balanceFactor((AVLTreeNode<E>) A.left) <= 0) balanceLL(A, parentOfA);
                    else balanceLR(A, parentOfA);
                    break;
                case 2:
                    if (balanceFactor((AVLTreeNode<E>) A.right) >= 0) balanceRR(A, parentOfA);
                    else balanceRL(A, parentOfA);
                    break;
            }
        }
    }

    private int balanceFactor(AVLTreeNode<E> node) {
        if (node.right == null) return -node.height;
        if (node.left == null) return node.height;
        return ((AVLTreeNode<E>) node.right).height - ((AVLTreeNode<E>) node.left).height;
    }

    private void updateHeight(AVLTreeNode<E> node) {
        if (node.left == null && node.right == null) node.height = 0;
        else if (node.left == null) node.height = 1 + ((AVLTreeNode<E>) node.right).height;
        else if (node.right == null) node.height = 1 + ((AVLTreeNode<E>) node.left).height;
        else node.height = 1 + Math.max(((AVLTreeNode<E>) node.right).height, ((AVLTreeNode<E>) node.left).height);
    }

    private ArrayList<TreeNode<E>> getPath(E e) {
        ArrayList<TreeNode<E>> list = new ArrayList<>();
        TreeNode<E> current = root;
        while (current != null) {
            list.add(current);
            if (e.compareTo(current.element) < 0) current = current.left;
            else if (e.compareTo(current.element) > 0) current = current.right;
            else break;
        }
        return list;
    }

  //balancing by rotation

    private void balanceLL(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.left;
        if (A == root) root = B;
        else if (parentOfA.left == A) parentOfA.left = B;
        else parentOfA.right = B;

        A.left = B.right;
        B.right = A;
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
    }

    private void balanceRR(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.right;
        if (A == root) root = B;
        else if (parentOfA.left == A) parentOfA.left = B;
        else parentOfA.right = B;

        A.right = B.left;
        B.left = A;
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
    }

    private void balanceLR(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.left;
        TreeNode<E> C = B.right;
        if (A == root) root = C;
        else if (parentOfA.left == A) parentOfA.left = C;
        else parentOfA.right = C;

        A.left = C.right;
        B.right = C.left;
        C.left = B;
        C.right = A;
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
        updateHeight((AVLTreeNode<E>) C);
    }

    private void balanceRL(TreeNode<E> A, TreeNode<E> parentOfA) {
        TreeNode<E> B = A.right;
        TreeNode<E> C = B.left;
        if (A == root) root = C;
        else if (parentOfA.left == A) parentOfA.left = C;
        else parentOfA.right = C;

        A.right = C.left;
        B.left = C.right;
        C.left = A;
        C.right = B;
        updateHeight((AVLTreeNode<E>) A);
        updateHeight((AVLTreeNode<E>) B);
        updateHeight((AVLTreeNode<E>) C);
    }

    //Traversals

    @Override
    public void inorder() { inorder(root); }
    private void inorder(TreeNode<E> node) {
        if (node == null) return;
        inorder(node.left);
        System.out.println(node.element.toString()+ " ");
        inorder(node.right);
    }
    @Override
    public Iterator<E> iterator() { return new InorderIterator(); }

    private class InorderIterator implements Iterator<E> {
        private ArrayList<E> list = new ArrayList<>();
        private int current = 0;
        public InorderIterator() { fillList(root); }
        private void fillList(TreeNode<E> node) {
            if (node == null) return;
            fillList(node.left);
            list.add(node.element);
            fillList(node.right);
        }
        @Override public boolean hasNext() { return current < list.size(); }
        @Override public E next() { return list.get(current++); }
    }

    //Nodes
    public static class TreeNode<E> {
        protected E element;
        protected TreeNode<E> left, right;
        public TreeNode(E e) { this.element = e; }
    }

    protected static class AVLTreeNode<E> extends TreeNode<E> {
        protected int height = 0;
        public AVLTreeNode(E e) { super(e); }
    }
}
