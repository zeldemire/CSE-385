package eldemizt;

import java.io.IOException;
import java.sql.*;

/**
 * Created by Zach Eldemire on 11/17/15.
 * Project
 * CSE 385
 * This class handles all of the database interactions.
 */
public class DB {
    String user = info.username;
    String pwd = info.password;
    String dbURL = "jdbc:mysql://localhost:3306/Project";
    Connection conn = null;

    public void connect() throws IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, user, pwd);

        } catch (Exception err) {
            throw new IOException(err);
        }
    }

    /**
     * Computes the average building cost.
     */
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
            System.out.println("---------");
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    /**
     * Selects information from the parking table.
     * @param entry pass type to search for.
     * @param all true if user wants to return everything in table, false if they want to search for just the pass type.
     */
    protected void selectParking(String entry, boolean all) {
        PreparedStatement statement;
        try {
            connect();

            if (all) statement = conn.prepareStatement("SELECT * FROM Parking");
            else {
                statement = conn.prepareStatement("SELECT * FROM Parking WHERE Pass_type=?");
                statement.setString(1, entry);
            }
            ResultSet rs = statement.executeQuery();
            System.out.printf("%-19s | %s | %s | %s\n", "Parking_name", "Parking_spots", "Location_id", "Pass_type");
            System.out.println("-------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-19s | %13d | %11d | %9s\n", rs.getString("Parking_name"), rs.getInt("Parking_spots"), rs.getInt("Location_id"), rs.getString("Pass_type"));
            }
            System.out.println("-------------------------------------------------------------");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects information from the location table.
     * @param entry either the address or the location id depends on the address boolean.
     * @param all true to show everything in table, false to just show selected field.
     * @param address true if the user wants to search using the address, false for location id.
     */
    protected void selectLocation(String entry, boolean all, boolean address) {
        PreparedStatement statement;
        try {
            connect();
            if (all) statement = conn.prepareStatement("SELECT * FROM Location");
            else if (address) {
                statement = conn.prepareStatement("SELECT * FROM Location WHERE Address=?");
                statement.setString(1, entry);
            } else {
                statement = conn.prepareStatement("SELECT * FROM Location WHERE Location_id=?");
                statement.setString(1, entry);
            }
            ResultSet rs = statement.executeQuery();
            System.out.printf("%s | %22s | %s | %s | %s\n", "Location_id", "Address", "Campus_name", "Quad_name", "Grid_number");
            System.out.println("----------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-11d | %22s | %11s | %9s | %11s\n", rs.getInt("Location_id"), rs.getString("Address"), rs.getString("Campus_name"), rs.getString("Quad_name"), rs.getString("Grid_number"));
            }
            System.out.println("----------------------------------------------------------------------------");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects information from the field table.
     * @param entry location id of the field.
     * @param all returns all of the information from the table.
     */
    protected void selectField(int entry, boolean all) {
        PreparedStatement preparedStatement;
        try {
            connect();
            if (all) preparedStatement = conn.prepareStatement("SELECT * FROM Field");
            else {
                preparedStatement = conn.prepareStatement("SELECT * FROM Field WHERE Location_id=?");
                preparedStatement.setInt(1, entry);
            }
            ResultSet rs = preparedStatement.executeQuery();
            System.out.printf("%s | %s | %-13s\n","Field_name", "Location_id", "Field_uses");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.printf("%10s | %11d | %s\n", rs.getString("Field_name"), rs.getInt("Location_id"), rs.getString("Field_uses"));
            }
            System.out.println("----------------------------------------");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects information from the campus table.
     * @param entry the campus name.
     * @param all returns all of the information from the table.
     */
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
            System.out.printf("%s | %s | %s\n", "Campus_name", "State", "City");
            System.out.println("--------------------------------");
            while (rs.next()) {
                System.out.printf("%-11s | %5s | %5s\n", rs.getString("Campus_name"), rs.getString("State"), rs.getString("City"));
            }
            System.out.println("--------------------------------");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects information from the building type table.
     * @param entry the type name of the building.
     * @param all returns all of the information from the table.
     */
    protected void selectBuildingType(String entry, boolean all) {
        PreparedStatement statement;
        try {
            connect();
            if (all) {
                statement = conn.prepareStatement("SELECT * FROM Building_type");

            } else {
                statement = conn.prepareStatement("SELECT * FROM Building_type WHERE type_name=?");
                statement.setString(1, entry);
            }
            ResultSet rs = statement.executeQuery();

            System.out.printf("%s | %s\n", "typeID", "type_name");
            System.out.println("------------------");
            while (rs.next()) {
                System.out.printf("%-6d | %9s\n", rs.getInt("typeID"), rs.getString("type_name"));
            }
            System.out.println("------------------");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects information from the building table.
     * @param entry either the building id or the building name.
     * @param all returns all of the information from the table.
     * @param id changes the query to search for buildings using the id.
     */
    protected void selectBuilding(String entry, boolean all, boolean id) {
        PreparedStatement ps;
        try {
            connect();
            if (all)
                ps = conn.prepareStatement("SELECT * FROM Building");
            else if (id) {
                ps = conn.prepareStatement("SELECT * FROM Building WHERE Building_id=?");
                ps.setString(1, entry);
            } else {
                ps = conn.prepareStatement("SELECT * FROM Building WHERE Building_name=?");
                ps.setString(1, entry);
            }
            ResultSet rs = ps.executeQuery();
            System.out.printf("%s | %8s | %s | %s | %3s | %s\n", "Building_id", "Cost", "Capacity", "Location_id", "Building_name", "typeID");
            System.out.println("------------------------------------------------------------------------");
            while (rs.next()) {
                int Bid = rs.getInt("Building_id");
                int cost = rs.getInt("Cost");
                int cap = rs.getInt("Capacity");
                int Lid = rs.getInt("Location_id");
                String bulding = rs.getString("Building_name");
                int typeID = rs.getInt("typeID");
                System.out.printf("%-11d | %8d | %8d | %11d | %13s | %6d\n", Bid, cost, cap, Lid, bulding, typeID);
            }
            System.out.println("------------------------------------------------------------------------");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Selects information from the location table.
     * @param entry address to search for
     * @return the location id to the address
     */
    protected int selectLocIDByAddress(String entry) {
        PreparedStatement statement;
        try {
            connect();
            statement = conn.prepareStatement("SELECT Location_id FROM Location WHERE Address=?");
            statement.setString(1, entry);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) return rs.getInt("Location_id");
            else return -1;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Joins the campus table with the location table on the campus name.
     */
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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows all of the table names in the database.
     */
    protected void showTableNames() {
        try {
            connect();
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet rs1 = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
            while (rs1.next())
            {
                System.out.print(rs1.getString(3) + " | ");
            }
            System.out.println();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Runs the provided sql statement.
     * @param sql the users sql statement
     * @return true if everything wen ok, false if it didn't.
     */
    protected boolean update(String sql) {
        try {
            connect();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
            return true;
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}