package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GetConnection {
    Connection getConnection (){
        Connection con = null;
        try{
            String url = "jdbc:postgresql://localhost:5432/aimsdb", username = "postgres", password = "123456";

            try {
                con = DriverManager.getConnection(url,username,password);
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

//            con.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return con;
    }
}
