package exercisetwo;
import java.util.*;
import java.io.*;
public class MenuService {
	
	FileIOService fileIO = new FileIOService();
	
	private static Scanner input = new Scanner(System.in);
	private final int MAX_NUMBER = 100;
	
	public void displayOptions() throws IOException{
		int option = 0;
		boolean isNotValid = true;

		System.out.print("\n");
		System.out.println("----------OPTIONS----------");
		System.out.println("\n");
		System.out.println("   (1)     Search");
		System.out.println("   (2)     Edit");
		System.out.println("   (3)     Print");
		System.out.println("   (4)     Add Row");
		System.out.println("   (5)     Sort Row");
		System.out.println("   (6)     Reset");
		System.out.println("   (7)     Exit");
		System.out.println("\n");
		System.out.print("Select from options 1-7: ");
		option = checkIntegerInput(7);
		
		System.out.println("\n");

		switch (option) {

		case 1:
			search();
			displayOptions();
			break;
		case 2:
			try {
			edit();
			}catch(Exception e) {}
			
			displayOptions();
			break;
		case 3:
			print();
			displayOptions();
			break;
		case 4:
			addRow();
			displayOptions();
			break;
		case 5:
			sortRow();
			displayOptions();
			break;
		case 6:
			reset();
			displayOptions();
			break;
		case 7:
			exit();
			break;
		}
	}
	private static String stringFileContent = "";
	
	private List<String> createList() throws IOException{
		List<String> currentFileContent = new ArrayList<String>();
		
		currentFileContent = fileIO.readFile("newFile.txt");
		
		return currentFileContent;
	}
	
	private Map<String, String> createMap() throws IOException{
		Map<String, String> currentMapContent = new HashMap<String, String>();
		List<String> currentFileContent = new ArrayList<String>();
		
		currentFileContent = fileIO.readFile("newFile.txt");
		
		for (int i = 0; i < currentFileContent.size(); i++) {
			String[] listLine = currentFileContent.get(i).split("\t");
			
			for (int j = 0; j < listLine.length; j++) {
				String[] keyValue = listLine[j].split(":", 2);
				
				if (keyValue.length >= 2) {
					currentMapContent.put(keyValue[0], keyValue[1]);
				}
			}
		}
		
		return currentMapContent;
	}
	
	
	public void search() throws IOException{
		Map<String, String> currentMapContent = new HashMap<String, String>();
		List<String> key = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		
		String strToSearch = "";
		int count = 0;
		boolean isNotValid = true;
		
		currentMapContent = createMap();
		
		System.out.println("Enter character/s to search.");
		System.out.print("1-3 character/s only: ");
		strToSearch = input.nextLine();
		
		if(strToSearch.length() > 3 || strToSearch.length() < 1) {
			while(isNotValid) {
				strToSearch = input.nextLine();
				
				if(strToSearch.length() <= 3 && strToSearch.length() >= 1) {
					isNotValid = false;
				}else {
					System.out.print("Invalid input! Please try another: ");
				}
			}
		}
		
		System.out.println("KEY	VALUE	OCCURENCE");
		
		for(HashMap.Entry<String, String> entry : currentMapContent.entrySet()){
            key.add(entry.getKey());
			value.add(entry.getValue());
        }
		
		for (int i = 0; i < key.size(); i++) {
			String strValue = value.get(i).toString();
			String strKey = key.get(i).toString();
			if (strToSearch.length() == 1) {
				for (int j = 0; j < strValue.length(); j++) {
					if(strToSearch.charAt(0) == strValue.charAt(j)) {
						count++;
					}
				}
			}else if(strToSearch.length() == 2) {
				char charStrToSearch1 = strToSearch.charAt(0);
				char charStrToSearch2 = strToSearch.charAt(1);
				
				try {
					for (int j = 0; j < 3; j++) {
						if(strValue.charAt(j) == charStrToSearch1 && strValue.charAt(j+1) == charStrToSearch2) {
							count++;
						}
					}
				}catch (Exception e){};
				
			}else if(strToSearch.length() == 3) {
				if(strToSearch.equals(strValue)) {
					count++;
				}	
			}
			
			if(count > 0) {
				System.out.println(strKey + "\t" + strValue + "\t" + count);
			}
			
            count = 0;
		}
	}
	
	public void edit() throws IOException{
		List<String> currentFileContent = new ArrayList<String>();
		
		String lineToEdit = "";
		String newValue = "";
		int selectedRow = 0;
		int selectedColumn = 0;
		boolean isNotValid = true;
		
		currentFileContent = createList();
		
		stringFileContent = "";
		
		print();
		
		System.out.println();
		System.out.println("Please select the row and column to edit.");
		System.out.print("Which row?: ");
		selectedRow = checkIntegerInput(currentFileContent.size()) - 1;
		
		lineToEdit = currentFileContent.get(selectedRow);
		String[] lineKeyValue = lineToEdit.split("\t");
		
		System.out.print("Which column?: ");
		selectedColumn = checkIntegerInput(lineKeyValue.length) - 1;
		
        System.out.print("Enter new Value up to 3 character/s only): ");
		newValue = input.nextLine();
		
		if(newValue.length() > 3 || newValue.length() < 1) {
			while(isNotValid) {
				newValue = input.nextLine();
				
				if(newValue.length() <= 3 && newValue.length() >= 1) {
					isNotValid = false;
				}else {
					System.out.print("Invalid input! Please try another: ");
				}
			}
		}
		
		String[] keyValue = lineKeyValue[selectedColumn].split(":", 2);
		keyValue[1] = newValue;
		
		lineKeyValue[selectedColumn] = keyValue[0] + ":" + keyValue[1];
		lineToEdit = "";
		for (int i = 0; i < lineKeyValue.length; i++) {
			lineToEdit += lineKeyValue[i] + "\t";
		}
		
		currentFileContent.set(selectedRow, lineToEdit);
		
		for (int i = 0; i < currentFileContent.size(); i++) {
			stringFileContent = stringFileContent.concat(currentFileContent.get(i) + "\n");
		}
		
		try {
			fileIO.writeFile(stringFileContent);
			currentFileContent = fileIO.readFile("newFile.txt");
		}catch (IOException e){}
	}
	
