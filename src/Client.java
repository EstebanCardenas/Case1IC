public class Client extends Thread {

	public final static String RED_BOLD = "\033[1;31m";
	public final static String RESET = "\033[0m";
	private static final class Lock {};
	private final Object lock = new Lock();

	private int id;
	private Queue<Integer> queries;
	private static Buffer buffer;
	private Queue<Message> messages;

	public Client(int i, Queue<Integer> q, Buffer bf) {
		id = i;
		queries = q;
		buffer = bf;
		messages = new Queue<>();
	}

	@Override
	public void run() {
		//Messages are generated
		for (int i = 0; i < queries.size(); i++)
			generateMessage();
		//Attempts to store messages in buffer
		while(!queries.isEmpty())
			sendMessage();
		//All messages have been sent and an answer was recieved for all of them
		System.out.println(RED_BOLD + "Client #" + id + " has no remaining queries" + RESET);
		System.out.println("Client #" + id + " exiting buffer");
		buffer.dimNumClients();
	}

	public void generateMessage() {
		Message msg = new Message(queries.dequeue(), this);
		messages.enqueue(msg);
	}

	public synchronized void sendMessage() {
		if (!messages.isEmpty()) {
			//Pulls a message out of the message queue
			Message msg = messages.dequeue();
			System.out.println("Client #" + id + " sending message: \"" + msg.getQuery() + "\"");
			//Waits if buffer is full and continues until there is space available
			while (!buffer.depositMessage(msg)) {
				synchronized (lock) {
					try {
						lock.wait();
						yield();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			//Waits for the message to be answered
			try {
				wait();
				yield();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Client is woken up and an answer has been recieved
			notifyAll();
		}
	}

}
