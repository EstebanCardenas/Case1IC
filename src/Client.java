public class Client extends Thread {

    public static final String RED_BOLD = "\033[1;31m";
    public static final String RESET = "\033[0m";
    private static Buffer buffer;
    private final int id;
    private final Queue<Integer> queries;

    public Client(final int id, final Queue<Integer> queries, final Buffer buffer) {
        this.id = id;
        this.queries = queries;
        Client.buffer = buffer;
    }

    @Override
    public void run() {
        System.out.println(Server.GREEN_BOLD + "CLIENT #" + id + " starting" + RESET);
        //Attempts to store messages in buffer
        while (!queries.isEmpty()) {
            sendMessage();
        }
        //All messages have been sent and an answer was recieved for all of them
        System.out.println(RED_BOLD + "Client #" + id + " has no remaining queries" + RESET);
        System.out.println(RED_BOLD + "Client #" + id + " exiting buffer" + RESET);
        buffer.dimNumClients();
        System.out.println("CLIENTS REMAINING: " + buffer.getNumClients());
    }

    public void sendMessage() {
        if (!queries.isEmpty()) {
            //Pulls a message out of the message queue
            Message msg = new Message(queries.dequeue(), this);
            try {
                buffer.depositMessage(msg);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public int getClientId() {
        return id;
    }
}
