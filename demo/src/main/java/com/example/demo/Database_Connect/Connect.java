package com.example.demo.Database_Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection connect(){
        Connection conn = null;

        try{
            String url = "jdbc:sqlite:C:\\Users\\rdxzse\\IdeaProjects\\KoniecProjekt\\demo\\src\\main\\resources\\com\\example\\demo\\BazaDanychProjekt.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been establised.");
            return conn;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                if(conn != null){
                    conn.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }
}
