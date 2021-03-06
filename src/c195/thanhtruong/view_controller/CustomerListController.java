
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.CustomerDB;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class.
 * 
 * @author thanhtruong
 */
public class CustomerListController extends AbstractController implements Initializable {
    
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> custIDTableCol;

    @FXML
    private TableColumn<Customer, String> custNameTableCol;
    
    @FXML
    private TableColumn<Customer, String> phoneTableCol;

    @FXML
    private TableColumn<Customer, String> addressTableCol;

    @FXML
    private TableColumn<Customer, String> cityAndPCTableCol;

    @FXML
    private TableColumn<Customer, String> countryTableCol;

    @FXML
    private TextField customerSearchText;

    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button addButton;

    @FXML
    private Button closeButton;
    
    @FXML
    void handleAddCust(ActionEvent event) {
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("AddCustomer.fxml")
                .setTitle("Add Customer")
                .setOwnerStage(getDialogStage())
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleClose(ActionEvent event) {
        DialogPopup.exitConfirmation(getDialogStage());
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("Home.fxml")
                .setTitle("Home")
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleDeleteCust(ActionEvent event) {
        Customer selectedCust = customerTable.getSelectionModel().getSelectedItem();
        
        if (selectedCust != null) {
            CustomerDB.getInstance().deleteCustomer(selectedCust);
        } else {
            DialogPopup.showAlert(getDialogStage(), "Attention",
                "No customer selected!", "Please, select a customer to delete!",
                AlertType.ERROR);
        }
        displayCustTable(CustomerDB.getInstance());
    }

    @FXML
    void handleModifyCust(ActionEvent event) {
        Customer selectedCust = customerTable.getSelectionModel().getSelectedItem();
                
        if (selectedCust != null) {
            WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("UpdateCustomer.fxml")
                .setTitle("Update Customer Information")
                .setOwnerStage(getDialogStage())
                .setCustomer(selectedCust)
                .build();
            windowDisplay.displayScene();
        } else {
            DialogPopup.showAlert(getDialogStage(), "Attention",
                "No customer selected!", "Please, select a customer to modify!",
                AlertType.ERROR);
        }
    }
    
    // Display a filtered and sorted list of all customers
    private void displayCustTable(CustomerDB custDB) {
        // Lambda expression used for the Callback functional interface
        custIDTableCol.setCellValueFactory(cellData -> cellData.getValue().customerIDProperty().asObject());
        custNameTableCol.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        phoneTableCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        addressTableCol.setCellValueFactory(cellData -> Bindings.concat(cellData.getValue().address1Property(),
                                            "\n",
                                            cellData.getValue().address2Property()));
        cityAndPCTableCol.setCellValueFactory(cellData -> Bindings.concat(cellData.getValue().cityProperty(),
                                            "\n",
                                            cellData.getValue().postalCodeProperty()));
        countryTableCol.setCellValueFactory(cellData -> cellData.getValue().countryProperty());
        customerTable.setItems(custDB.getCustomerList());
        
        FilteredList<Customer> filteredCustomerByName = new FilteredList<>(
                custDB.getCustomerList(), p -> true);
        
        customerSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomerByName.setPredicate(customer -> {
                if((newValue.toString().isEmpty()) || (newValue.toString().equals(null))) {
                    return true;
                }

                String lowerCaseFilter = newValue.toString().toLowerCase();

                if(customer.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        
        SortedList<Customer> sortedData = new SortedList<>(filteredCustomerByName);
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }
        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        
    }    

    @Override
    public void displayData(Customer selectedCust, Appointment appoinment) {        
        CustomerDB.getInstance().downloadCustDB();
        displayCustTable(CustomerDB.getInstance());
    }
    
}
