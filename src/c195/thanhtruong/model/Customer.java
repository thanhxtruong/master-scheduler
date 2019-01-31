/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author thanhtruong
 */
public class Customer {
    private IntegerProperty customerID = new SimpleIntegerProperty();
    private StringProperty customerName = new SimpleStringProperty();
    private StringProperty address1 = new SimpleStringProperty();
    private StringProperty address2 = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty postalCode = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();
    
    
    public Customer(int customerID,
                    String customerName,
                    String address1,
                    String address2,
                    String city,
                    String postalCode,
                    String country,
                    String phone) {
        this(customerName,address1,address2, city,postalCode,country,phone);
        this.customerID.set(customerID);          
    }
    
    public Customer(String customerName,
                    String address1,
                    String address2,
                    String city,
                    String postalCode,
                    String country,
                    String phone) {
        this.customerName.set(customerName);
        this.address1.set(address1);
        this.address2.set(address2);
        this.city.set(city);
        this.postalCode.set(postalCode);
        this.country.set(country);
        this.phone.set(phone);
    }

    public int getCustomerID() {
        return customerID.get();
    }
    public String getCustomerName() {
        return customerName.get();
    }

    public String getAddress1() {
        return address1.get();
    }

    public String getAddress2() {
        return address2.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public String getCountry() {
        return country.get();
    }

    public String getPhone() {
        return phone.get();
    }
    
    public IntegerProperty customerID() {
        return customerID;
    }
    
    public StringProperty customerName() {
        return customerName;
    }
    
    public StringProperty address1() {
        return address1;
    }
    
    public StringProperty address2() {
        return address2;
    }
    
    public StringProperty city() {
        return city;
    }
    
    public StringProperty postalCode() {
        return postalCode;
    }       
    
    public StringProperty country() {
        return country;
    }
    
    public StringProperty phone() {
        return phone;
    }
}
