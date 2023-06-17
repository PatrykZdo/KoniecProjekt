package com.example.demo.Database_Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection connect(){
        Connection conn = null;

        try{
            String url = "jdbc:sqlite:D:/dam rade/Programowanie_Projekt/sqlite-tools-win32-x86-3420000/BazaDanychProjekt.db";
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
