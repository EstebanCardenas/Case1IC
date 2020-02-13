public class Message {
    private final Client client;
    private final int query;

    public Message(int q, Client cli) {
        client = cli;
        query = q;
    }

    public Client getClient() {
        return client;
    }

    public int getQuery() {
        return query;
    }

    public void setAnswer(int answer) {
    }
}
