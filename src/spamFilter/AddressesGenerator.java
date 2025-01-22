
import java.util.ArrayList;

public class AddressesGenerator {
    private static final String[] domains = { "gmail.com", "hotmail.com", "outlook.com", "yahoo.com" };
    private static final String[] names = { "amos", "BAR", "jaKE", "TRUFFLES", "SpOngEboB" };
    private static final String[] years = { "1992", "1977", "1985", "1963", "2003" };

    public static String[] GENERATE_EMAILS() {
        ArrayList<String> emails = new ArrayList<>(100);
        for (String domain : domains) {
            for (String name : names) {
                for (String year : years) {
                    emails.add(name + year + "@" + domain);
                }
            }
        }
        return emails.toArray(new String[0]);
    }
}
