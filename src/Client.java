public class Client extends Thread {

	private int id;
	private Queue<Integer> queries;

	public Client(int i, Queue<Integer> q) {
		id = i;
		queries = q;
	}

	@Override
	public void run() {

	}
}
