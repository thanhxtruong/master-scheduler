
package c195.thanhtruong.service;

import static c195.thanhtruong.service.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * The Query class is used to generate the appropriate SQL query
 * (executeQuery() or executeUpdate()) based on the input String.
 * If the query is SELECT, a ResultSet is returned and can be retrieved via getter()
 * 
 * @param q A SQL query statement 
 * @author thanhtruong
 */
public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    
    public static void makeQuery(String q) {
        query = q;
        
        try {
            // Create Statement obj
            stmt = conn.createStatement();
            
            // Determine query execution
            if (query.toLowerCase().startsWith("select")) {
                result = stmt.executeQuery(query);
            } else if (query.toLowerCase().startsWith("delete") ||
                        query.toLowerCase().startsWith("insert") ||
                        query.toLowerCase().startsWith("update")) {
                stmt.executeUpdate(query);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static ResultSet getResult() {
        return result;
    }
}
