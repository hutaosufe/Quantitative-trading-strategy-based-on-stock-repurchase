package project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class's main method takes a valid given csv file as the command line argument. 
 * 
 * After initialization and validating that the file exists and is readable, it will proceed to 
 * create a {@link MeteoriteList} of all the {@link Meteorite} objects that have a valid name 
 * and id. 
 * 
 * After completing the process, the user could interact with the program by entering search 
 * queries to find specific meteorites based on location, year, and mass of the meteorites. 
 * 
 * @author Jonason Wu
 * @version 10/5/2020
 */
public class FallenStars {

	/**
	 * The main() method of the program. General explanation is provided at {@link FallenStars}
	 * @param args the array of Strings that are provided by the command line when started.
	 * the first argument passed in should be the csv file that has the dataset.
	 */
	public static void main(String[] args) {
		//verify that the command line argument exists 
		if (args.length == 0 ) {
			System.err.println("Usage Error: the program expects file name as an "
					+ "argument.\n");
			System.exit(1);
		}

		//verify that command line argument contains a name of an existing file 
		File starfall = new File(args[0]); 
		//verify that file exists
		if (!starfall.exists()){
			System.err.println("Error: the file " + starfall.getAbsolutePath()
					+ " does not exist.\n");
			System.exit(1);
		}
		//verify that file is readable
		if (!starfall.canRead()){
			System.err.println("Error: the file " + starfall.getAbsolutePath()
					+ " cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//open the file for reading 
		Scanner meteors = null; 

		try {
			meteors = new Scanner (starfall);
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file "+ starfall.getAbsolutePath()
					+ " cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//Create list to hold all valid meteorites (Valid meteorites have valid name and id)
		MeteoriteList meteor = new MeteoriteList();
		//Set the values important to each meteorite
		String name;
		int id;
		int mass;
		int year;
		double latitude;
		double longitude;
		while (meteors.hasNextLine()) {
			
			//Index of parsed: [0   ,  1,2,3,    4,5,    6,7,8,   9]
			//Data of parsed:  [name, id, , , mass, , year, , , loc]
			ArrayList<String> parsed = splitCSVLine(meteors.nextLine());
			//Create meteorite object that is planned to be added to the list of meteorites
			Meteorite toAdd;
			//Try getting the name and id of the meteorite object. If error occurs, it is not
			//	a valid meteorite to add to list
			try {
				name = parsed.get(0);
				id = Integer.parseInt(parsed.get(1));
				toAdd = new Meteorite(name, id);
			}
			catch (IllegalArgumentException ex) {
				continue;
			}
			
			//Test if there is a valid mass to add to meteorite
			try {
				mass = Integer.parseInt(parsed.get(4));
				toAdd.setMass(mass);
			}
			catch (IllegalArgumentException ex) {}
			
			//Test if there is a valid year to add to meteorite
			try {
				//Example of data for partYear: "01/01/1880 12:00:00 AM"
				String partYear = parsed.get(6);
				String[] separated = partYear.split(" ");
				separated = separated[0].split("/");
				year = Integer.parseInt(separated[2]);
				toAdd.setYear(year);
			}
			catch (IllegalArgumentException ex) {}
			catch (ArrayIndexOutOfBoundsException ex) {}
			
			//Test if there is a valid location to add to meteorite
			try {
				//Example of data for partLoc: "(50.775, 6.08333)"
				String partLoc = parsed.get(9).replace("(", "").replace(")", "");
				String[] separated = partLoc.split(",");
				latitude = Double.parseDouble(separated[0]);
				longitude = Double.parseDouble(separated[1]);
				toAdd.setLocation(new Location(latitude, longitude));
			}
			catch (IllegalArgumentException ex) {}
			catch (IndexOutOfBoundsException ex) {}
			//add the meteorite to the list
			meteor.add(toAdd);
		}
		
		
		
		
		
		//User Interface
		System.out.println(
			"	Search the database by using one of the following queries.\n" + 
			"	  To search for meteorite nearest to a given geo-location, enter\n" + 
			"	        location LATITUDE LONGITUDE\n" + 
			"	  To search for meteorites that fell in a given year, enter\n" + 
			"	        year YEAR\n" + 
			"	  To search for meteorites with weights MASS +/- 10 grams, enter\n" + 
			"	        mass MASS\n" + 
			"	  To finish the program, enter\n" + 
			"	        quit\n\n");
		//User inputs
		Scanner userInput = new Scanner(System.in);
		String user;
		do {
			//User can input 3 queries
			//location LATITUDE LONGITUDE
			//year YEAR
			//mass MASS
			System.out.println("\nEnter your search query.\n");
			user = userInput.nextLine();
			
			//Split the user input by spaces.
			String[] userSplit = user.trim().split("\\s+");
			//Test which query the user wants
			switch (userSplit[0]) {
			
			case "location":
				//Location should have a array of size 3
				if (userSplit.length != 3) {
					System.err.println("This is not a valid geolocation. Try again.\n");
					break;
				}
				
				//Validate the user input and print the closest meteorite.
				try {
					double userLat= Double.parseDouble(userSplit[1]);
					double userLon = Double.parseDouble(userSplit[2]);
					Meteorite close = meteor.getByLocation(new Location (userLat, userLon));
					System.out.println(close);
				}
				catch (IllegalArgumentException ex) {
					System.err.println("This is not a valid geolocation. Try again.\n");
				}
				break;
				
			case "year":
				//Year must have array of length 2
				if (userSplit.length != 2) {
					System.err.println("This is not a valid year. Try again.\n");
					break;
				}
				
				//Validate user input and print all meteorites of matching years
				try {
					int userYear = Integer.parseInt(userSplit[1]);
					MeteoriteLinkedList match = meteor.getByYear(userYear);
					if (match == null) {
						System.out.println("No matches found. Try again.\n");
						break;
					}
					else {
						System.out.println(match);
					}
				}
				catch (IllegalArgumentException ex) {
					System.err.println("This is not a valid year. Try again.\n");
				}
				break;
				
			case "mass":
				//Mass must have array of length 2
				if (userSplit.length != 2) {
					System.err.println("This is not a valid mass. Try again.\n");
					break;
				}
				
				//Validate user input and print all meteorites of matching masses with error of 10.
				try {
					int userMass = Integer.parseInt(userSplit[1]);
					MeteoriteLinkedList match = meteor.getByMass(userMass, 10);
					if (match == null) {
						System.out.println("No matches found. Try again.\n");
						break;
					}
					else {
						System.out.println(match);
					}
				}
				catch (IllegalArgumentException ex) {
					System.err.println("This is not a valid mass. Try again.\n");
				}
				break;
				
			case "quit":
				break;
				
			default:
				//Query is not valid
				System.err.println("This is not a valid query. Try again.\n");
			}
		} while (!user.equals("quit"));
		
		userInput.close();
		
	}
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author Joanna Klukowska
	 * @param textLine  a line of text from a CSV file to be parsed
	 * @return an ArrayList object containing all individual entries found on that line;
	 *  any missing entries are indicated as empty strings; null is returned if the textline
	 *  passed to this function is null itself.
	 */
	public static ArrayList<String> splitCSVLine(String textLine){

		if (textLine == null ) return null;

		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;

		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);

			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {

				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
				}
				else {
					insideQuotes = true;
					insideEntry = true;
				}
			}
			else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
					// add it to the current entry
					nextWord.append( nextChar );
				}
				else { // skip all spaces between entries
					continue;
				}
			}
			else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar);
				}
				else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			}
			else {
				// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;
			}

		}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}

}
