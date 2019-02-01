/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.CityDB;
import c195.thanhtruong.model.CountryDB;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class UpdateCustomerController extends AbstractController implements Initializable {

    @FXML
    private TextField customerName;

    @FXML
    private TextField address1;

    @FXML
    private TextField address2;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField phoneNumber;
    
    @FXML
    private TextField currentCountry;

    @FXML
    private TextField currentCity;

    @FXML
    private ComboBox<String> countryCbo;

    @FXML
    private ComboBox<String> cityCbo;

    @FXML
    private Button cancelUpdateButton;

    @FXML
    private Button saveUpdateButton;

    @FXML
    void handleCancelUpdate(ActionEvent event) {

    }

    @FXML
    void handleSaveUpdate(ActionEvent event) {
        
    }
    
     @FXML
    void handleCityClicked(MouseEvent event) {
        if (countryCbo.getSelectionModel().getSelectedIndex() == -1) {
            WarningPopup.showAlert(getDialogStage(), "Attention!",
                                    "Country not selected!", "Please, select a country first!");
        } 
    }
    
    @FXML
    void handleCitySelected(ActionEvent event) {
        currentCity.setText(cityCbo.getSelectionModel().getSelectedItem());
    }

    @FXML
    void handleCountrySelected(ActionEvent event) {
        currentCountry.setText(countryCbo.getSelectionModel().getSelectedItem());
        CityDB cityDB = new CityDB(countryCbo.getSelectionModel().getSelectedItem());
        cityCbo.setItems(cityDB.getListAsString());
    }
    
    public void displayCustData(Customer selectedCust) {
        customerName.setText(selectedCust.getCustomerName());
        address1.setText(selectedCust.getAddress1());
        address2.setText(selectedCust.getAddress2());
        postalCode.setText(selectedCust.getPostalCode());
        phoneNumber.setText(selectedCust.getPhone());
        currentCountry.setText(selectedCust.getCountry());
        currentCity.setText(selectedCust.getCity());
        
        CountryDB countryDB = new CountryDB();
        countryCbo.setItems(countryDB.getListAsString());
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Select a country first to enable this drop-down!");
        cityCbo.setTooltip(tooltip);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        
    }
    
}
