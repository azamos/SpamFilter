import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SpamFilterB extends SpamFilterA {
    StringSkipList skipList = new StringSkipList(0.5);

    @Override
    public void AddSpam(String emailAddress) {
        super.AddSpam(emailAddress);
        skipList.add(emailAddress);
    }

    @Override
    public boolean IsSpam(String emailAddress) {
        return super.IsSpam(emailAddress) && skipList.search(emailAddress) != null;
    }

    public int getSearchCount() {
        return skipList.getSearchCount();
    }

    public static void main(String[] args) {
        System.out.println();
        System.out.println();
        System.out.println("Starting Part B");
        String[] spamAddresses = AddressesGenerator.GENERATE_EMAILS();
        ArrayList<String> removeSelected = new ArrayList<>(Arrays.asList(spamAddresses));
        ArrayList<String> randomlyChosen = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            int index = r.nextInt(removeSelected.size());
            String sfsaf = removeSelected.get(index);
            randomlyChosen.add(sfsaf);
            removeSelected.remove(index);
        }

        SpamFilterB spamFilter = new SpamFilterB();
        for (String string : spamAddresses) {
            spamFilter.AddSpam(string);
        }
        for (String string : randomlyChosen) {
            if (!spamFilter.IsSpam(string))
                System.out.println("ERROR: " + string + " incorrectly NOT identified as spam!");
        }
        int countSpam1 = spamFilter.getSearchCount();
        System.out.println("Out of the " + randomlyChosen.size() + " Spam addresses, " + countSpam1);

    }
}
