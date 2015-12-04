package eldemizt;

import java.util.Scanner;

/**
 * Created by Zach Eldemire on 11/24/15.
 * Project
 * CSE 385
 * This class handles all of interactions the program has with the user.
 */
public class Question {

    public static Scanner in = new Scanner(System.in);
    private static DB db = new DB();
    public static String password, username;

    public static void main(String[] args) {
        System.out.println("Welcome to the Miami University Building Database!");
        handleInput();
    }

    /**
     * This is the start point for the questions. It handles whether the user wants to edit the database or search it.
     * The user can input exit to exit the program.
     */
    private static void handleInput() {
        String input;
        System.out.println("What would you like to do. (Type search to search the database, update to edit the database," +
                " or exit to leave the program.)");
        input = fixInput(in.nextLine());

        switch (input) {
            case "search":
                searchDB();
                break;
            case "edit":
                login();
                break;
            case "exit":
                return;
            default:
                handleInput();
                break;
        }
    }

    private static void login() {
        System.out.print("Username: ");
        username = in.nextLine();
        System.out.print("Password: ");
        password = in.nextLine();
        Login login = new Login(password);
        if (!login.test()) {
            System.out.println("Can't connect to database, another user is editing.");
            handleInput();
        } else editDB();
    }

    /**
     * This method handles what the user wants to search for.
     */
    private static void searchDB() {
        System.out.println("Please enter the type of search you would like to do: " +
                "(Building name, building id, building type, campus, location id, parking pass type, building average cost, " +
                "full address, field, join, or quit to return to previous question)");
        String searchInput = fixInput(in.nextLine());
        switch (searchInput) {
            case "buildingname":
                searchBuildingName();
                break;
            case "buildingid":
                searchBuildingId();
                break;
            case "buildingtype":
                searchBuildingType();
                break;
            case "campus":
                searchCampus();
                break;
            case "locationid":
                searchLocationNumber();
                break;
            case "parkingpasstype":
                searchPassColor();
                break;
            case "buildingaveragecost":
                searchBuildingAvgCost();
                break;
            case "fulladdress":
                searchBuildingFullAddress();
                break;
            case "field":
                searchField();
                break;
            case "join":
                db.join();
                handleInput();
                break;
            case "quit":
                handleInput();
                break;
            default:
                System.out.println("You entered something that was unsupported. Please try again.");
                searchDB();
                break;
        }
    }

    /**
     * Searches the database for the given field id, or returns the whole table if the user inputs all.
     */
    private static void searchField() {
        System.out.println("Please enter a field location id to search for or type all to return a list of all fields in database.");
        String fieldInput = fixInput(in.nextLine());

        if (fieldInput.equals("all")) db.selectField(-1, true);
        else db.selectField(Integer.parseInt(fieldInput), false);

        System.out.println("If you want to search for another field type yes, if you want to go to the previous menu type no");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchField();
        else searchDB();
    }

    /**
     * Searches the database for the given building name, or returns the whole building table if the user inputs all.
     */
    private static void searchBuildingName() {
        System.out.println("Please enter a building name to search for or type all to return a list of all buildings in database.");
        String buildingInput = fixInput(in.nextLine());
        if (buildingInput.equalsIgnoreCase("all")) db.selectBuilding("", true, false);
        else db.selectBuilding(buildingInput, false, false);

        System.out.println("If you want to search for another building type yes, if you want to go to the previous menu type no");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchBuildingName();
        else searchDB();
    }

    /**
     * Searches the database for the given building id, or returns the whole building table if the user inputs all.
     */
    private static void searchBuildingId() {
        System.out.println("Please enter a building id to search for or type all to return a list of all buildings in database.");
        String buildingInput = fixInput(in.nextLine());
        if (buildingInput.equals("all")) db.selectBuilding("", true, false);
        else db.selectBuilding(buildingInput, false, true);

        System.out.println("If you want to search for another building type yes, if you want to go to the previous menu type no");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchBuildingId();
        else searchDB();
    }

    /**
     * Searches the database for the given building type, or returns the whole building type table if the user inputs all.
     */
    private static void searchBuildingType() {
        System.out.println("Please enter a building type to search for or type all to return a list of all building types in database.");
        String buildingInput = fixInput(in.nextLine());
        if (buildingInput.equals("all")) db.selectBuildingType("", true);
        else db.selectBuildingType(buildingInput, false);

        System.out.println("If you  want to search for a different building type type yes, if you want to return to the previous menu type no");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchBuildingType();
        else searchDB();
    }

    /**
     * Searches the database for the given campus name, or returns the whole campus table if the user inputs all.
     */
    private static void searchCampus() {
        System.out.println("Please enter a campus to search for or type all to return a list of all campuses in database.");
        String campusInput = fixInput(in.nextLine());
        if (campusInput.equals("all")) db.selectCampus("", true);
        else db.selectCampus(campusInput, false);

        System.out.println("If you want to search for another campus type yes, type no to return to previous menu");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchCampus();
        else searchDB();
    }

