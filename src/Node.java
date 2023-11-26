public class Node {
    char data;
    int weight;
    Node next = null;

    public Node(char d) {
        data = d;
        weight = 0;
        next = null;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node(char d, int w){
        data = d;
        weight = w;
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
