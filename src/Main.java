import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int clients = 0;
		int servers = 0;
		int queries = 0;
		int buffSize = 0;
		//Reading configuration parameters
		File f = new File ("./data/config.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		try {
			String line = br.readLine();
			clients = Integer.parseInt(line.split(":")[1]);
			line = br.readLine();
			servers = Integer.parseInt(line.split(":")[1]);
			line = br.readLine();
			queries = Integer.parseInt(line.split(":")[1]);
			line = br.readLine();
			buffSize = Integer.parseInt(line.split(":")[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Finished reading config parameters
		System.out.println("Clients: " + clients);
		System.out.println("Servers: " + servers);
		System.out.println("Queries per Client " + queries);
		System.out.println("Buffer Size: " + buffSize);
		System.out.println();
		//Buffer creation
		Buffer buffer = new Buffer(buffSize, clients);

		//Server creation
		Server[] serverList = new Server[servers];
		for (int i = 0; i < servers; i++) {
			serverList[i] = new Server(i, buffer);
			serverList[i].start();
		}

		//Client creation
		Client[] clientList = new Client[clients];
		Random rand = new Random();
		for (int i = 0; i < clients; i++) {
			Queue<Integer> tQueue = new Queue<>();
			for (int j = 0; j < queries; j++) {
				int rd = rand.nextInt(1000);
				tQueue.enqueue(rd);
			}
			clientList[i] = new Client(i, tQueue, buffer);
			clientList[i].start();
		}
	}
	
}
