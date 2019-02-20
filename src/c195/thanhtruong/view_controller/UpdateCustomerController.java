
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.CityDB;
import c195.thanhtruong.model.CountryDB;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.CustomerDB;
import c195.thanhtruong.model.DataInput;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    
    private Customer tempCust;
    private Customer newCust;

    @FXML
    void handleCancelUpdate(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("CustomerList.fxml")
                .setTitle("Customer Maintenance")
                .build();
        windowDisplay.displayScene();
    }
    
    @FXML
    void handleSaveUpdate(ActionEvent event) {
        DataInput dataInput = new DataInput();
        try {
            dataInput.checkMissingInput(customerName.getText(),address1.getText(),
                    currentCountry.getText(),currentCity.getText(),
                    postalCode.getText(), phoneNumber.getText());
            
        } catch (NullPointerException ex)  {
            DialogPopup.showAlert(getDialogStage(),
                                    "Warning",
                                    ex.getMessage(),
                                    "Please, fill in the missing input",
                                    AlertType.ERROR);
        } finally {
            if (!dataInput.isMissingInput()) {
                newCust = new Customer(customerName.getText(), address1.getText(),
                    address2.getText(), currentCity.getText(), postalCode.getText(),
                    currentCountry.getText(), phoneNumber.getText());
                                
                CustomerDB.getInstance().updateDB(newCust, tempCust);

                getDialogStage().close();
            }
        }
    }
    
    /*
    Display warning when user clicks on City combobox without a new country selected.
    */
    @FXML
    void handleCityClicked(MouseEvent event) {
        if (countryCbo.getSelectionModel().getSelectedIndex() == -1) {
            DialogPopup.showAlert(getDialogStage(), "Attention!",
                "Country not selected!", "Please, select a country first!",
                AlertType.ERROR);
        } 
    }
    
    @FXML
    void handleCitySelected(ActionEvent event) {
        currentCity.setText(cityCbo.getSelectionModel().getSelectedItem());
    }

    @FXML
    void handleCountrySelected(ActionEvent event) {
        currentCountry.setText(countryCbo.getSelectionModel().getSelectedItem());
        currentCity.clear();
        CityDB cityDB = new CityDB(countryCbo.getSelectionModel().getSelectedItem());
        cityCbo.setItems(cityDB.getListAsString());
    }    
    
    @Override
    public void displayData(Customer selectedCust, Appointment appoinment) {
        tempCust = selectedCust;
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
