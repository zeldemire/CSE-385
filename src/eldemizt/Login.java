package eldemizt;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Created by Zach Eldemire on 11/30/15.
 * Project
 * CSE 385
 * This class handles all of the admin login logic.
 */
public class Login {
    private final String password;
    String user = info.username;
    String pwd = info.password;
    String dbURL = "jdbc:mysql://localhost:3306/Project";
    Connection conn = null;

    public Login(String password) {
        this.password = password;
        try {
            connect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Establishes a connection to the database.
     * @throws IOException
     */
    public void connect() throws IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, user, pwd);

        } catch (Exception err) {
            throw new IOException(err);
        }
    }

    /**
     * Tests to see if given username and password are an administrators.
     * @return true if it is an admin false if not
     */
    private boolean isAdmin() {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Admin WHERE username='admin' AND password=?;");
            ps.setString(1,generateHash());
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Tests to see if there is another admin editing the database.
     * @return true if there is a user, false if not.
     */
    private boolean canConnect() {
        int val = -1;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT isConnect FROM Admin");
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                val = Integer.parseInt(rs.getString("isConnect"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return val == 1;
    }

    /**
     * Tests to see if the user can edit the database.
     * @return true if the user can edit, false if not.
     */
    public boolean test() {
        if (!canConnect() && isAdmin()) {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE Admin SET isConnect=1 WHERE username='admin'");
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        else return false;
    }

    /**
     * This function logs the current user out and set the isConnect field to 0 so another user can connect.
     */
    public void exit() {
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("UPDATE Admin SET isConnect=0 WHERE username='admin'");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates a hash of the password.
     * @return the hashed password.
     */
    public String generateHash() {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
