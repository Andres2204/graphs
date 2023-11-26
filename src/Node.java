public class Node {
    char data;
    Node next = null;

    public Node(char d) {
        data = d;
        next = null;
    }

    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

}
