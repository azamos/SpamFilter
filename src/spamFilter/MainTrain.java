import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainTrain {
    private static final int MAX_ADDRESSES = 100;
    private static final int SAMPLE_SIZE = 10;

    public static void main(String[] args) {
        String[] spamAddresses = AddressesGenerator.GENERATE_EMAILS();
        ArrayList<String> removeSelected = new ArrayList<>(Arrays.asList(spamAddresses));
        ArrayList<String> randomlyChosen = new ArrayList<>();
        SpamFilter myFilter = new SpamFilter();
        Random r = new Random();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            int index = r.nextInt(removeSelected.size());
            String sfsaf = removeSelected.get(index);
            randomlyChosen.add(sfsaf);
            removeSelected.remove(index);
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Starting Part A");
        // System.out.println("removeSelected size is " + removeSelected.size());
        // for (String string : randomlyChosen) {
        // System.out.println(string + " was randomly chosen!");
        // }
        SpamFilter spamFilter = new SpamFilter();
        for (String string : spamAddresses) {
            spamFilter.AddSpam(string);
        }
        for (String string : randomlyChosen) {
            if (!spamFilter.IsSpam(string))
                System.out.println("ERROR: " + string + " incorrectly NOT identified as spam!");
        }
        int count = 0;
        for (int i = 1; i < 500001; i++) {
            if (spamFilter.IsSpam(("valid#" + r.nextDouble() + "@valid.com")))
                count++;
        }
        double fpr = count / 500000.0000;
        System.out.println("false positives out of 500,000: " + count);
        if (fpr < 0.001)
            System.out.println("fpr = " + fpr + " < " + 0.001 + " ! Part A successful!");
        // System.out.println("m = " + spamFilter.m + ", k = " + spamFilter.k);
    }
}
