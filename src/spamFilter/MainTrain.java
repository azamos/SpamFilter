
//amos zohar - 311402812 
//shelly srour - 316384254
import java.util.ArrayList;
import java.util.Random;

public class MainTrain {
    public static final int SPAM_COUNT = 100;
    public static final int NON_SPAM_COUNT = 500000;
    public static final int SAMPLE_SIZE = 10;
    public static final int PART_C_RAND_AMOUNT = 50;
    public static final int PART_C_AMOUNT = 100000;

    public static void main(String[] args) {
        System.out.println();
        System.out.println();
        System.out.println("-----------PART A -----------");
        Boolean partAsuccess = true;
        ArrayList<String> spamAddresses = AddressesGenerator.GENERATE_EMAILS(SPAM_COUNT);
        ArrayList<String> removeSelected = new ArrayList<>(spamAddresses);
        ArrayList<String> randomlyChosen = new ArrayList<>();
        ArrayList<String> VALID_ADDRESSES = AddressesGenerator.GENERATE_EMAILS(NON_SPAM_COUNT);
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
        double fpr = (double) count / NON_SPAM_COUNT;
        System.out.println("false positives out of 500,000: " + count);
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
        double fpr2 = (double) count2 / NON_SPAM_COUNT;
        System.out.println("FOR PART2: false positives out of 500,000: " + count2);
        if (fpr2 <= 0.001 && partBsuccess)
            System.out.print("-----------PART B FINISHED SUCCESSFULLY -----------");
        else {
            System.out.print("fpr > 0.001, that is a mistake. fpr = " + fpr2);
            partBsuccess = false;
        }

        System.out.println();
        System.out.println();
        System.out.println("-----------PART C -----------");
        boolean partCsuccess = true;
        SpamFilterC spamFilterC = new SpamFilterC();
        for (String string : spamAddresses) {
            spamFilterC.AddSpam(string);
        }
        for (String string : randomlyChosen) {
            if (!spamFilterC.IsSpam(string)) {
                System.out.println("ERROR: " + string + " incorrectly NOT identified as spam!");
                partCsuccess = false;
            }
        }
        int count3 = 0;
        for (int i = 0; i < PART_C_AMOUNT; i++) {
            String validAddress = VALID_ADDRESSES.get(i);
            if (spamFilterC.IsSpam(validAddress))
                count3++;
        }
        System.out.println("false positive count for first 100000: " + count3);
        /*
         * Now to randomly remove 50 addresses from the spam list,
         * and then randomly pick another 50 addresses from the forst 10000 and mark
         * them as spam
         */
        removeSelected = new ArrayList<>(spamAddresses);
        randomlyChosen = new ArrayList<>();
        for (int i = 0; i < PART_C_RAND_AMOUNT; i++) {
            int index = r.nextInt(removeSelected.size());
            String sfsaf = removeSelected.get(index);
            randomlyChosen.add(sfsaf);
            removeSelected.remove(index);
        }
        // Now I have 50 random addressess I can remove from spamFilter C
        for (String spamAddress : randomlyChosen) {
            spamFilterC.RemoveSpam(spamAddress);
        }
        // Done. Now to pick 50 random indexes out of the first 100000
        ArrayList<Integer> removeIndexes = new ArrayList<>(PART_C_AMOUNT);
        for (int i = 0; i < PART_C_AMOUNT; i++) {
            removeIndexes.add(i);
        }
        ArrayList<Integer> randomIndexes = new ArrayList<>();
        for (int i = 0; i < PART_C_RAND_AMOUNT; i++) {
            int index = r.nextInt(removeIndexes.size());
            int sfsaf = removeIndexes.get(index);
            randomIndexes.add(sfsaf);
            removeIndexes.remove(index);
        }
        // Now randomIndexes got the 50 random indexes between 0 and 100000
        for (Integer index : randomIndexes) {
            spamFilterC.AddSpam(VALID_ADDRESSES.get(index));
        }
        // added the 50 random addresses from the 100000. Now, to finish checking FPs
        // for the remaining 400000
        int count3Dot1 = 0;
        for (int i = PART_C_AMOUNT; i < VALID_ADDRESSES.size(); i++) {
            String validAddress = VALID_ADDRESSES.get(i);
            if (spamFilterC.IsSpam(validAddress))
                count3Dot1++;
        }
        System.out.println("false positive count for REMAINING 400000: " + count3Dot1);
        count3 += count3Dot1;
        double fpr3 = (double) count3 / NON_SPAM_COUNT;
        System.out.println("false positives out of 500,000: " + count3);
        if (fpr3 <= 0.001 && partCsuccess)
            System.out.print("-----------PART C FINISHED SUCCESSFULLY -----------");
        else {
            System.out.print("fpr > 0.001, that is a mistake. fpr = " + fpr3);
            partCsuccess = false;
        }
        System.out.println();
        System.out.println();
        if (partAsuccess && partBsuccess && partCsuccess)
            System.out.println("-----------ALL PARTS SUCCEEDED! CONGRATULATIONS! -----------");
        else
            System.out.println("-----------FAILURE IN AT LEAST ONE PART. REVIEW AND REVISE! -----------");
    }
}