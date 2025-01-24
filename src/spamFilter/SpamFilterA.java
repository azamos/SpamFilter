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
    // private static final int nearestPrimeToM = 2161;// bigger: 2179
    private MessageDigest MD5;
    private MessageDigest SHA256;
    private Function<String, Integer> h1;
    private Function<String, Integer> h2;
    Function<String, Integer>[] hashFunctions = new Function[k];
    BitSet indicator = new BitSet(m);

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
}