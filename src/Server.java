
public class Server extends Thread {

    private int id;
    private int buffSize;

    public Server(int i) {
        id = i;
    }

    @Override
    public void run() {

    }

    public synchronized int answerMessage(Message msg) {
        System.out.println("Server #" + id + " recieved client #" + msg.get);
        int answer = msg.getQuery() + 1;
        System.out.println();
    }


}
