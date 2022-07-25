class ll_node {
    ll_node next;
    Node emp;
    int id;

    ll_node itr_next() {//iterator to next element
        return this.next;
    }
}

public class Linked_List<Object> {
    ll_node first = null;
    ll_node last = null;
    int size = 0;

    Node head() {
        return first.emp;
    }

    ll_node itr_head() {//iterator to head of linked list
        return first;
    }

    boolean isEmpty() {//checks f linked list is empty
        if (size==0) {
            return true;
        } else {
            return false;
        }
    }

    void delete_head() {//for deleting head
        first = first.next;
    }

    int size() {
        return size;
    }

    Node find(int id) {//for searching element in list
        ll_node curr = first;
        while (curr.id != id&&curr!=null) {
            curr = curr.next;
        }
        if (curr.id == id) {
            return curr.emp;
        }
        return null;
    }

    void add(Node emp) {//inserting element in list
        ll_node node = new ll_node();
        node.emp = emp;
        node.id = emp.id;
        if (last != null) {
            last.next = node;
        }
        node.next = null;
        if (this.first == null) {
            this.first = node;
        }
        this.last = node;
        size++;
    }

    void delete_emp(int id) throws IllegalIDException {//deleting element from list
        ll_node node = first;
        if (first.id == id) {//if first element needs to be removed
            first = node.next;
            if (size == 1) {//if just 1 element is present n the last
                last = null;
            }
        } else {
            while (node.next != null) {//for finding element just before the element we want to remove
                if (node.next.id == id) {
                    break;
                }
                node = node.next;
            }
            if (node.next == null) {
                throw new IllegalIDException("illegal id");
            }
            ll_node temp = node.next;
            node.next = temp.next;
            if (last.id == id) {//if last element is removed
                last = node;
            }
        }
        size--;
    }
}
