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

    private static void handleInput() {
        String input;
        System.out.println("What would you like to do. (Type search to search the database. or update to update the database.)");
        input = fixInput(in.nextLine());

        if (input.equals("search")) searchDB();
        else updateDB();

    }

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
                break;
        }
    }

    private static void searchBuildingName() {
        System.out.println("Please enter a building name to search for or type all to return a list of all buildings in database.");
        String buildingInput = fixInput(in.nextLine());
        if (buildingInput.equals("all")) db.selectBuilding("", true, false);
        else db.selectBuilding(buildingInput, false, false);

    }

    private static void searchBuildingId() {
        System.out.println("Please enter a building id to search for or type all to return a list of all buildings in database.");
        String buildingInput = fixInput(in.nextLine());
        if (buildingInput.equals("all")) db.selectBuilding("", true, false);
        else db.selectBuilding(buildingInput, false, true);
    }

    private static void searchBuildingType() {
        System.out.println("Please enter a building type to search for or type all to return a list of all building types in database.");
        String buildingInput = fixInput(in.nextLine());
        if (buildingInput.equals("all")) db.selectBuildingType("", true);
        else db.selectBuildingType(buildingInput, false);
    }

    private static void searchCampus() {
        System.out.println("Please enter a campus to search for or type all to return a list of all campuses in database.");
        String campusInput = fixInput(in.nextLine());
        if (campusInput.equals("all")) db.selectCampus("", true);
        else db.selectCampus(campusInput, false);
    }

    private static void searchLocationNumber() {
        System.out.println("Please enter a Location id to search for or type all to return a list of all Location ids in database.");
        String locationInput = fixInput(in.nextLine());
        if (locationInput.equals("all")) db.selectLocation(-1, true);
        else db.selectLocation(Integer.parseInt(locationInput), false);
    }

    private static void searchPassColor() {
        System.out.println("Please enter a pass type to search for or type all to return a list of all pass types in database.");
        String passInput = fixInput(in.nextLine());
        if (passInput.equals("all")) db.selectParking("", true);
        else db.selectParking(passInput, false);
    }

    private static void searchBuildingAvgCost() {
        System.out.println("The average cost of all of the buildings in the database is: ");
        db.avg();
    }

    private static void searchBuildingFullAddress() {

    }

    private static void updateDB(){
        System.out.println("Please enter the table you would like to update. (Building, Building_type, Campus, Field, Location, or Parking)");
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
            case "building_type":
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
