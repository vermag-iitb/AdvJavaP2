package AdvanceJava2;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static void main(String[] args) {
        try {
            String url, name, pwd;
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            System.out.println("Driver loaded");
            url = "jdbc:jtds:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true;";
            name = "sa";
            pwd = "reallyStrongPwd123";
            Connection con = DriverManager.getConnection(url, name, pwd);
            if (con != null)
                System.out.println("Connection Established. \nHashcode => " + con);
            else
                System.out.println("Connection could not establish");
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}