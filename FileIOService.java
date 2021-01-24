package exercisetwo;
import java.util.*;
import java.io.*;
public class FileIOService {

    public List<String> readFile(String fileName) throws IOException{
		List<String> fileContent = new ArrayList<String>();
        Scanner fileInput = new Scanner(new File(fileName));
		
		fileContent.clear();
        while (fileInput.hasNextLine()) {
			fileContent.add(fileInput.nextLine());
        }
        fileInput.close();
        
        for (int i = 0; i < fileContent.size(); i++) {
			if (fileContent.get(i).trim().isEmpty()) {
				fileContent.remove(i);
			}
		}
		return fileContent;
    }
	
	public void writeFile(String newFileContent) throws IOException{
		FileWriter writer  = new FileWriter("newFile.txt");
		
		writer.write(newFileContent);
		writer.close();
		readFile("newFile.txt");	
	}
}