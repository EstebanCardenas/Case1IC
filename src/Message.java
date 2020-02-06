public class Message {
    private int clientId;
    private int query;
    private int answer;

    public Message(int q, int id) {
        clientId = id;
        query = q;
    }



    public int getQuery() {
        return query;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
