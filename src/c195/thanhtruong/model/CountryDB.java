/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

import c195.thanhtruong.service.DBConnection;
import c195.thanhtruong.service.Query;
import java.sql.ResultSet;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The CountryDB class is used to retrieve the list of all countries from the DB.
 * ObservableList is used to stored data for filtering and sorting purposes.
 * The getListAsString() method is used to populate data in combobox GUI.
 *  * 
 * @author thanhtruong
 */
public class CountryDB {
    ObservableList<StringProperty> countryList = FXCollections.observableArrayList();
    public CountryDB() {
        
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            String sqlStatement = "SELECT country FROM country";            
            
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();            
            
            while (result.next()) {
                countryList.add(new SimpleStringProperty(result.getString("country")));
            }
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public ObservableList<StringProperty> getCountryList() {
        return countryList;
    }
    
    public ObservableList<String> getListAsString() {
        ObservableList<String> countryStringList = FXCollections.observableArrayList();
        for (StringProperty country:countryList) {
            countryStringList.add(country.getValue());
        }
        return countryStringList;
    }
    
}
