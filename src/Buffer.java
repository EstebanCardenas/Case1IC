public class Buffer {
    private Integer nClients;
    private int buffSize;
    private Queue<Message> msgQueue;
    private Message currentMsg;

    public Buffer(int bs, int numClients) {
        buffSize = bs;
        msgQueue = new Queue<>();
        nClients = numClients;
        currentMsg = null;
    }

    public synchronized boolean depositMessage(Message msg) {
        if (buffSize != msgQueue.size()) {
            if (msg.getClient().getState() == Thread.State.WAITING) msg.getClient().notify();
            msgQueue.enqueue(msg);
            if (currentMsg == null)
                currentMsg = msg;
            buffSize++;
            return true;
        }
        return false;
    }

    public synchronized Message retrieveMessage() {
        Message sent = currentMsg;
        if (currentMsg != null) {
            currentMsg = msgQueue.dequeue();
            if (currentMsg != null)
                currentMsg.getClient().notify();
            buffSize--;
        }
        return sent;
    }

    public boolean hasMessages(Server sv) {
        if (!msgQueue.isEmpty()) {
            if (sv.getState() == Thread.State.WAITING) sv.notify();
            return true;
        }
        return false;
    }

    public void dimNumClients() {
        nClients--;
    }

    public boolean hasClients(){
        return nClients > 0;
    }
}
