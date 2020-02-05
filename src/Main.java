import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		int clients = 0;
		int servers = 0;
		int queries = 0;
		int buffSize = 0;
		
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

	}
	
}
