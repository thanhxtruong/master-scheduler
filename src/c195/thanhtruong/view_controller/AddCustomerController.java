
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
        DataInput dataInput = new DataInput();
        try {
            dataInput.checkMissingInput(customerName.getText(),address1.getText(),
                    countryCbo.getSelectionModel().getSelectedItem(),
                    cityCbo.getSelectionModel().getSelectedItem(),
                    postalCode.getText(), phoneNumber.getText());
            
        } catch (NullPointerException ex)  {
            DialogPopup.showAlert(getDialogStage(),
                                    "Warning",
                                    ex.getMessage(),
                                    "Please, fill in the missing input",
                                    AlertType.ERROR);
        } finally {
            if (!dataInput.isMissingInput()) {
                Customer newCust = new Customer(customerName.getText(),
                    address1.getText(),
                    address2.getText(),
                    cityCbo.getSelectionModel().getSelectedItem(),
                    postalCode.getText(),
                    countryCbo.getSelectionModel().getSelectedItem(),
                    phoneNumber.getText());
                
                CustomerDB.getInstance().insertDB(countryCbo.getSelectionModel().getSelectedItem(),
                                cityCbo.getSelectionModel().getSelectedItem(),
                                newCust);

                getDialogStage().close();
                WindowsDisplay windowDisplay = new WindowsBuilder()
                    .setFXMLPath("CustomerList.fxml")
                    .setTitle("Customer Maintenance")
                    .build();
                windowDisplay.displayScene();
            }
        }
    }

    @FXML
    void handleCancelAddCust(ActionEvent event) {
        DialogPopup.exitConfirmation(getDialogStage());
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
    
    /*
    Event handler trigger when user click to select City combobox.
    The alert is invoked only when user has not selected a country yet.
    */
    @FXML
    void handleCitySelected() {
        if (countryCbo.getSelectionModel().getSelectedIndex() == -1) {
            DialogPopup.showAlert(getDialogStage(), "Attention!",
                "Country not selected!", "Please, select a country first!",
                AlertType.ERROR);
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
        countryCbo.setItems(countryDB.getListAsString());
        Tooltip tooltip = new Tooltip();
        tooltip.setText("Select a country first to enable this drop-down!");
        cityCbo.setTooltip(tooltip);
    }    

    @Override
    public void displayData(Customer selectedCust, Appointment appointment) {
        
    }
    
}
