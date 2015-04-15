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
        public String getData() {
            Connection con = null;
            Statement st = null;
            ResultSet rs = null;
            String dbName = "wfa1";
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                con = (Connection) DriverManager.getConnection("jdbc:mysql://158.39.26.242:3306/" +
                        dbName, "wfa", "testing");
                st = (Statement) con.createStatement();
                rs = (ResultSet) st.executeQuery("SELECT PersNr, Navn, Adresse FROM testtabell");
                StringBuffer sb = new StringBuffer();
                while (rs.next()) {
                    String persnr = rs.getString(1);
                    String navn = rs.getString(2);
                    String adresse = rs.getString(3);
                    sb.append(persnr + ", " + navn + ", " + adresse + "\n");
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
            return "Feil ved lesing fra database";
        }
    }