    /**
     * Searches the database for the given location id, or returns the whole location table if the user inputs all.
     */
    private static void searchLocationNumber() {
        System.out.println("Please enter a Location id to search for or type all to return a list of all Location ids in database.");
        String locationInput = fixInput(in.nextLine());
        if (locationInput.equals("all")) db.selectLocation("", true, false);
        else db.selectLocation(locationInput, false, false);

        System.out.println("If you want to search for another location by id type yes, type no to return to previous menu.");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchLocationNumber();
        else searchDB();
    }

    /**
     * Searches the database for the given pass color, or returns the whole parking table if the user inputs all.
     */
    private static void searchPassColor() {
        System.out.println("Please enter a pass type to search for or type all to return a list of all pass types in database.");
        String passInput = fixInput(in.nextLine());
        if (passInput.equals("all")) db.selectParking("", true);
        else db.selectParking(passInput, false);

        System.out.println("If you would like to continue searching for parking location by parking pass type yes, if not" +
                " type no");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchPassColor();
        else searchDB();
    }

    /**
     * Returns the average cost of the buildings.
     */
    private static void searchBuildingAvgCost() {
        System.out.println("The average cost of all of the buildings in the database is: ");
        db.avg();
        System.out.println("To make another search type yes, or no to return to main menu.");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchDB();
        else handleInput();
    }

    /**
     * Searches for the building corresponding to the given address. If the address is not in the database it will ask the question again.
     */
    private static void searchBuildingFullAddress() {
        System.out.println("Please enter the address of the building you want to search for.");
        String locationInput = in.nextLine();

        int locID = db.selectLocIDByAddress(locationInput);

        if (locID == -1) {
            System.out.println("Address is not in database.");
            searchBuildingFullAddress();
        } else db.selectBuilding(Integer.toString(locID), false, true);

        System.out.println("If you would like to search for another building type yes, if not type no");
        String cont = in.nextLine();
        if (cont.equalsIgnoreCase("yes")) searchBuildingFullAddress();
        else searchDB();
    }

    /**
     * This function handles the admin input from the user.
     */
    private static void editDB(){
        System.out.println("Please enter the statement that you want to execute. Type help to see the usage. Or exit to " +
                "return the main menu.");
        String updateInput = in.nextLine();
        if (updateInput.equalsIgnoreCase("help")) help();
        else if (updateInput.equalsIgnoreCase("select everything")) selectEverything();
        else if (updateInput.equalsIgnoreCase("show table names")) showTableNames();
        else if (updateInput.equalsIgnoreCase("select table")) selectTable();
        else if (updateInput.equalsIgnoreCase("exit")) {
            Login login = new Login(password);
            login.exit();
            handleInput();
        }
        else updateDB(updateInput);
    }

    /**
     * Handles the selection for which table to search
     */
    private static void selectTable() {
        System.out.print("Which table: ");
        String tableName = in.nextLine();
        switch (tableName.toLowerCase()) {
            case "building":
                db.selectBuilding("", true, false);
                break;
            case "location":
                db.selectLocation("", true, false);
                break;
            case "field":
                db.selectField(-1,true);
                break;
            case "campus":
                db.selectCampus("", true);
                break;
            case "parking":
                db.selectParking("", true);
                break;
            case "building type":
                db.selectBuildingType("", true);
                break;
            default:
                selectTable();
                break;
        }
        editDB();
    }

    /**
     * This function will show all of the table names currently in the database.
     */
    private static void showTableNames() {
        DB db = new DB();
        db.showTableNames();
        editDB();
    }

    /**
     * This function will return every table in the database.
     */
    private static void selectEverything() {
        DB db = new DB();
        System.out.println("Building Table");
        db.selectBuilding("", true, false);
        System.out.println("Campus Table");
        db.selectCampus("", true);
        System.out.println("Building_type Table");
        db.selectBuildingType("", true);
        System.out.println("Location Table");
        db.selectLocation("", true, false);
        System.out.println("Parking Table");
        db.selectParking("", true);
        System.out.println("Field");
        db.selectField(-1, true);
        editDB();
    }

    /**
     * This function will take the sql statements and run them.
     * @param updateInput the sql statement from the user.
     */
    private static void updateDB(String updateInput) {
        DB db = new DB();

        if (db.update(updateInput)) {
            System.out.println("Edit has been applied");
        }
        editDB();
    }


    /**
     * Returns the usage of the program.
     */
    private static void help() {
        System.out.println("Usage: Type Select everything to show each table.");
        System.out.println("Type show table names to get the table names.");
        System.out.println("Or type select table to bring up and option to select tables that are in the database.");
        System.out.println("Or just type the sql statements in.");
        editDB();
    }

    /**
     * Fixes the input string.
      * @param input The string from the user.
     * @return the string without spaces and in lowercase
     */
    private static String fixInput(String input) {
        return input.replaceAll("\\s","").toLowerCase();
    }
}
