/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import c195.thanhtruong.service.DBConnection;
import c195.thanhtruong.service.Query;
import java.sql.ResultSet;
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
        
    public void accessCustDB() {
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
//            for (Customer customer:customerList) {
//                System.out.println(customer.getAddress() + ", " + customer.getCityAndPostalCode());
//            }
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
