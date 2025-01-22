
import java.util.ArrayList;
import java.util.Random;

public class SkipList<T> {
    private ArrayList<Node<T>> overList = new ArrayList<>();
    /* For convenience's sake, the overList will hold only the partialLists */
    private double P;
    private Node<T> bottom;
    private int depth = 0;// Depth of the overList, excluding the bottom layer

    public SkipList(double P) {
        this.P = P;
    }

    public SkipList() {
        super(0.5);
    }

    public T search(int key) {
        int i = depth - 1;
        while (i >= 0) {
            Node<T> curr = overList.get(i);
            while (curr != null) {
                if (curr.key < key)
                    curr = curr.next;
                else if (curr.key == key)
                    return curr;
                else if (curr.key > key)
                    i++;

            }
        }
        return null;
    }

    public void add(T item) {
        bottom.add(item);
        Random r = new Random();
        boolean cond;
        int i = 0;
        Node<T> newNode = new Node<>();
        while (i <= depth && (cond = r.nextDouble() <= P)) {
            if (i != depth) {
                overList.get(i).next 
            } else {
                LinkedList<T> newTop = new LinkedList<>();
                newTop.add(item);
                overList.add(newTop);
                depth++;
                break;// If we needed to add a layer, no need to continue
            }
            i++;
        }
    }
}