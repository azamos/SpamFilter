import java.util.function.Function;

public class SpamFilterC extends SpamFilterA {
    private int[] counter;

    public SpamFilterC() {
        super();
        counter = new int[m];
    }

    @Override
    public void AddSpam(String emailAddress) {
        for (Function<String, Integer> function : hashFunctions) {
            int index = function.apply(emailAddress);
            counter[index]++;
            indicator.set(index);
        }
    }

    public void RemoveSpam(String emailAddress) {
        for (Function<String, Integer> function : hashFunctions) {
            int index = function.apply(emailAddress);
            if (counter[index] > 0) {
                counter[index]--;
                if (counter[index] == 0) {
                    indicator.clear(index);
                }
            }
        }
    }
}
