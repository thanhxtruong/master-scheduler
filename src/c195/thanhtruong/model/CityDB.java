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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The CityDB class is used to retrieve the list of all cities by country from the DB.
 * The data is stored as a Map in order to implement EventHandler for City combobox in the GUI,
 * i.e. when user selects a country from the GUI, the corresponding list of cities
 * for that country will be populated in the combobox.
 * @author thanhtruong
 */
public class CityDB {
    
    ObservableList<StringProperty> cityList = FXCollections.observableArrayList();
    
    public CityDB(String country) {
        try {
            CountryDB countryDB = new CountryDB();
            // Connect to the DB
            DBConnection.makeConnection();
            
            String sqlStatement = "SELECT city, country " +
                                    "FROM city " +
                                    "INNER JOIN country ON city.countryId = country.countryId " +
                                    "WHERE country.country = '" +
                                    country + "'";
            System.out.println(sqlStatement);
            Query.makeQuery(sqlStatement);
            // result is the list of city for the selected country
            ResultSet result = Query.getResult();                            

            // Loop until end of list of city(result)
            while (result.next()) {
                cityList.add(new SimpleStringProperty(result.getString("city")));
            }                       
            
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public ObservableList<StringProperty> getCityList() {
        return cityList;
    }
    
    public ObservableList<String> getListAsString() {
        ObservableList<String> cityStringList = FXCollections.observableArrayList();
        for (StringProperty city:cityList) {
            cityStringList.add(city.getValue());
        }
        return cityStringList;
    }
    
}
