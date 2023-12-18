package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class Database {
    public boolean create()
    {
        Connection con = null;
        Statement st;
        try {
            GetConnection gtCon = new GetConnection();
            con = gtCon.getConnection();
            st = con.createStatement();
            drop_table(st);
            create_table(st);
            st.close();
            con.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    void drop_table (Statement st) {
        try {
            String drop_file = "/home/ashish/hdd/study/java_proj/AIIMSPortal/src/sqlSource/drop.sql";
            String drop = new String(Files.readAllBytes(Paths.get(drop_file)));
//            System.out.println(drop);
            st.execute(drop);
        }
        catch (Exception e) {
            System.out.println("drop Exception: "+ e.getMessage());
        }
    }

    void create_table (Statement st) {
        try {
            String create_file = "/home/ashish/hdd/study/java_proj/AIIMSPortal/src/sqlSource/create.sql";
            String create = new String(Files.readAllBytes(Paths.get(create_file)));
            st.execute(create);
        }
        catch (Exception e) {
            System.out.println("Create Exception");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        Database crtDb = new Database();
        crtDb.create();
    }
}
