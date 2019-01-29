/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.service;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DBConnection class is used to establish connection to the MySQL database.
 * The closeConnection() method is used to close the DB once completed to
 * prevent memory leak.
 * 
 * @author thanhtruong
 */
public class DBConnection {
    private static final String databaseName = "U05Z8u";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + databaseName;
    private static final String username = "U05Z8u";
    private static final String password = "53688648243";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        Class.forName(driver);
        conn = (Connection) DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection sucessful!");
    }
    
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn.close();
        System.out.println("Connection closed!");
    }
}
