/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.service.DBConnection;
import c195.thanhtruong.service.Query;
import java.sql.ResultSet;
import static java.time.LocalDateTime.now;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thanhtruong
 */
public class CustomerDB {
    private static ObservableList<Customer> customerList;

    public CustomerDB() {
        this.customerList = FXCollections.observableArrayList();
    }

    public ObservableList<Customer> getCustomerList() {
        return customerList;
    }
        
    public void downloadCustDB() {
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            String sqlStatement = "SELECT customerId, customerName, address, address2, city, postalCode, country, phone\n" +
                                "FROM customer, address, city, country WHERE customer.addressId = address.addressId\n" +
                                "AND address.cityId = city.cityId\n" +
                                "AND city.countryID = country.countryId " +
                                "ORDER BY customerId";
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();            
            
            while (result.next()) {
                Customer tempCust = new Customer(result.getInt("customerId"),
                                    result.getString("customerName"),
                                    result.getString("address"),
                                    result.getString("address2"),
                                    result.getString("city"),
                                    result.getString("postalCode"),
                                    result.getString("country"),
                                    result.getString("phone"));
                addCustomer(tempCust);
            }
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void insertDB(String country, String city, Customer newCust) {
        int ID;
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            // Get the cityId (FK) for the city and country input by user from GUI
            String sqlStatement = "SELECT cityId FROM country, city WHERE country.country = '" +
                                country + "' AND city.city = '" +
                                city + "'";
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();
            result.next();
            
            ID = result.getInt("cityId");
            System.out.println(ID);
            
            // Insert the new customer into the DB
            sqlStatement = "INSERT INTO address " +
                        "(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)\n" +
                        "VALUES ('"+ newCust.getAddress1() + "', '" +
                        newCust.getAddress2() + "', " + 
                        ID + ", '" +
                        newCust.getPostalCode() + "', '" +
                        newCust.getPhone() + "', '" + 
                        now() + "', '" + MainApp.getCurrentUser().getUserName() + "', '" +
                        now() + "', '" + MainApp.getCurrentUser().getUserName() + "')";
            
            Query.makeQuery(sqlStatement);
            
            // Get the addressId (FK) to insert into the customer table
            sqlStatement = "SELECT last_insert_id() FROM address";
            Query.makeQuery(sqlStatement);
            result = Query.getResult();
            result.next();
            ID = result.getInt("last_insert_id()");
            
            sqlStatement = "INSERT INTO customer " +
                "(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)\n" +
                "VALUES ('" + newCust.getCustomerName() + "', " + ID + ", 1, '" +
                now() + "', '" + MainApp.getCurrentUser().getUserName() + "', '" +
                now() + "', '" + MainApp.getCurrentUser().getUserName() + "')";
            
            Query.makeQuery(sqlStatement);
            
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }      
    }
           
    public boolean addCustomer(Customer customer) {
        customerList.add(customer);
        return true;
    }
    
    public boolean updateCustomer(Customer customer) {
        return true;
    }
    
    public boolean deleteCustomer(Customer customer) {
        return true;
    }
}
