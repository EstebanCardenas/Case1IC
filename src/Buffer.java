public class Buffer {
    private Integer nClients;
    private int buffSize;
    private Queue<Message> msgQueue;

    public Buffer(int bs, int numClients) {
        buffSize = bs;
        nClients = numClients;
        msgQueue = new Queue<>();
    }

    public void depositMessage(Message msg) throws InterruptedException {
        synchronized (this) {
            while (msgQueue.size() == buffSize) {
                Client.yield();
            }
            System.out.println("Client #" + msg.getClient().getId() + " sending message: \"" + msg.getQuery() + "\"");
            msgQueue.enqueue(msg);
            wait();
        }
    }

    public void retrieveMessage() throws InterruptedException {
        while (hasClients()) {
            while (msgQueue.isEmpty()) {
                Server.yield();
            }
            Message currentMsg = msgQueue.dequeue();
            System.out.println("Server received client #" + currentMsg.getClient().getId() + "'s query: \"" + currentMsg.getQuery() + "\"");
            int answer = currentMsg.getQuery() + 1;
            currentMsg.setAnswer(answer);
            System.out.println("Server answered client #" + currentMsg.getClient().getId() + "'s query: \"" + currentMsg.getQuery() + "\"");
            System.out.println("    with: \"" + answer + "\"");
            synchronized (this) {
                notify();
            }
        }
    }

    public void dimNumClients() {
        nClients--;
    }

    public boolean hasClients(){
        return nClients > 0;
    }
}
