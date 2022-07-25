class avl_Node {
    int key, height;
    avl_Node left, right;
    Node emp;

    avl_Node(int d, Node emp) {
        key = d;
        height = 1;
        Node e = new Node();
        e = emp;
        this.emp = e;
    }
}

public class AVL_tree {
    avl_Node root;

    //function to get height of the tree
    int height(avl_Node n) {
        if (n == null)
            return 0;
        return n.height;
    }

    // function to get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // function to right rotate subtree rooted with y
    avl_Node rightRotate(avl_Node y) {
        avl_Node x = y.left;
        avl_Node z = x.right;
        // Perform rotation
        x.right = y;
        y.left = z;
        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
        // Return new root
        return x;
    }

    // function to left rotate subtree rooted with x
    avl_Node leftRotate(avl_Node x) {
        avl_Node y = x.right;
        avl_Node z = y.left;
        // Perform rotation
        y.left = x;
        x.right = z;
        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
        // Return new root
        return y;
    }

    // Get Balance factor of node n
    int getBalance(avl_Node n) {
        if (n == null)
            return 0;
        return height(n.left) - height(n.right);
    }

    //searching in avl tree
    public avl_Node search(avl_Node node, int key) {
        if (node == null || key == node.key) {//if root  id is equal to employee id
            return node;
        }
        if (key < node.key) {//go to left subtree
            return search(node.left, key);
        }
        return search(node.right, key);//else go to right subtree
    }

    //inserting in avl tree
    avl_Node insert(avl_Node node, Node emp, int key) {
        if (node == null) {
            avl_Node n = new avl_Node(key, emp);
            n.height = 1;
            n.left = n.right = null;
            n.key = key;
            n.emp = emp;
            return n;//returning new node
        }
        if (key < node.key)//checking in left subtree
        {
            node.left = insert(node.left, emp, key);
        } else if (key > node.key)//checking in right subtree
        {
            node.right = insert(node.right, emp, key);
        } else // Equal keys not allowed
            return node;

        /* for updating height of this ancestor node */
        node.height = 1 + max(height(node.left), height(node.right));

        /*  find the balance factor of this boss node to check whether this node became unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then
        // Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    /* returns the node with minimum key value in that tree */
    avl_Node minValueNode(avl_Node node) {
        avl_Node current = node;
        /* finding the leftmost leaf */
        while (current.left != null)
            current = current.left;
        return current;
    }

    avl_Node delete(avl_Node root, int key) {
        if (root == null)
            return root;
        // If the key to be deleted is smaller than
        // the root's key, then move to left subtree
        if (key < root.key)
            root.left = delete(root.left, key);
            // If the key to be deleted is greater than the
            // root's key, then move to right subtree
        else if (key > root.key)
            root.right = delete(root.right, key);
            // if key is same as root's key, then this is the node delete node
        else {
            // for node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                avl_Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child present
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child
                    root = temp; //simply connect temp to parent of node to be deleted
            } else {
                // node with two children
                // Get the inorder successor (smallest in the right subtree)
                avl_Node temp = minValueNode(root.right);
                // Copy the inorder successor's data to this node
                root.key = temp.key;
                root.emp = temp.emp;
                // Delete the inorder successor
                root.right = delete(root.right, temp.key);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;
        root.height = max(height(root.left), height(root.right)) + 1;
        //get balance factor of node
        int balance = getBalance(root);

        // If this node becomes unbalanced
        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }
}


