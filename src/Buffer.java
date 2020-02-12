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
        //If buffer is full, client waits until there is available space
        while (msgQueue.size() == buffSize) {
            synchronized (this) {
                Client.yield();
            }
        }
        System.out.println("Client #" + msg.getClient().getClientId() + " sending message: \"" + msg.getQuery() + "\"");
        synchronized (this) {
            msgQueue.enqueue(msg);
            wait();
        }
    }

    public void retrieveMessage() throws InterruptedException {
        while (nClients > 0) {
            //Checks until there are messages available and clients are still active
            while (msgQueue.isEmpty()) {
                synchronized (this) {
                    Server.yield();
                }
                if (nClients == 0) break;
            }
            //Server wakes up and proceeds to answer a message
            synchronized (this) {
                if (!msgQueue.isEmpty()) {
                    Message currentMsg = msgQueue.dequeue();
                    System.out.println("Server received client #" + currentMsg.getClient().getClientId() + "'s query: \"" + currentMsg.getQuery() + "\"");
                    int answer = currentMsg.getQuery() + 1;
                    currentMsg.setAnswer(answer);
                    System.out.println("Server answered client #" + currentMsg.getClient().getClientId() + "'s query: \"" + currentMsg.getQuery() + "\"");
                    System.out.println("    with: \"" + answer + "\"");
                    notify();
                }
            }
        }
    }

    public void dimNumClients() {
        synchronized (this) {
            nClients--;
        }
    }

    public int getNumClients() {
        return nClients;
    }
}
