package exercisetwo;
import java.util.*;
import java.io.*;
public class Main {

    public static void main(String[] args) throws IOException{
		MenuService menu = new MenuService();
		FileIOService fileIO = new FileIOService();
	
		Scanner input = new Scanner(System.in);
		String fileName = "";
		
        try {
            if (args.length > 0) {
                fileName = args[0];
				fileIO.readFile(fileName);
            }else {
				System.out.println("No selected File");
				menu.reset();
			}
			
        }
        catch (Exception e) {
			System.out.println("File not found!");
			menu.reset();
        }
		
		menu.displayOptions();
    }
}