public class Client extends Thread {

	public final static String RED_BOLD = "\033[1;31m";
	public final static String RESET = "\033[0m";

	private int id;
	private Queue<Integer> queries;
	private static Buffer buffer;

	public Client(int i, Queue<Integer> q, Buffer bf) {
		id = i;
		queries = q;
		buffer = bf;
	}

	@Override
	public void run() {
		System.out.println(Server.GREEN_BOLD + "Client #" + id + " starting" + Client.RESET);
		//Attempts to store messages in buffer
		while(!queries.isEmpty())
			sendMessage();
		//All messages have been sent and an answer was recieved for all of them
		System.out.println(RED_BOLD + "Client #" + id + " has no remaining queries" + RESET);
		System.out.println("Client #" + id + " exiting buffer");
		buffer.dimNumClients();
	}

	public 	void sendMessage() {
		if (!queries.isEmpty()) {
			//Pulls a message out of the message queue
            Message msg = new Message(queries.dequeue(), this);
            try {
            	buffer.depositMessage(msg);
			} catch	(Exception e) {
            	e.printStackTrace();
			}
		}
	}

}
