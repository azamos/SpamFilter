import java.util.ArrayList;
import java.util.Random;

public class StringSkipList {
    private int count_searches = 0;

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

    public int getSearchCount() {
        return count_searches;
    }

    public StringNode search(String key) {
        count_searches++;
        int i = depth - 1; // Start from the topmost level
        while (i >= 0) {
            StringNode curr = overList.get(i);
            while (curr != null && curr.key.compareTo(key) < 0) {
                curr = curr.next;
            }
            if (curr != null && curr.key.equals(key)) {
                return curr;
            }
            i--;
        }
        return null;
    }

    public void add(String key) {
        Random r = new Random();
        boolean promote = true;
        int level = 0;

        StringNode newNode = new StringNode(key);
        StringNode promotedNode = null;

        while (promote) {
            if (level < depth) {
                // Insert in an existing level
                StringNode curr = overList.get(level);
                StringNode prev = null;

                while (curr != null && curr.key.compareTo(key) < 0) {
                    prev = curr;
                    curr = curr.next;
                }

                if (prev == null) {
                    // Insert at the head of the level
                    newNode.next = overList.get(level);
                    overList.set(level, newNode);
                } else {
                    prev.next = newNode;
                    newNode.next = curr;
                }

            } else {
                // Create a new level
                overList.add(newNode);
                depth++;
            }

            promotedNode = newNode;
            newNode = new StringNode(key);
            promote = r.nextDouble() < P;
            level++;
        }
    }
}