	public void print() throws IOException{
		List<String> currentFileContent = new ArrayList<String>();
		
		currentFileContent = createList();
		
		for(int i = 0; i < currentFileContent.size(); i++) {
            System.out.println(currentFileContent.get(i));
        }
	}
	
	public void addRow() throws IOException{
		List<String> currentFileContent = new ArrayList<String>();
		
		String lineToAdd = "";
		String asciiValue = "";
		String asciiKey = "";
		int numElements = 0;
		
		currentFileContent = createList();
		
		System.out.print("Enter number of Element/s: ");
		numElements = checkIntegerInput(MAX_NUMBER);
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < numElements; j++) {
				asciiKey = createAsciiKey();
				asciiValue = createAsciiValue();
				
				lineToAdd += asciiKey + ":" + asciiValue + "\t";
			}
			stringFileContent = stringFileContent.concat(lineToAdd + "\n");
			lineToAdd = "";
		}
		
		try {
			fileIO.writeFile(stringFileContent);
			currentFileContent = fileIO.readFile("newFile.txt");
		}catch (IOException e){}
		
		
	}
	
	public void sortRow() throws IOException{
		List<String> currentFileContent = new ArrayList<String>();
		
		currentFileContent = createList();
		
		print();
		
		System.out.print("Enter row to sort: ");
		int rowToSort = checkIntegerInput(currentFileContent.size());
		
		String[] rowElements = currentFileContent.get(rowToSort-1).split("\t");
		String sortedRow = "";
		
		Arrays.sort(rowElements);
		
		for (int i = 0; i < rowElements.length; i++) {
			sortedRow += rowElements[i] + "\t";
		}
		
		stringFileContent = "";
		currentFileContent.set(rowToSort-1, sortedRow.trim());
		
		for (int i = 0; i < currentFileContent.size(); i++) {
			stringFileContent = stringFileContent.concat(currentFileContent.get(i) + "\n");
		}
		
		try {
			fileIO.writeFile(stringFileContent);
			currentFileContent = fileIO.readFile("newFile.txt");
		}catch (IOException e){}
		
		
	}

	public void reset() throws IOException{
		List<String> currentFileContent = new ArrayList<String>();
		
		currentFileContent = createList();
		
		String line = "";
		String asciiValue = "";
		String asciiKey = "";
		boolean notUnique = true;
		
		currentFileContent.clear();
		
		stringFileContent = "";

		System.out.print("Enter number of Rows: ");
		int row = checkIntegerInput(MAX_NUMBER);
		System.out.print("Enter number of Columns: ");
		int column = checkIntegerInput(MAX_NUMBER);
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				asciiKey = createAsciiKey();
				asciiValue = createAsciiValue();
				
				line += asciiKey + ":" + asciiValue + "\t";
			}
			stringFileContent = stringFileContent.concat(line + "\n");
			line = "";
		}
		
		try {
			fileIO.writeFile(stringFileContent);
			currentFileContent = fileIO.readFile("newFile.txt");
		}catch (IOException e){}
		
		
	}
	
	private String createAsciiValue() {
		String asciiValue = "";
		char tempChar;
		int x = 0;

		while (x < 3) {
			int randomAsciiNumber = (int) ((Math.random() * 96) + 32);
			tempChar = (char)randomAsciiNumber;
			asciiValue += tempChar;
			x++;
		}
		return asciiValue;
	}
	
	private String createAsciiKey() {
		List<String> uniqueKeys = new ArrayList<String>();
		
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String asciiKey = "";
		char tempChar;
		int x = 0;
		
		while (x < 3) {
			int randomAlphaNumeric = (int) (Math.random() * alphaNumeric.length()-1);
			tempChar = alphaNumeric.charAt(randomAlphaNumeric);
			asciiKey += tempChar;
			x++;
		}
		
		if (uniqueKeys.isEmpty()) {
			uniqueKeys.add(asciiKey.toUpperCase());
			return asciiKey.toUpperCase();
		}else {
			if ( uniqueKeys.contains(asciiKey.toUpperCase())) {
				createAsciiValue();
			}
		}
		
		return asciiKey.toUpperCase();
	}
	
	public void exit() {
		System.out.println("The System has Exited! Thank you");
		System.exit(1);
	}
	
	int checkIntegerInput(int limit) {
		int numInput = 0;
		boolean isNotValid = true;
		
		while (isNotValid) {
			try{
				numInput = input.nextInt();
				
				if(limit == 5 || limit == 10) {
					if(numInput <= limit && numInput >= 1)
						break;
					else
						System.out.print("Input out of range! Please enter 1-" + limit + " only: ");
				}else {
					if((numInput <= limit && numInput >= 1) || (numInput <= limit && numInput >= 1))
						break;
					else
						System.out.print("Input out of range! Please enter 1-" + limit + " only: ");
				}
            }
			catch(InputMismatchException e){
				input.next();
				System.out.print("invalid input! Please try another: ");
			}
		}
		
		return numInput;
	}
}