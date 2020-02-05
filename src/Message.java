public class Message {
    private int query;
    private int answer;

    public Message(int q, int a) {
        query = q;
        answer = a;
    }

    public int getQuery() {
        return query;
    }

    public void setQuery(int query) {
        this.query = query;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
