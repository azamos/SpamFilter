
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class SpamFilterA {
    /* First, setting all paramaters according to the agreed upon n=100 */
    private static final int MAX_SPAM_ADDRESS_COUNT = 100;// n
    private static final double ERROR_THRESHOLD = 0.001;
    private static final double ln2 = Math.log(2);
    public static int m = (int) Math.ceil(1.2500 * (-MAX_SPAM_ADDRESS_COUNT / (ln2 * ln2)) * Math.log(ERROR_THRESHOLD));
    public static int k = (int) Math.ceil(ln2 * m / MAX_SPAM_ADDRESS_COUNT);
    private static final int nearestPrimeToM = 2161;// bigger: 2179
    private MessageDigest MD5;
    private MessageDigest SHA256;
    private Function<String, Integer> h1;
    private Function<String, Integer> h2;
    private Function<String, Integer>[] hashFunctions = new Function[k];
    private BitSet indicator = new BitSet(m);

    public static final int SAMPLE_SIZE = 10;

    public SpamFilterA() {
        setAlgorithms();
        h1 = defineHashFunction(MD5);
        h2 = defineHashFunction(SHA256);
        set_k_hash_functions();
    }

    private void setAlgorithms() {
        try {
            MD5 = MessageDigest.getInstance("MD5");
            SHA256 = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Function<String, Integer> defineHashFunction(MessageDigest MD) {
        Function<String, Integer> hashFunction = str -> {
            byte[] bytes = MD.digest(str.getBytes());
            BigInteger bigInteger = new BigInteger(1, bytes);
            /*
             * We have used Math.floorMod instead of regular modulu to avoid Integer
             * overflow
             */
            int res = Math.floorMod(bigInteger.intValue(), m);
            return res;
        };
        return hashFunction;
    }

    private void set_k_hash_functions() {
        for (int i = 0; i < k; i++) {
            final int final_i = i;
            /*
             * We have used Math.floorMod instead of regular modulu to avoid Integer
             * overflow
             */
            hashFunctions[i] = str -> Math.floorMod(h1.apply(str) + final_i * h2.apply(str), m);
        }
    }

    public void AddSpam(String emailAddress) {
        for (Function<String, Integer> function : hashFunctions) {
            int index = function.apply(emailAddress);
            indicator.set(index);
        }
    }

    public boolean IsSpam(String emailAddress) {
        for (Function<String, Integer> function : hashFunctions) {
            if (!indicator.get(function.apply(emailAddress)))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println();
        System.out.println("Starting Part A");
        int EXP_COUNT = 2000;
        int sum = 0;
        for (int i = 0; i < EXP_COUNT; i++) {
            sum += runExperiment();
        }
        System.out.println("Out of " + EXP_COUNT + " experminets run, " + sum + " were succesfull");
    }

    public static int runExperiment() {

        String[] spamAddresses = AddressesGenerator.GENERATE_EMAILS();
        ArrayList<String> removeSelected = new ArrayList<>(Arrays.asList(spamAddresses));
        ArrayList<String> randomlyChosen = new ArrayList<>();
        SpamFilterA myFilter = new SpamFilterA();
        Random r = new Random();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            int index = r.nextInt(removeSelected.size());
            String sfsaf = removeSelected.get(index);
            randomlyChosen.add(sfsaf);
            removeSelected.remove(index);
        }
        // System.out.println("removeSelected size is " + removeSelected.size());
        // for (String string : randomlyChosen) {
        // System.out.println(string + " was randomly chosen!");
        // }
        SpamFilterA spamFilter = new SpamFilterA();
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
        // System.out.println("false positives out of 500,000: " + count);
        if (fpr < 0.001)
            return 1;
        // System.out.println("fpr = " + fpr + " < " + 0.001 + " ! Part A successful!");
        // System.out.println("m = " + spamFilter.m + ", k = " + spamFilter.k);
        return 0;
    }
}