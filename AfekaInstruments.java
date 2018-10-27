import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class AfekaInstruments {

	public static Scanner consoleScanner = new Scanner(System.in);

	public static void main(String[] args) {
		ArrayList<MusicalInstrument> allInstruments = new ArrayList<MusicalInstrument>();
		File file = getInstrumentsFileFromUser();

		loadInstrumentsFromFile(file, allInstruments);

		if (allInstruments.size() == 0) {
			System.out.println("There are no instruments in the store currently");
			return;
		}

		printInstruments(allInstruments);

		int different = getNumOfDifferentElements(allInstruments);

		System.out.println("\n\nDifferent Instruments: " + different);

		MusicalInstrument mostExpensive = getMostExpensieveInstrument(allInstruments);

		System.out.println("\n\nMost Expensive Instrument:\n" + mostExpensive);

		startInventoryMenu(allInstruments);

	}

	public static File getInstrumentsFileFromUser() {
		boolean stopLoop = true;
		File file;

		do {
			System.out.println("Please enter instruments file name / path:");
			String filepath = consoleScanner.nextLine();
			file = new File(filepath);
			stopLoop = file.exists() && file.canRead();

			if (!stopLoop)
				System.out.println("\nFile Error! Please try again\n\n");
		} while (!stopLoop);

		return file;
	}

	// load instruments from file
	public static void loadInstrumentsFromFile(File file, ArrayList<MusicalInstrument> allInstruments) {
		Scanner fileScanner = null;

		try {

			fileScanner = new Scanner(file);

			addAllInstruments(allInstruments, loadGuitars(fileScanner));
			addAllInstruments(allInstruments, loadBassGuitars(fileScanner));
			addAllInstruments(allInstruments, loadFlutes(fileScanner));
			addAllInstruments(allInstruments, loadSaxophones(fileScanner));

		} catch (InputMismatchException | IllegalArgumentException ex) {
			System.err.println("\n" + ex.getMessage());
			System.exit(1);
		} catch (FileNotFoundException ex) {
			System.err.println("\nFile Error! File was not found");
			System.exit(2);
		} finally {
			fileScanner.close();
		}
		System.out.println("\nInstruments loaded from file successfully!\n");

	}

	// load guitars
	public static ArrayList<Guitar> loadGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Guitar> guitars = new ArrayList<Guitar>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			guitars.add(new Guitar(scanner));

		return guitars;
	}

	// loads basses from scanner
	public static ArrayList<Bass> loadBassGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Bass> bassGuitars = new ArrayList<Bass>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			bassGuitars.add(new Bass(scanner));

		return bassGuitars;
	}

	// loads flutes from scanner
	public static ArrayList<Flute> loadFlutes(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Flute> flutes = new ArrayList<Flute>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			flutes.add(new Flute(scanner));

		return flutes;
	}

	// loads saxophones from scanner
	public static ArrayList<Saxophone> loadSaxophones(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Saxophone> saxophones = new ArrayList<Saxophone>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			saxophones.add(new Saxophone(scanner));

		return saxophones;
	}

	// add all instruments
	public static void addAllInstruments(ArrayList<MusicalInstrument> instruments,
			ArrayList<? extends MusicalInstrument> moreInstruments) {
		for (int i = 0; i < moreInstruments.size(); i++) {
			instruments.add(moreInstruments.get(i));
		}
	}

	// print instruments
	public static void printInstruments(ArrayList<? extends MusicalInstrument> instruments) {
		for (int i = 0; i < instruments.size(); i++)
			System.out.println(instruments.get(i));
	}

	// get num of different elements
	public static int getNumOfDifferentElements(ArrayList<? extends MusicalInstrument> instruments) {
		int numOfDifferentInstruments;
		ArrayList<MusicalInstrument> differentInstruments = new ArrayList<MusicalInstrument>();
		System.out.println();

		for (int i = 0; i < instruments.size(); i++) {
			if (!differentInstruments.contains((instruments.get(i)))) {
				differentInstruments.add(instruments.get(i));
			}
		}
		if (differentInstruments.size() == 1)
			numOfDifferentInstruments = 0;
		else
			numOfDifferentInstruments = differentInstruments.size();
		return numOfDifferentInstruments;
	}

	// get most expensive instrument
	public static MusicalInstrument getMostExpensieveInstrument(ArrayList<? extends MusicalInstrument> array1) {
		double price = 0;
		int index = 0;
		for (int i = 0; i < array1.size(); i++) {
			if (((MusicalInstrument) array1.get(i)).getPrice().doubleValue() > price) {
				price = ((MusicalInstrument) array1.get(i)).getPrice().doubleValue();
				index = i;
			}
		}
		return (MusicalInstrument) array1.get(index);
	}

	// The menu of the inventory
	public static void startInventoryMenu(ArrayList<MusicalInstrument> afekaStore) {
		AfekaInventory<MusicalInstrument> inventory = new AfekaInventory<MusicalInstrument>();
		MusicalInstrument instrument;
		Number price = 0;
		boolean chack;
		int index = -1;
		String brand = "";
		boolean isChoose = false;
		while (!isChoose) {
			printHeader("AFEKA MUSICAL INSTRUMENT INVENTORY MENU");
			printmenu();
			String choose = consoleScanner.next();
			switch (choose) {
			case "1":
				inventory.addAllStringInstruments(afekaStore, inventory.getArray());
				System.out.println("\nAll String Instruments Added Successfully!\n");
				break;

			case "2":
				inventory.addAllWindInstruments(afekaStore, inventory.getArray());
				System.out.println("\nAll String Wind Added Successfully!\n");
				break;

			case "3":
				if (inventory.getArray().isEmpty()) {
					System.out.println("arry is empty");
					break;
				}
				inventory.SortByBrandAndPrice(inventory.getArray());
				System.out.println("\nInstruments Sorted Successfully!\n");
				break;
			case "4":
				System.out.println("SEARCH INSTRUMENTS:\n Brand:");
				brand = consoleScanner.next();
				System.out.println("\n Price:");
				index = checkPriceBrand(price, inventory, brand);
				System.out.println("Result: ");
				if (index >= 0) {
					System.out.println("\t" + inventory.getArray().get(index));
				} else {
					System.out.println("Instrument Not Found!");
				}
				break;
			case "5":
				System.out.println("\nDELETE INSTRUMENT:\n Brand:");
				brand = consoleScanner.next();
				System.out.println("\nPrice:");
				index = checkPriceBrand(price, inventory, brand);
				System.out.println("Result: ");
				if (index >= 0) {
					System.out.println("\t" + inventory.getArray().get(index));
					System.out.println("Are You Sure?(Y/N)");
					do {
						choose = consoleScanner.next();
						if (choose.equalsIgnoreCase("y")) {
							instrument = inventory.getArray().get(index);
							inventory.removeInstrument(inventory.getArray(), instrument);
							System.out.println("Instrument Deleted Successfully!");
						} else if (choose.equalsIgnoreCase("n")) {
							System.out.println("okay, Instrument was not deleted");
						} else {
							System.out.println("You need to choose \"Y\" or \"N\" only");
						}
					} while (!choose.equalsIgnoreCase("y") && !choose.equalsIgnoreCase("n"));
				} else {
					System.out.println("Instrument Not Found!");
				}
				break;
			case "6":
				System.out.println("\nDELETE ALL INSTRUMENTS:\nAre You Sure?(Y/N)");
				do {
					choose = consoleScanner.next();
					if (choose.equalsIgnoreCase("y")) {
						chack = inventory.removeAll(inventory.getArray());
						if (!chack) {
							System.out.println("its empty");
							break;
						}

						System.out.println("All Instruments Deleted Successfully!");
					} else if (choose.equalsIgnoreCase("n"))
						System.out.println("okay, Instruments were not deleted");
					else {
						System.out.println("You need to choose \"Y\" or \"N\" only");
					}
				} while (!choose.equalsIgnoreCase("y") && !choose.equalsIgnoreCase("n"));
				break;
			case "7":
				printHeader("AFEKA MUSICAL INSTRUMENT INVENTORY");
				if (inventory.getArray().isEmpty())
					System.out.println("There Is No Instruments To Show\n");
				System.out.println(inventory.toString());
				break;
			default:
				System.out.println("Finished!");
				isChoose = true;
			}
		}

	}

	// check price brand
	public static int checkPriceBrand(Number price, AfekaInventory<MusicalInstrument> inventory, String brand) {
		boolean hasPrice = true;
		try {
			price = consoleScanner.nextInt();
		} catch (InputMismatchException ex) {
			try {
				price = consoleScanner.nextDouble();
			} catch (InputMismatchException exe) {
				consoleScanner.nextLine();
				hasPrice = false;
			}
		}
		if (hasPrice)
			return inventory.binnarySearchByBrandAndPrice(inventory.getArray(), brand, price);
		return -1;
	}

	// print header
	public static void printHeader(String header) {
		System.out.print("\n-------------------------------------------------------------------------\n" + header
				+ "\n-------------------------------------------------------------------------\n");
	}

	// print the menu
	public static void printmenu() {
		System.out.print("1. Copy All String Instruments To Inventory\n" + "2. Copy All Wind Instruments To Inventory\n"
				+ "3. Sort Instruments By Brand And Price\n" + "4. Search Instrument By Brand And Price\n"
				+ "5. Delete Instrument\n" + "6. Delete all Instruments\n" + "7. Print Inventory Instruments\n"
				+ "Choose your option or any other key to EXIT\n\nYour Option: ");
	}
}
