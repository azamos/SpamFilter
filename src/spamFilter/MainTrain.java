
//amos zohar - 311402812 
//shelly srour - 316384254
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainTrain {
    public static final int SPAM_COUNT = 100;
    public static final int NON_SPAM_COUNT = 50000;
    public static final int SAMPLE_SIZE = 10;

    public static void main(String[] args) {
        System.out.println();
        System.out.println();
        System.out.println("-----------PART A -----------");
        Boolean partAsuccess = true;
        String[] spamAddresses = AddressesGenerator.GENERATE_EMAILS(SPAM_COUNT);
        ArrayList<String> removeSelected = new ArrayList<>(Arrays.asList(spamAddresses));
        ArrayList<String> randomlyChosen = new ArrayList<>();
        String[] VALID_ADDRESSES = AddressesGenerator.GENERATE_EMAILS(NON_SPAM_COUNT);
        Random r = new Random();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            int index = r.nextInt(removeSelected.size());
            String sfsaf = removeSelected.get(index);
            randomlyChosen.add(sfsaf);
            removeSelected.remove(index);
        }
        SpamFilterA spamFilter = new SpamFilterA();
        for (String string : spamAddresses) {
            spamFilter.AddSpam(string);
        }
        for (String string : randomlyChosen) {
            if (!spamFilter.IsSpam(string)) {
                System.out.println("ERROR: " + string + " incorrectly NOT identified as spam!");
                partAsuccess = false;
            }
        }
        int count = 0;
        for (String validAddress : VALID_ADDRESSES) {
            if (spamFilter.IsSpam(validAddress))
                count++;
        }
        double fpr = count / NON_SPAM_COUNT;
        System.out.println("false positives out of 500,000: " + count);
        // System.out.println("fpr is " + fpr + " , which is " + (fpr <= 0.001 ?
        // "indeed" : "NOT") + " <= 0.001");
        if (fpr <= 0.001 && partAsuccess)
            System.out.print("-----------PART A FINISHED SUCCESSFULLY -----------");
        else {
            System.out.print("fpr > 0.001, that is a mistake. fpr = " + fpr);
            partAsuccess = false;
        }

        System.out.println();
        System.out.println();
        System.out.println("-----------PART B STARTS HERE -----------");
        boolean partBsuccess = true;
        SpamFilterB spamFilterB = new SpamFilterB();
        for (String string : spamAddresses) {
            spamFilterB.AddSpam(string);
        }
        for (String string : randomlyChosen) {
            if (!spamFilterB.IsSpam(string)) {
                System.out.println("ERROR: " + string + " incorrectly NOT identified as spam!");
                partBsuccess = false;
            }
        }
        int countSpam1 = spamFilterB.getSearchCount();
        System.out.println("Out of the " + randomlyChosen.size() + " Spam addresses, " + countSpam1
                + " were tested to make sure they are indeed spam addresses and not False Positive Hits");
        if (countSpam1 != SAMPLE_SIZE) {
            partBsuccess = false;
            System.out.println(
                    "PART B failed. Reason: the spamfilter did not make sure that every spam address is indeed spam and not a FP");
        } else {
            System.out
                    .println("all " + countSpam1 + " randomly chosen spam addresses were further acertained to be so!");
        }
        int count2 = 0;
        for (String validAddress : VALID_ADDRESSES) {
            if (spamFilter.IsSpam(validAddress))
                count2++;
        }
        double fpr2 = count2 / NON_SPAM_COUNT;
        System.out.println("FOR PART2: false positives out of 500,000: " + count2);
        if (fpr2 <= 0.001 && partBsuccess)
            System.out.print("-----------PART B FINISHED SUCCESSFULLY -----------");
        else {
            System.out.print("fpr > 0.001, that is a mistake. fpr = " + fpr2);
            partBsuccess = false;
        }
    }
}