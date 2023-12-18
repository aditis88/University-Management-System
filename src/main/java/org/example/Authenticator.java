package org.example;

import java.sql.*;

public class Authenticator {

    public boolean authenticate(String username,String role, String password) {
        // Retrieve the hashed password from the database for the given username
        String storedHashedPassword = retrieveHashedPasswordFromDB(username,role);

        // Check if the given password matches the stored hashed password
        return  (password.equals(storedHashedPassword));
    }

    private String retrieveHashedPasswordFromDB(String username,String role) {
        // In a real-world scenario, you would retrieve the hashed password from a database
        // For the purpose of this example, we'll return a fixed hashed password
        GetConnection gtCon = new GetConnection();
        Connection con = gtCon.getConnection();

        String password = "";
        String findQuery = "Select password from user_table as u where u.user_id = ? and u.role = ? ";
        try{
            PreparedStatement preparedStatement = con.prepareStatement(findQuery);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,role);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
            {
                password = rs.getString(1);
            }
            else {
                System.out.println("NO user found!!");
            }
            con.close();

        }catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return password;
    }
//    public static void main(String[] args)
//    {
//        String username = "2020csb1101@iitrpr.ac.in";
//        String role = "student";
//        String password= "7(G])@&r";
//        Authenticator auth = new Authenticator();
//        boolean ans = auth.authenticate(username,role,password);
//        System.out.println(ans);
//
//    }

}
