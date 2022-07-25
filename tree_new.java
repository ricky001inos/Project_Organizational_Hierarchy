class avl_node {
    int id, height;
    avl_node left, right;
    customer cst;
    node_heap node;
}

public class tree_new {
    avl_node root;

    //function to get height of the tree
    int height(avl_node n) {
        if (n == null)
            return 0;
        return n.height;
    }

    // function to get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // function to right rotate subtree rooted with y
    avl_node rightRotate(avl_node y) {
        avl_node x = y.left;
        avl_node z = x.right;
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
    avl_node leftRotate(avl_node x) {
        avl_node y = x.right;
        avl_node z = y.left;
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
    int getBalance(avl_node n) {
        if (n == null)
            return 0;
        return height(n.left) - height(n.right);
    }

    //searching in avl tree
        avl_node find_tree(int id) {//to search customer id in the tree
            avl_node curr = root;
            while (curr != null) {
                if (curr.id < id) {//if id id > than node of tree then move right else left
                    curr = curr.right;
                } else if (curr.id > id) {
                    curr = curr.left;
                } else {
                    break;
                }
            }
            return curr;
        }


    //inserting in avl tree
    public avl_node insert(avl_node node, int key) {
        if (node == null) {
            avl_node n = new avl_node();
            n.height = 1;
            n.left = n.right = null;
            n.id = key;
            return n;//returning new node
        }
        if (key < node.id)//checking in left subtree
        {
            node.left = insert(node.left, key);
        } else if (key > node.id)//checking in right subtree
        {
            node.right = insert(node.right, key);
        } else // Equal keys not allowed
            return node;

        /* for updating height of this ancestor node */
        node.height = 1 + max(height(node.left), height(node.right));

        /*  find the balance factor of this boss node to check whether this node became unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then
        // Left Left Case
        if (balance > 1 && key < node.left.id)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.id)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
}

