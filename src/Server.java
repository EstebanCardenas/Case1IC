public class Server extends Thread {

    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String GREEN_BOLD = "\033[1;32m";
    private static Buffer buffer;
    private final int id;

    public Server(final int id, final Buffer buffer) {
        this.id = id;
        Server.buffer = buffer;
    }

    @Override
    public void run() {
        //Attempts to retrieve messages
        System.out.println(GREEN_BOLD + "SERVER #" + id + " starting" + Client.RESET);
        buffer.retrieveMessage();
        //Answers messages as long as buffer's queue is not empty
        System.out.println("\n" + BLUE_BOLD + "Server #" + id + " has finished" + Client.RESET);
    }
}
