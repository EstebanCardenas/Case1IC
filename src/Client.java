public class Client extends Thread {

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
		for (int i = 0; i < queries.size(); i++) {
			Message msg = new Message(queries.dequeue(), id);
			System.out.println("Sending message: " + queries.dequeue() + " by client #" + id);
			buffer.recieveMsg(msg);

		}
	}
}
