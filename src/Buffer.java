public class Buffer {
    private Integer nClients;
    private int buffSize;
    private Queue<Message> msgQueue;

    public Buffer(int bs) {
        buffSize = bs;
    }

    public synchronized void recieveMsg(Message msg) {
        if (nClients == null) nClients = 1;
        else nClients++;
        msgQueue.enqueue(msg);
    }

    public int getClients() {
        return nClients;
    }

    public int getBuffSize() {
        return buffSize;
    }

    public boolean avClients(){
        return nClients > 0;
    }
}
