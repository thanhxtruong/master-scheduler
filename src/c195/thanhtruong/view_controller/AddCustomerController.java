/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.model.CityDB;
import c195.thanhtruong.model.CountryDB;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.CustomerDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
        if(isInputValid()) {
            Customer newCust = new Customer(customerName.getText(),
                    address1.getText(),
                    address2.getText(),
                    cityCbo.getSelectionModel().getSelectedItem(),
                    postalCode.getText(),
                    countryCbo.getSelectionModel().getSelectedItem(),
                    phoneNumber.getText());
            
            CustomerDB custDB = new CustomerDB();
            custDB.insertDB(countryCbo.getSelectionModel().getSelectedItem(),
                            cityCbo.getSelectionModel().getSelectedItem(),
                            newCust);
            WindowsDisplay.displayScene("CustomerList.fxml", "Customer Maintenance");
        }
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
    
    private boolean isInputValid(){
        String errorMessage = "";
        
        if(customerName.getText() == null ||
            customerName.getText().length() == 0 ||
            address1.getText() == null ||
            address1.getText().length() == 0 ||
            countryCbo.getSelectionModel().getSelectedItem() == null ||
            countryCbo.getSelectionModel().getSelectedItem().length() == 0 ||
            cityCbo.getSelectionModel().getSelectedItem() == null ||
            cityCbo.getSelectionModel().getSelectedItem().length() == 0 ||    
            postalCode.getText() == null ||
            postalCode.getText().length() == 0 ||
            phoneNumber.getText() == null ||
            phoneNumber.getText().length() == 0) {
            errorMessage = "Missing input!";
        }        
        
        if(errorMessage.length() == 0){
            return true;
        } else {
            WarningPopup.showAlert(getDialogStage(),
                                    "Warning",
                                    "Missing input",
                                    "Please, fill in the missing input"); 
            return false;
        }
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
