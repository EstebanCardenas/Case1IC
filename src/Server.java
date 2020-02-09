
public class Server extends Thread {

    public final static String BLUE_BOLD = "\033[1;34m";
    public final static String GREEN_BOLD = "\033[1;32m";
    private static final class Lock {};
    private final Object lock = new Lock();

    private int id;
    private static Buffer buffer;

    public Server(int i, Buffer buff) {
        id = i;
        buffer = buff;
    }

    @Override
    public void run() {
        //Attempts to retrieve messages
        while (!buffer.hasMessages(this)) {
            synchronized (lock) {
                try {
                    lock.wait();
                    yield();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(GREEN_BOLD + "Server #" + id + " starting" + Client.RESET);
        //Answers messages as long as buffer's queue is not empty
        while (buffer.hasClients()) {
            answerMessage(buffer.retrieveMessage());
        }
        System.out.println(BLUE_BOLD + "Server #" + id + " has finished" + Client.RESET);
    }

    public int answerMessage(Message msg) {
        System.out.println("Server #" + id + " recieved client #" + msg.getClient().getId() + "'s query: \"" + msg.getQuery() + "\"");
        int answer = msg.getQuery() + 1;
        msg.setAnswer(answer);
        msg.setServerId(id);
        System.out.println("Server #" + id + "answered client #" + msg.getClient().getId() + "'s query: \"" + msg.getQuery() + "\"");
        System.out.println("    with: \"" + answer + "\"");
        msg.getClient().notify();
        return answer;
    }


}
