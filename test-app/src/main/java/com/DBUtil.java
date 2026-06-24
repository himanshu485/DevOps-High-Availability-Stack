package com.example;

import java.sql.*;

public class DBUtil {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {

        String url =
            "jdbc:postgresql://pgpool:5432/mydb";

        String user = "postgres";
        String password = "mysecretpassword";

        return DriverManager.getConnection(
                url,
                user,
                password);
    }
}
