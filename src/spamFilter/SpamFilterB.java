
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
}
