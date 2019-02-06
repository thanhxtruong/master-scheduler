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
import java.time.ZoneId;
import static java.time.ZonedDateTime.now;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thanhtruong
 */
public class CustomerDB{
    
    private static ObservableList<Customer> customerList;

    public CustomerDB() {
        this.customerList = FXCollections.observableArrayList();
    }

    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }
    
    public Customer getCustByName(String name) {
        for (Customer customer:customerList) {
            if (customer.getCustomerName().contains(name)) {
                return customer;
            }
        }
        return null;
    }
    
    public List<String> getCustListAsString() {
        List<String> custNameList = new ArrayList<>();
        for(Customer customer:customerList) {
            custNameList.add(customer.getCustomerName());
        }
        return custNameList;
    }
            
    public void downloadCustDB() {
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
            
            // Insert the new customer into the DB
            sqlStatement = "INSERT INTO address " +
                        "(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)\n" +
                        "VALUES ('"+ newCust.getAddress1() + "', '" +
                        newCust.getAddress2() + "', " + 
                        ID + ", '" +
                        newCust.getPostalCode() + "', '" +
                        newCust.getPhone() + "', '" + 
                        now(ZoneId.of("UTC")) + "', '" + MainApp.getCurrentUser().getUserName() + "', '" +
                        now(ZoneId.of("UTC")) + "', '" + MainApp.getCurrentUser().getUserName() + "')";
            
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
                now(ZoneId.of("UTC")) + "', '" + MainApp.getCurrentUser().getUserName() + "', '" +
                now(ZoneId.of("UTC")) + "', '" + MainApp.getCurrentUser().getUserName() + "')";
            
            Query.makeQuery(sqlStatement);
            
            DBConnection.closeConnection();
            
            // Update customerList
            downloadCustDB();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }      
    }
    
    public void updateDB(Customer newCust, Customer selectedCust, boolean isAddressChanged, boolean isCustNameChanged) {
        int cityId, addressId;
        String sqlStatement;
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            if(isAddressChanged) {
                // Get the cityId for the new city
                sqlStatement = "SELECT cityId FROM city WHERE city = '" + 
                                    newCust.getCity() + "'";
                Query.makeQuery(sqlStatement);
                ResultSet result = Query.getResult();            
                result.next();
                cityId = result.getInt("cityId");

                // Update address
                sqlStatement = "UPDATE address\n" +
                                "SET address = '" + newCust.getAddress1() + "',\n" +
                                "address2 = '" + newCust.getAddress2() + "',\n" +
                                "cityId = " + cityId + ",\n" +
                                "postalCode = '" + newCust.getPostalCode() + "',\n" +
                                "phone = '" + newCust.getPhone() + "',\n" +
                                "lastUpdate = '" + now(ZoneId.of("UTC")) + "',\n" +
                                "lastUpdateBy = '" + MainApp.getCurrentUser().getUserName() + "'\n" +
                                "WHERE addressId = " + selectedCust.getAddressId();
                Query.makeQuery(sqlStatement);
            }
            
            if(isCustNameChanged) {
                sqlStatement = "UPDATE customer\n" +
                            "SET customerName = '" + newCust.getCustomerName() + "'\n" +
                            "WHERE customerId = " + selectedCust.getCustomerID();
                
                Query.makeQuery(sqlStatement);
            }
            
            DBConnection.closeConnection();
            downloadCustDB();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public boolean addCustomer(Customer customer) {
        customerList.add(customer);
        return true;
    }
    
    public boolean deleteCustomer(Customer customer) {
        String sqlStatement;
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            sqlStatement = "DELETE FROM address, customer\n" +
                            "USING customer\n" +
                            "INNER JOIN address\n" +
                            "WHERE customerId = " + customer.getCustomerID() + 
                            " AND address.addressId = " + customer.getAddressId();
            Query.makeQuery(sqlStatement);
            
            DBConnection.closeConnection();
            
            downloadCustDB();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return true;
    }
}
