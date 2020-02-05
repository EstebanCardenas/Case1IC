import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		static 
		
		File f = new File (".data/config");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
		
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
