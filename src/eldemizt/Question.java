package eldemizt;

import java.util.Scanner;

/**
 * Created by Zach Eldemire on 11/24/15.
 * Project
 * CSE 383
 */
public class Question {

    public static Scanner in = new Scanner(System.in);
    private static DB db = new DB();

    public static void main(String[] args) {
        System.out.println("Welcome to the Miami University Building Database!");
        handleInput();
    }

    /**
     * This is the start point for the questions. It handles whether the user wants to update the database or search it.
     * The user can input exit to exit the program.
     */
    private static void handleInput() {
        String input;
        System.out.println("What would you like to do. (Type search to search the database, update to update the database, or exit to leave the program.)");
        input = fixInput(in.nextLine());

        switch (input) {
            case "search":
                searchDB();
                break;
            case "update":
                updateDB();
                break;
            case "exit":
                return;
            default:
                handleInput();
                break;
        }
    }

    /**
     * This method handles what the user wants to search for.
     */
    private static void searchDB() {
        System.out.println("Please enter the type of search you would like to do: " +
                "(Building name, building id, building type, campus, location id, parking pass type, building average cost, " +
                "full address, or quit to return to previous question)");
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
     * Searches the database for the given building name, or returns the whole building table if the user inputs all.
     */
    private static void searchBuildingName() {
        System.out.println("Please enter a building name to search for or type all to return a list of all buildings in database.");
        String buildingInput = fixInput(in.nextLine());
        if (buildingInput.equals("all")) db.selectBuilding("", true, false);
        else db.selectBuilding(buildingInput, false, false);

        System.out.println("If you want to search for another building type yes, if you want to go to the previous menu type no");
        String cont = in.nextLine();
        if (cont.equals("yes")) searchBuildingName();
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
        if (cont.equals("yes")) searchBuildingId();
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
        if (cont.equals("yes")) searchBuildingType();
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
        if (cont.equals("yes")) searchCampus();
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
        if (cont.equals("yes")) searchLocationNumber();
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
        if (cont.equals("yes")) searchPassColor();
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
        if (cont.equals("yes")) searchDB();
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
        if (cont.equals("yes")) searchBuildingFullAddress();
        else searchDB();
    }

    private static void updateDB(){
        System.out.println("Please enter the table you would like to update. (Building, Building type, Campus, Field, Location, or Parking)");
        String updateInput = fixInput(in.nextLine());
        switch (updateInput) {
            case "parking":
                updateParking();
                break;
            case "location":
                updateLocation();
                break;
            case "campus":
                updateCampus();
                break;
            case "building":
                updateBuilding();
                break;
            case "buildingtype":
                updateBuildingType();
                break;
            case "field":
                updateField();
                break;
            default:
                System.out.println("You entered something that was not supported, please try again.");
                break;
        }
    }

    private static void updateField() {

    }

    private static void updateBuildingType() {

    }

    private static void updateBuilding() {

    }

    private static void updateCampus() {

    }

    private static void updateLocation() {

    }

    private static void updateParking() {

    }

    private static String fixInput(String input) {
        return input.replaceAll("\\s","").toLowerCase(); }




}
