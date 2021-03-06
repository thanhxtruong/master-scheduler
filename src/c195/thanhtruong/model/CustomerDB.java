
package c195.thanhtruong.model;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.service.ActivityLogger;
import c195.thanhtruong.service.DBConnection;
import c195.thanhtruong.service.Query;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.ZoneId;
import static java.time.ZonedDateTime.now;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Callback;

/**
 * The CustDB class is used to download appointments from the MYSQL database
 * and to retrieve lists of customers for other method calls.
 * This class uses the Singleton design pattern to allow only one object to be created
 * in memory and shared among multiple classes as the lists/maps of appointments
 * are updated/downloaded from the DB after each INSERT, DELETE, UPDATE query.
 * @author thanhtruong
 */
public class CustomerDB{
    
    private static ObservableList<Customer> customerList;
    private static final CustomerDB instance;
    private String sqlStatement;
    
    static {
        instance = new CustomerDB();
        Callback<Customer, Observable[]> extractor = (Customer c) -> new Observable[] {
            c.customerIDProperty(),
            c.customerNameProperty(),
            c.address1Property(),
            c.address2Property(),
            c.cityProperty(),
            c.postalCodeProperty(),
            c.countryProperty(),
            c.phoneProperty()
        };
        customerList = FXCollections.observableArrayList(extractor);
    }

    private CustomerDB() {}
    
    public static CustomerDB getInstance() {
        return instance;
    }

    public ObservableList<Customer> getCustomerList() {
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
    
    /**
     * This method downloads the list of all customers in the DB.
     */
    public void downloadCustDB() {
        customerList.clear();
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            sqlStatement = "SELECT customerId, customerName, " +
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
                customerList.add(tempCust);
            }
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    /**
     * This method adds new customer to the MySQL DB.
     * The event is logged in the ActivityLogger.
     * The custList is updated after insertion.
     * @param country country of the new customer
     * @param city city of the new customer
     * @param newCust object for the new Customer
     */
    public void insertDB(String country, String city, Customer newCust) {
        int ID;
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            // Get the cityId (FK) for the city and country input by user from GUI
            sqlStatement = "SELECT cityId FROM country, city WHERE country.country = '" +
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
                Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) +
                "', '" + MainApp.getCurrentUser().getUserName() + "', '" +
                Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) +
                "', '" + MainApp.getCurrentUser().getUserName() + "')";
            
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
                Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) +
                "', '" + MainApp.getCurrentUser().getUserName() + "', '" +
                Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) +
                "', '" + MainApp.getCurrentUser().getUserName() + "')";
            
            Query.makeQuery(sqlStatement);
            // Get the new customerID
            sqlStatement = "SELECT last_insert_id() FROM customer";
            Query.makeQuery(sqlStatement);
            result = Query.getResult();
            result.next();
            ID = result.getInt("last_insert_id()");
            
            ActivityLogger.logActivities(MainApp.getCurrentUser().getUserName() +
                    " added a new customerId #" + ID);
            
            DBConnection.closeConnection();
            
            // Update customerList
            downloadCustDB();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }      
    }
    
    /**
     * This method update the current customer in the DB.
     * The event is logged in the ActivityLogger.
     * The custList is updated after insertion.
     * @param newCust new Customer
     * @param selectedCust current Customer
     */
    public void updateDB(Customer newCust, Customer selectedCust) {
        int cityId, addressId;
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
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
                "lastUpdate = '" + Timestamp.valueOf(now(ZoneId.of("UTC")).toLocalDateTime()) + "',\n" +
                "lastUpdateBy = '" + MainApp.getCurrentUser().getUserName() + "'\n" +
                "WHERE addressId = " + selectedCust.getAddressId();
            Query.makeQuery(sqlStatement);
            
            sqlStatement = "UPDATE customer\n" +
                            "SET customerName = '" + newCust.getCustomerName() + "'\n" +
                            "WHERE customerId = " + selectedCust.getCustomerID();
                
            Query.makeQuery(sqlStatement);
            
            ActivityLogger.logActivities(MainApp.getCurrentUser().getUserName() +
                    " updated customerId #" + selectedCust.getCustomerID());
            
            DBConnection.closeConnection();
            downloadCustDB();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public boolean deleteCustomer(Customer customer) {
        try {
            int custId = customer.getCustomerID();
            // Connect to the DB
            DBConnection.makeConnection();
            
            sqlStatement = "DELETE FROM appointment\n" +
                    "WHERE customerId = " + customer.getCustomerID();
            Query.makeQuery(sqlStatement);
            
            sqlStatement = "DELETE FROM address, customer\n" +
                            "USING customer\n" +
                            "INNER JOIN address\n" +
                            "WHERE customerId = " + customer.getCustomerID() + 
                            " AND address.addressId = " + customer.getAddressId();
            Query.makeQuery(sqlStatement);
            
            ActivityLogger.logActivities(MainApp.getCurrentUser().getUserName() +
                    " deleted customerId #" + custId);
            
            DBConnection.closeConnection();
            
            downloadCustDB();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return true;
    }
}
