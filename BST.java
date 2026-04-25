public class BST<E extends Comparable<E>> implements Tree<E> {
    protected TreeNode<E> root;
    protected int size = 0;

    /**
     * Create an empty BST
     */
    public BST() {
    }

    /**
     * Create a BST from an array of elements
     */
    public BST(E[] objects) {
        for (int i = 0; i < objects.length; i++) {
    
            // Insert each array element into the tree.
          
            insert(objects[i]);
        }
    }

    public TreeNode<E> getRoot() {
        return root;
    }

    @Override
    public boolean search(E e) {
        TreeNode<E> current = root;

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
          
                // Move to the left child when e is smaller.
              
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {

                // Move to the right child when e is larger.
              
                current = current.right;
            } else {
                //return true when the element is found?
                // SOLUTION: return true;
                return true;
            }
        }

        //eturned false if element was not found

        return false;
    }

    @Override
    public boolean insert(E e) {
        if (root == null) {
            // Create the root node when the tree is empty.
       
            root = createNewNode(e);
        } else {
            TreeNode<E> parent = null;
            TreeNode<E> current = root;

            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    parent = current;

     
                    // Move left if e is smaller than current.element.
                 
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    parent = current;

         
                    // Move right if e is greater than current.element.
     
                    current = current.right;
                } else {
                    return false; // duplicate value
                }
            }

            if (e.compareTo(parent.element) < 0) {
     
                // Attach the new node as the left child of parent.
           
                parent.left = createNewNode(e);
            } else {
         
                // Attach the new node as the right child of parent.
     
                parent.right = createNewNode(e);
            }
        }

        size++;
        return true;
    }

    protected TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    @Override
    public void inorder() {
        inorder(root);
    }

    protected void inorder(TreeNode<E> root) {
        if (root == null) return;


        // In inorder, visit left subtree first.
      
        inorder(root.left);

        System.out.print(root.element + " ");

   
        // In inorder, visit right subtree last.
    
        inorder(root.right);
    }


    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean delete(E e) {
        TreeNode<E> parent = null;
        TreeNode<E> current = root;

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;

   
                // Move left while searching for the node to delete.
        
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                parent = current;

                // Move right while searching for the node to delete.
       
                current = current.right;
            } else {
                break;
            }
        }

        if (current == null)
            return false;

        // Case 1: current has no left child
        if (current.left == null) {
            if (parent == null) {
         
                // Deleting the root: replace root by current.right.
            
                root = current.right;
            } else {
                if (e.compareTo(parent.element) < 0) {
           
                    // Connect parent's left to current.right.
              
                    parent.left = current.right;
                } else {
              
                    // Connect parent's right to current.right.
                
                    parent.right = current.right;
                }
            }
        } else {
            // Case 2: current has a left child
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;

             
                // Keep moving to the rightmost node in the left subtree.
          
                rightMost = rightMost.right;
            }

            current.element = rightMost.element;

            if (parentOfRightMost.right == rightMost) {
      
                // Remove rightMost by connecting its parent to rightMost.left.
         
                parentOfRightMost.right = rightMost.left;
            } else {
     
                // Special case when parentOfRightMost == current.
         
                parentOfRightMost.left = rightMost.left;
            }
        }

        size--;
        return true;
    }

    @Override
    public java.util.Iterator<E> iterator() {
        return new InorderIterator();
    }

    private class InorderIterator implements java.util.Iterator<E> {
        private java.util.ArrayList<E> list = new java.util.ArrayList<>();
        private int current = 0;

        public InorderIterator() {
            inorder();
        }

        private void inorder() {
            inorder(root);
        }

        private void inorder(TreeNode<E> root) {
            if (root == null) return;
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        @Override
        public boolean hasNext() {
            return current < list.size();
        }

        @Override
        public E next() {
            return list.get(current++);
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    public static class TreeNode<E> {
        protected E element;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E e) {
            // TODO 25:
            // Store the given element in this node.
            // SOLUTION: element = e;
            element = e;
        }
    }
}
