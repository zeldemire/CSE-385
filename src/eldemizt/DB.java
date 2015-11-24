package eldemizt;

import java.io.IOException;
import java.sql.*;

/**
 * Created by Zach Eldemire on 11/17/15.
 * Project
 * CSE 385
 */
public class DB {
    String user = "root";
    String pwd = "Theblood5";
    String dbURL = "jdbc:mysql://localhost:3306/Project";
    Connection conn = null;

    public void connect() throws IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL,user,pwd);

        } catch (Exception err) {
            throw new IOException(err);
        }
    }

    protected void avg() {
        try {
            connect();

            PreparedStatement stmt = conn.prepareStatement("SELECT AVG(Cost) FROM Building");
            ResultSet rs = stmt.executeQuery();
            System.out.println("AVG(Cost)");
            System.out.println("---------");
            while (rs.next()) {

                System.out.printf("%-9d\n", rs.getInt("AVG(Cost)"));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    protected void selectParking(String entry, boolean all) {
        PreparedStatement statement;
        try {
            connect();

            if (all) statement = conn.prepareStatement("SELECT * FROM Parking");
            else {
                statement = conn.prepareStatement("SELECT * FROM Parking WHERE Pass_type=?");
                statement.setString(1,entry);
            }
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-19s | %s | %s | %s\n", "Parking_name", "Parking_spots", "Location_id", "Pass_type");
            System.out.println("-------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-19s | %13d | %11d | %9s\n", rs.getString("Parking_name"), rs.getInt("Parking_spots"), rs.getInt("Location_id"), rs.getString("Pass_type"));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void selectLocation(int entry, boolean all) {
        PreparedStatement statement;
        try {
            connect();
            if (all) statement = conn.prepareStatement("SELECT * FROM Location");
            else {statement = conn.prepareStatement("SELECT * FROM Location WHERE Location_id=?");
                statement.setInt(1,entry);
            }
            ResultSet rs = statement.executeQuery();
            System.out.printf("%s | %-22s | %s | %s | %s\n", "Location_id", "Address", "Campus_name", "Quad_name", "Grid_number");
            System.out.println("----------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-11d | %22s | %11s | %9s | %11s\n", rs.getInt("Location_id"), rs.getString("Address"), rs.getString("Campus_name"), rs.getString("Quad_name"), rs.getString("Grid_number"));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void selectCampus(String entry, boolean all) {
        PreparedStatement statement;
        try {
            connect();

            if (all) statement = conn.prepareStatement("SELECT * FROM Campus");
            else {
                statement = conn.prepareStatement("SELECT * FROM Campus WHERE Campus_name=?");
                statement.setString(1, entry);
            }
            ResultSet rs = statement.executeQuery();
            System.out.printf("%s | %s | %s\n","Campus_name", "State", "City");
            System.out.println("--------------------------------");
            while (rs.next()) {
                System.out.printf("%-11s | %5s | %5s\n", rs.getString("Campus_name"), rs.getString("State"), rs.getString("City"));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void selectBuildingType(String entry, boolean all) {
        PreparedStatement statement;
        try {
            connect();
            if (all) {
                statement = conn.prepareStatement("SELECT * FROM Building_type");

            } else {
                statement = conn.prepareStatement("SELECT * FROM Building_type WHERE type_name=?");
                statement.setString(1,entry);
            }
            ResultSet rs = statement.executeQuery();

            System.out.printf("%s | %s\n", "typeID", "type_name");
            System.out.println("------------------");
            while (rs.next()) {
                System.out.printf("%-6d | %9s\n", rs.getInt("typeID"), rs.getString("type_name"));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void selectBuilding(String entry, boolean all, boolean id) {
        PreparedStatement ps;
        try {
            connect();
            if (all)
                ps = conn.prepareStatement("SELECT * FROM Building");
            else if (id) {
                ps = conn.prepareStatement("SELECT * FROM Building WHERE Building_id=?");
                ps.setString(1, entry);
            }
            else {
                ps = conn.prepareStatement("SELECT * FROM Building WHERE Building_name=?");
                ps.setString(1, entry);
            }
            ResultSet rs = ps.executeQuery();
            System.out.printf("%s | %8s | %s | %s | %3s | %s\n", "Building_id", "Cost", "Capacity", "Location_id","Building_name", "typeID");
            System.out.println("------------------------------------------------------------------------");
            while (rs.next()) {
                int Bid = rs.getInt("Building_id");
                int cost = rs.getInt("Cost");
                int cap = rs.getInt("Capacity");
                int Lid = rs.getInt("Location_id");
                String bulding = rs.getString("Building_name");
                int typeID = rs.getInt("typeID");
                System.out.printf("%-11d | %8d | %8d | %11d | %13s | %6d\n", Bid,cost, cap,Lid,bulding,typeID);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    protected void join() {
        try {
            connect();

            PreparedStatement statement = conn.prepareStatement("SELECT l.Address, c.City, c.State  FROM Location l, Campus c WHERE c.Campus_name = l.Campus_name;");
            ResultSet resultSet = statement.executeQuery();
            System.out.printf("%-22s |%7s  | %5s\n", "Address", "City", "State");
            System.out.println("-----------------------------------------");
            while (resultSet.next()) {
                String address = resultSet.getString("Address");
                String city = resultSet.getString("City");
                String state = resultSet.getString("State");
                System.out.printf("%-22s | %5s | %5s\n", address, city, state);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    protected
//    public static void main(String[] args) {
//        DB db = new DB();
//
//        System.out.println("TESTING JOIN");
//        System.out.println("*************************");
//        db.join();
//        System.out.println();
//        System.out.println("TESTING SELECT BUILDING");
//        System.out.println("*************************");
//        db.selectBuilding("");
//        System.out.println();
//        System.out.println("TESTING AVG");
//        System.out.println("*************************");
//        db.avg();
//        System.out.println();
//        System.out.println("TESTING SELECT CAMPUS");
//        System.out.println("*************************");
//        db.selectCampus("");
//        System.out.println();
//        System.out.println("TESTING SELECT LOCATION");
//        System.out.println("*************************");
//        db.selectLocation(1);
//        System.out.println();
//        System.out.println("TESTING SELECT PARKING");
//        System.out.println("*************************");
//        db.selectParking("");
//        System.out.println();
//        System.out.println("TESTING SELECT BUILDING TYPE");
//        System.out.println("*************************");
//        db.selectBuildingType("",true);
//    }
}
