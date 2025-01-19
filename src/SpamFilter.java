package src;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class SpamFilter {
    /* First, setting all paramaters according to the agreed upon n=100 */
    private static final int MAX_SPAM_ADDRESS_COUNT = 100;// n
    private static final double ERROR_THRESHOLD = 1 / 1000;
    private static final double ln2 = Math.log(2);
    private static int m = Math.ceil((-MAX_SPAM_ADDRESS_COUNT / (ln2 * ln2)) * Math.log(ERROR_THRESHOLD));
    private static int k = Math.ceil(ln2 * m / MAX_SPAM_ADDRESS_COUNT);
    private static final MessageDigest MD5 = MessageDigest.getInstance("MD5");
    private static final MessageDigest SHA256 = MessageDigest.getInstance("SHA256");
    private Function<String, Integer> h1;
    private Function<String, Integer> h2;
    private Function<String, Integer>[] hashFunctions = new Function[k];
    private BitSet indicator = new BitSet(m);

    public SpamFilter() {
        h1 = defineHashFunction(MD5);
        h2 = defineHashFunction(SHA256);
        set_k_hash_functions();
    }

    private Function<String, Integer> defineHashFunction(MessageDigest MD) {
        Function<String, Integer> hashFunction = str -> {
            byte[] bytes = MD.digest(str.getBytes());
            BigInteger bigInteger = new BigInteger(1, bytes);
            /*
             * I've used Math.floorMod instead of regular modulu to avoid Integer overflow
             */
            return Math.floorMod(bigInteger.intValue(), m);
        };
        return hashFunction;
    }

    private void set_k_hash_functions() {
        for (int i = 0; i < k; i++) {
            /*
             * I've used Math.floorMod instead of regular modulu to avoid Integer overflow
             */
            hashFunctions[i] = str -> Math.floorMod(h1.apply(str) + i * h2.apply(str), m);
        }
    }

    public void AddSpam(String emailAddress) {
        for (Function<String, Integer> function : hashFunctions) {
            indicator.set(function.apply(emailAddress));
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