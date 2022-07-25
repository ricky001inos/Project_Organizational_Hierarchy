class Node {
    int level;
    Node boss;
    int id;
    Linked_List<Node> children;
}

public class OrgHierarchy implements OrgHierarchyInterface {
    //root node
    Node root;
    int size;

    AVL_tree tree = new AVL_tree();

    String int_to_string(int num) {//converting int to string type
        String str = "";//for storing integer as string in reverse order
        while (num > 0) {
            int n = num % 10;
            char c = (char) (n + '0');
            str = str + c;
            num = num / 10;
        }
        String ans = "";//for inverting str and return string of integer
        for (int i = str.length() - 1; i >= 0; i--) {
            ans = ans + str.charAt(i);//iteration over the string str for reversing it
        }
        return ans;
    }

    public Linked_List<Node> sort_ll(Linked_List<Node> ll) {//for sorting linked list to print employees at same level
        int r = ll.size();
        Node arr[] = new Node[r];
        int s = arr.length;
        int i = 0;
        for (ll_node itr = ll.itr_head(); itr != null; itr = itr.itr_next()) {
            arr[i] = itr.emp;
            i++;
        }
        int n = arr.length;
        for (i = 1; i < n; i++) {//sorting array of nodes in ascending order
            int key = arr[i].id;
            int j = i - 1;
                /* Move elements of arr[0 to i-1], that are
               greater than key, to one position ahead
               of their current position */
            Node temp = arr[i];
            Node temp2 = arr[i];
            while (j >= 0 && arr[j].id > key) {
                temp = arr[j + 1];
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = temp2;//inserting node at correct position
        }
        Linked_List<Node> ll2 = new Linked_List<>();//linked list to store sorted employees
        for (i = 0; i < n; i++) {
            ll2.add(arr[i]);//inserting sorted elements in a new linked list
        }
        return ll2;
    }

    public boolean isEmpty() throws NotEmptyException {
        //your implementation
        if (this.size == 0) {
            return true;
        } else {
            throw new NotEmptyException("Not empty");
        }
    }

    public int size() {
        //your implementation
        return this.size;
    }

    public int level(int id) throws IllegalIDException {
        //your implementation
        Node curr_employee = tree.search(tree.root, id).emp;//searching id in avl tree
        if (curr_employee == null) {
            throw new IllegalIDException("illegal id");
        } else {
            return curr_employee.level;
        }
    }

    public void hireOwner(int id) throws NotEmptyException {
        //your implementation
        if (this.size == 0) {//there is no owner previously
            Node owner = new Node();
            this.root = owner;
            owner.level = 1;
            owner.id = id;
            owner.boss = null;
            owner.children = new Linked_List<>();
            tree.root = tree.insert(tree.root, owner, id);//updating root of avl tree and inserting id in it
            this.size++;
        } else {
            throw new NotEmptyException("Not empty");
        }
    }

    public void hireEmployee(int id, int bossid) throws IllegalIDException {
        //your implementation
        if (tree.search(tree.root, id) != null || tree.search(tree.root, bossid) == null) {//if id present or bossid is not present in avl tree throw exception
            throw new IllegalIDException("illegal id");
        } else {
            Node employee = new Node();
            Node boss = tree.search(tree.root, bossid).emp;//search boss in avl
            employee.boss = boss;
            employee.id = id;
            employee.children = new Linked_List<>();
            employee.level = boss.level + 1;
            boss.children.add(employee);
            tree.root = tree.insert(tree.root, employee, id);//insert employee id in avl tree
            size++;
        }
    }

    public void fireEmployee(int id) throws IllegalIDException {
        //your implementation
        if (tree.search(tree.root, id) == null || root.id == id) {//if owner is fired throw exception
            throw new IllegalIDException("illegal id");
        } else {
            Node del_employee = tree.search(tree.root, id).emp;
            (del_employee.boss.children).delete_emp(id);//delete id from children of boss O(children)
            tree.root = tree.delete(tree.root, id);//delete from tree
            size--;
        }
    }

    public void fireEmployee(int id, int manageid) throws IllegalIDException {
        //your implementation
        if (tree.search(tree.root, id) == null || tree.search(tree.root, manageid) == null || id == root.id || manageid == root.id) {//if owner is fired throw exception
            throw new IllegalIDException("illegal id");
        } else {
            Node employee = tree.search(tree.root, id).emp;//search employee with id in avl tree//O(log(n))
            Node boss = employee.boss;
            tree.root = tree.delete(tree.root, id);//delete id from tree
            Node new_mangr = boss.children.find(manageid);//find manageid in children of boss of id
            if (new_mangr == null) {//if manageid not present
                throw new IllegalIDException("illegal id");
            }
            ll_node itr = employee.children.itr_head();//for iterating over children of id and adding them to those of manageid
            while (itr != null) {//O(no of children or n)
                new_mangr.children.add(itr.emp);
                itr.emp.boss = new_mangr;
                itr = itr.itr_next();
            }
            boss.children.delete_emp(id);//O(n)
            size--;
        }
    }

    public int boss(int id) throws IllegalIDException {//O(1) time cx.
        //your implementation
        Node employee = tree.search(tree.root, id).emp;//search id in tree
        if (employee == null) {
            throw new IllegalIDException("illegal id");
        }
        if (id == root.id) {
            return -1;
        } else {
            return employee.boss.id;
        }
    }

    public int lowestCommonBoss(int id1, int id2) throws IllegalIDException {
        //your implementation
        if (id1 == root.id || id2 == root.id) {//if one of id is owner
            return -1;
        }
        if (tree.search(tree.root, id1) == null || tree.search(tree.root, id2) == null) {//if ids are not present in tree
            throw new IllegalIDException("illegal id");
        }
        Node emp1 = tree.search(tree.root, id1).emp;
        Node emp2 = tree.search(tree.root, id2).emp;
        int boss1[] = new int[100];
        int idx1 = 0;
        int boss2[] = new int[100];
        int idx2 = 0;
        while (emp1.boss != null) {
            boss1[idx1] = emp1.boss.id;
            emp1 = emp1.boss;
            idx1++;
        }
        while (emp2.boss != null) {
            boss2[idx2] = emp2.boss.id;
            emp2 = emp2.boss;
            idx2++;
        }
        idx1--;
        idx2--;
        while (idx1 > -1 && idx2 > -1) {//checking from the end of array if boss matches for both ids O(height)
            if (boss1[idx1] != boss2[idx2]) {
                return boss1[++idx1];
            }
            idx1--;
            idx2--;
        }
        return boss1[0];
    }

    public String toString(int id) throws IllegalIDException {
        //your implementation
        Node emp = tree.search(tree.root, id).emp;
        String ans = "";
        Queue_nw Q = new Queue_nw<>();//queue for bfs traversal of hierarchy
        Linked_List ll = new Linked_List();
        int num = 0;
        Q.enqueue(emp);
        while (!Q.isEmpty()) {
            Node p = (Node) Q.dequeue();
            ll.add(p);//adding ordered employees in a level in linked list
            num++;
            for (ll_node itr = p.children.itr_head(); itr != null; itr = itr.itr_next()) {//iteration over children of each employee
                Q.enqueue(itr.emp);
            }
        }
        int level = 1;
        ll_node itr = ll.itr_head();
        int flag = 0;
        Linked_List temp = new Linked_List();//for string employees of a particular level
        Linked_List sorted = new Linked_List();//for storing final sorted employees of each level
        while (num > 0) {
            if (itr.emp.level == level) {
                temp.add(itr.emp);
                itr = itr.itr_next();
                num--;
            } else {
                sorted = sort_ll(temp);
                Linked_List x = new Linked_List();
                temp = x;
                for (ll_node itr_nw = sorted.itr_head(); itr_nw != null; itr_nw = itr_nw.itr_next()) {//for converting ids to string
                    if (flag == 0) {//to ensure there is no space at the beginning of string
                        ans = ans + int_to_string(itr_nw.id);
                        flag = 1;
                    } else {
                        ans = ans + " " + int_to_string(itr_nw.id);
                    }
                }
                level++;
            }
        }
        sorted = sort_ll(temp);
        for (ll_node itr_nw = sorted.itr_head(); itr_nw != null; itr_nw = itr_nw.itr_next()) {
            if (flag == 0) {//to ensure there is no space at the beginning of string
                ans = ans + int_to_string(itr_nw.id);
                flag = 1;
            } else {
                ans = ans + " " + int_to_string(itr_nw.id);
            }
        }
        return ans;
    }
}
