/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.CityDB;
import c195.thanhtruong.model.CountryDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.InputMethodEvent;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class AddCustomerController extends AbstractController implements Initializable {
    
    @FXML
    private TextField customerName;

    @FXML
    private TextField address1;

    @FXML
    private TextField address2;

    @FXML
    private ComboBox<String> cityCbo;

    @FXML
    private TextField postalCode;

    @FXML
    private ComboBox<String> countryCbo;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button addCustomerCancel;

    @FXML
    private Button addCustomerButton;

    @FXML
    void handleSaveAddCust(ActionEvent event) {

    }

    @FXML
    void handleCancelAddCust(ActionEvent event) {

    }
    
    /*
    Event Handler triggered when user selects a country from the combobox.
    A new instance of CityDB is instantiated to retrieve the list of city for
    the selected country and populate the City combobox with this list.
    */
    @FXML
    void handleCountrySelected() {
        CityDB cityDB = new CityDB(countryCbo.getSelectionModel().getSelectedItem());
        cityCbo.setItems(cityDB.getListAsString());
    }

    /**
     * Initializes the controller class.
     * A new instance of the countryDB is instantiated to retrieve the list of
     * all countries from the database.
     * Populate the Country combobox with this list.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        CountryDB countryDB = new CountryDB();
        for (String item:countryDB.getListAsString()) {
            System.out.println(item);
        }
        countryCbo.setItems(countryDB.getListAsString());
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Select a country first to enable this drop-down!");
        cityCbo.setTooltip(tooltip);
    }    
    
}
