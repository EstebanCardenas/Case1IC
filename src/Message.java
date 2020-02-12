public class Message {
    private Client client;
    private int query;
    private int answer;

    public Message(int q, Client cli) {
        client = cli;
        query = q;
    }

    public Client getClient() { return client; }

    public int getQuery() {
        return query;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
