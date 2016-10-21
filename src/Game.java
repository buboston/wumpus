import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

	public static void main(String[] args)  throws IOException {

		String[] file_content = loadInputFile();
		Board board = new Board(file_content);		
	}
	
	public static String[] loadInputFile() throws IOException{
	    Scanner sys_scanner = new Scanner(System.in);
		System.out.print("Enter input file name: ");
		String file_name = sys_scanner.next();
		sys_scanner.close();
		    
		File in_file = new File (file_name);
		Scanner file_scanner = new Scanner (in_file);
		ArrayList<String> line_list = new ArrayList<String>();

		while (file_scanner.hasNextLine())
		{
			String line = file_scanner.nextLine();
		    line_list.add(line);
		}
		file_scanner.close();
		    
		String[] file_content = new String[line_list.size()];
		file_content = line_list.toArray(file_content);
		
		return file_content;
	}
}
