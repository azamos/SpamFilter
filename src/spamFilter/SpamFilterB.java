
public class SpamFilterB extends SpamFilter {
    StringSkipList spamAddresses = new StringSkipList(0.5);

    @Override
    public void AddSpam(String emailAddress) {
        super.AddSpam(emailAddress);
        spamAddresses.add(emailAddress);
    }

    @Override
    public boolean IsSpam(String emailAddress) {
        return super.IsSpam(emailAddress) && spamAddresses.search(emailAddress) != null;
    }
}
