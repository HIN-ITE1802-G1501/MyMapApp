package no.hin.student.mymapapp.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by kurt-erik on 15.04.2015.
 */
public class Database {
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    private String dbUsername = "karlsen";
    private String dbPassword = "";
    private String dbName = "";

    public void writeData() {

    }

    public String getData() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = (Connection) DriverManager.getConnection("jdbc:mysql://158.39.26.242:3306/" + dbName, dbUsername, dbPassword);
            st = (Statement) con.createStatement();
            rs = (ResultSet) st.executeQuery("SELECT id, device, latitude, longitude, FROM mymapapp");
            StringBuffer sb = new StringBuffer();
            while (rs.next()) {
                String id = rs.getString(1);
                String device = rs.getString(2);
                String latitude = rs.getString(3);
                String longitude = rs.getString(4);
                sb.append(id + ", " + device + ", " + latitude + ", " + longitude + "\n");
            }
            return sb.toString();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {}
        }
        return "Error reading from database";
    }
}