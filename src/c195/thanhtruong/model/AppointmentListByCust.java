/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import c195.thanhtruong.service.DBConnection;
import c195.thanhtruong.service.Query;
import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thanhtruong
 */
public class AppointmentListByCust {
    
    public void downloadAppt() {
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            String sqlStatement = "SELECT customerId, customerName, " +
                "customer.addressId, address, address2,  postalCode,  " +
                "phone, address.cityId, city, country\n" +
                "FROM customer\n" +
                "INNER JOIN address\n" +
                "ON customer.addressId = address.addressId\n" +
                "INNER JOIN city\n" +
                "ON address.cityId = city.cityId\n" +
                "INNER JOIN country\n" +
                "ON city.countryId = country.countryId";
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();            
            
            while (result.next()) {
                Customer tempCust = new Customer(result.getInt("customerId"),
                                    result.getString("customerName"),
                                    result.getInt("addressId"),
                                    result.getString("address"),
                                    result.getString("address2"),
                                    result.getString("city"),
                                    result.getString("postalCode"),
                                    result.getString("country"),
                                    result.getString("phone"));
//                addCustomer(tempCust);
            }
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }            
    }
    
//    public static void main(String[] args) {
//        ObservableList<Appointment> apptList = FXCollections.observableArrayList();
//        Map<Customer, ObservableList<Appointment>> apptMapByCust = new TreeMap<>();
//        
//        
//    }
    
    
}
