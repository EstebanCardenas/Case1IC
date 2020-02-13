public class Buffer {
    private final int bufferSize;
    private final Queue<Message> messageQueue;
    private Integer maxClients;

    public Buffer(final int bufferSize, final int maxClients) {
        this.bufferSize = bufferSize;
        this.maxClients = maxClients;
        messageQueue = new Queue<>();
    }

    public void depositMessage(Message msg) throws InterruptedException {
        //If buffer is full, client waits until there is available space
        while (messageQueue.size() == bufferSize) {
            synchronized (this) {
                Thread.yield();
            }
        }
        System.out.println("Client #" + msg.getClient().getClientId() + " sending message: \"" + msg.getQuery() + "\"");
        synchronized (this) {
            messageQueue.enqueue(msg);
            wait();
        }
    }

    public void retrieveMessage() {
        while (maxClients > 0) {
            //Checks until there are messages available and clients are still active
            while (messageQueue.isEmpty()) {
                synchronized (this) {
                    Thread.yield();
                }
                if (maxClients == 0) break;
            }
            //Server wakes up and proceeds to answer a message
            synchronized (this) {
                if (!messageQueue.isEmpty()) {
                    Message currentMsg = messageQueue.dequeue();
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

    public synchronized void dimNumClients() {
        maxClients--;
    }

    public int getNumClients() {
        return maxClients;
    }
}
