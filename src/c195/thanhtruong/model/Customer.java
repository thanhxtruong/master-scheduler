/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import c195.thanhtruong.view_controller.DialogPopup;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author thanhtruong
 */
public class Customer extends AbstractModel {
    private IntegerProperty customerID = new SimpleIntegerProperty();
    private StringProperty customerName = new SimpleStringProperty();
    private IntegerProperty addressId = new SimpleIntegerProperty();
    private StringProperty address1 = new SimpleStringProperty();
    private StringProperty address2 = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty postalCode = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();
    
    
    public Customer(int customerID,
                    String customerName,
                    int addressId,
                    String address1,
                    String address2,
                    String city,
                    String postalCode,
                    String country,
                    String phone) {
        this(customerName,address1,address2, city,postalCode,country,phone);
        this.customerID.set(customerID);
        this.addressId.set(addressId);
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
    
    public int getAddressId() {
        return addressId.get();
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
    
    public IntegerProperty customerIDProperty() {
        return customerID;
    }
    
    public StringProperty customerNameProperty() {
        return customerName;
    }
    
    public StringProperty address1Property() {
        return address1;
    }
    
    public StringProperty address2Property() {
        return address2;
    }
    
    public StringProperty cityProperty() {
        return city;
    }
    
    public StringProperty postalCodeProperty() {
        return postalCode;
    }       
    
    public StringProperty countryProperty() {
        return country;
    }
    
    public StringProperty phoneProperty() {
        return phone;
    }
}
