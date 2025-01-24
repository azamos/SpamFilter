
import java.util.ArrayList;
import java.util.Random;

public class AddressesGenerator {
    private static final String[] domains = { "gmail.com", "hotmail.com", "outlook.com", "yahoo.com" };
    private static final String[] names = { "amos", "BAR", "jaKE", "TRUFFLES", "SpOngEboB" };

    public static ArrayList<String> GENERATE_EMAILS(int addressesAmount) {
        ArrayList<String> emails = new ArrayList<>(addressesAmount);
        Random r = new Random();
        for (int i = 0; i < addressesAmount; i++) {
            String initial = names[r.nextInt(names.length)];
            int num = r.nextInt(9000) + 1000;// 1000<=num<=9999
            String domain = domains[r.nextInt(domains.length)];
            emails.add(initial + num + "@" + domain);
        }
        return emails;
    }
}
