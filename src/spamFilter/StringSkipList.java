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

    private ArrayList<StringNode> overList = new ArrayList<>(); // Skip list levels
    private double P; // Probability of promotion
    private int depth = 0; // Number of levels in the skip list

    public StringSkipList(double P) {
        this.P = P;
    }

    // Getter for search count
    public int getSearchCount() {
        return count_searches;
    }

    // Search for a key in the skip list
    public StringNode search(String key) {
        count_searches++;
        int i = depth - 1; // Start from the topmost level
        while (i >= 0) {
            StringNode curr = overList.get(i);
            while (curr != null && curr.key.compareTo(key) < 0) {
                curr = curr.next;
            }
            if (curr != null && curr.key.equals(key)) {
                return curr; // Key found
            }
            i--; // Drop to the next lower level
        }
        return null; // Key not found
    }

    // Add a new key to the skip list
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
                    // Insert between prev and curr
                    prev.next = newNode;
                    newNode.next = curr;
                }

            } else {
                // Create a new level
                overList.add(newNode);
                depth++;
            }

            // Prepare for promotion
            promotedNode = newNode; // Copy the reference
            newNode = new StringNode(key); // Create a new node for the higher level
            promote = r.nextDouble() < P; // Flip a coin
            level++;
        }
    }
}
