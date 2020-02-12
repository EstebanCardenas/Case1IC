public class Server extends Thread {

    public final static String BLUE_BOLD = "\033[1;34m";
    public final static String GREEN_BOLD = "\033[1;32m";

    private int id;
    private static Buffer buffer;

    public Server(int i, Buffer buff) {
        id = i;
        buffer = buff;
    }

    @Override
    public void run() {
        //Attempts to retrieve messages
        System.out.println(GREEN_BOLD + "SERVER #" + id + " starting" + Client.RESET);
        try {
            buffer.retrieveMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Answers messages as long as buffer's queue is not empty
        System.out.println();
        System.out.println(BLUE_BOLD + "Server #" + id + " has finished" + Client.RESET);
    }
}
