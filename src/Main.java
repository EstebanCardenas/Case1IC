import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;

public class Main {

	public static void main(String... args) {
		int clients = 0;
		int servers = 0;
		int queries = 0;
		int buffSize = 0;
		//Reading configuration parameters
		// File f = new File ("./data/config.txt");
		// BufferedReader br = null;
//		try {
//
//		} catch (FileNotFoundException e) {
//			System.out.println("File not found");
//		}

		try {
			// br = new BufferedReader(new FileReader(f));
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("./data/config.txt")));
			String line = bufferedReader.readLine();
			clients = Integer.parseInt(line.split("=")[1]);
			line = bufferedReader.readLine();
			queries = Integer.parseInt(line.split("=")[1]);
			line = bufferedReader.readLine();
			servers = Integer.parseInt(line.split("=")[1]);
			line = bufferedReader.readLine();
			buffSize = Integer.parseInt(line.split("=")[1]);
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Finished reading config parameters
		System.out.println("Clients: " + clients);
		System.out.println("Queries per Client " + queries);
		System.out.println("Servers: " + servers);
		System.out.println("Buffer Size: " + buffSize);
		System.out.println();

		//Buffer creation
		final Buffer buffer = new Buffer(buffSize, clients);

		//Server creation
		Server[] serverList = new Server[servers];
		for (int i = 0; i < servers; i++) {
			serverList[i] = new Server(i, buffer);
			serverList[i].start();
		}

		//Client creation
		Client[] clientList = new Client[clients];
//		Random rand = new Random();
		SecureRandom secureRandom = new SecureRandom();
		for (int i = 0; i < clients; i++) {
			Queue<Integer> tQueue = new Queue<>();
			for (int j = 0; j < queries; j++) {
//				int rd = rand.nextInt(1000);
//				int randomNumber = secureRandom.nextInt(1000);
				tQueue.enqueue(secureRandom.nextInt(1000));
			}
			clientList[i] = new Client(i, tQueue, buffer);
			clientList[i].start();
		}

		for (int i = 0; i < servers; i++) {
			try {
				serverList[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < clients; i++) {
			try {
				clientList[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
