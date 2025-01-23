
import java.util.ArrayList;
import java.util.Random;

public class StringSkipList {

    private class StringNode {
        String key;
        StringNode next;

        public StringNode(String key) {
            this.key = key;
        }
    }

    private ArrayList<StringNode> overList = new ArrayList<>();
    private double P;
    private int depth = 0;

    public StringSkipList(double P) {
        this.P = P;
    }

    public StringNode search(String key) {
        /* Start search at the shortest(Top Level) linked list */
        int i = depth - 1;
        while (i >= 0) {
            StringNode curr = overList.get(i);
            while (curr != null) {
                int res = curr.key.compareTo(key);
                if (res < 0)
                    curr = curr.next;
                else if (res == 0)
                    return curr;
                else if (res > 0)
                    i++;

            }
        }
        return null;
    }

    public void add(String key) {
        StringNode newAddress = new StringNode(key);
        Random r = new Random();
        boolean cond;
        int i = 0;
        do {
            if (i != depth) {
                StringNode p_i = overList.get(i);
                while (p_i.next != null) {
                    if (p_i.key.compareTo(key) < 0)
                        p_i = p_i.next;
                    else {
                        StringNode p_i_next = p_i.next;
                        p_i.next = newAddress;
                        if (p_i_next != null)
                            newAddress.next = p_i_next;
                    }
                }
                cond = r.nextDouble() <= P;
            } else {
                /* Create a new list starting with newAddress, and attach it to OverList */
                overList.add(newAddress);
                depth++;
                cond = false;
            }
            i++;
        } while (i <= depth && cond);
    }
}