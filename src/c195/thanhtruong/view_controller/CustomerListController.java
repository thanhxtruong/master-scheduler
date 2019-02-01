/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.CustomerDB;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
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
        WindowsDisplay.displayModalWindow("AddCustomer.fxml",
                                        "Add Customer",
                                        MainApp.getPrimaryStage()); 
    }

    @FXML
    void handleClose(ActionEvent event) {

    }

    @FXML
    void handleDeleteCust(ActionEvent event) {
        
    }

    @FXML
    void handleModifyCust(ActionEvent event) {
        Customer selectedCust = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCust != null) {
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(WindowsDisplay.class.getResource("UpdateCustomer.fxml"));
                InputStream logoStream = WindowsDisplay.class.getClassLoader().getResourceAsStream("resources/images/logo.png");
                root = loader.load();

                // Create a new Stage
                Stage newStage = new Stage();            
                newStage.setTitle("Edit Customer");
                newStage.getIcons().add(new Image(logoStream));
                newStage.initModality(Modality.WINDOW_MODAL);
                newStage.initOwner(getDialogStage());            

                Scene scene = new Scene(root);
                newStage.setScene(scene);                        

                UpdateCustomerController controller = loader.getController();
                controller.setDialogStage(newStage);
                controller.setExitConfirmation();
                controller.displayCustData(selectedCust);

                newStage.showAndWait();

            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            WarningPopup.showAlert(getDialogStage(), "Attention",
                    "No customer selected!", "Please, select a customer to modify!");
        }
    }
    
    private void displayCustTable(CustomerDB custDB) {
        custIDTableCol.setCellValueFactory(cellData -> cellData.getValue().customerID().asObject());
        custNameTableCol.setCellValueFactory(cellData -> cellData.getValue().customerName());
        phoneTableCol.setCellValueFactory(cellData -> cellData.getValue().phone());
        addressTableCol.setCellValueFactory(cellData -> Bindings.concat(cellData.getValue().address1(),
                                            "\n",
                                            cellData.getValue().address2()));
        cityAndPCTableCol.setCellValueFactory(cellData -> Bindings.concat(cellData.getValue().city(),
                                            "\n",
                                            cellData.getValue().postalCode()));
        countryTableCol.setCellValueFactory(cellData -> cellData.getValue().country());
        customerTable.setItems(custDB.getCustomerList());
        
        Predicate p = new Predicate() {
            @Override
            public boolean test(Object t) {
                return true;
            }
            
        };
        
        FilteredList<Customer> filteredCustomerByName = new FilteredList<>(custDB.getCustomerList(), p);
        
        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable,
                    Object oldValue,
                    Object newValue) {                
        
                Predicate<Customer> p2 = new Predicate<Customer>() {
                    @Override
                    public boolean test(Customer customer) {
                        if((newValue.toString().isEmpty()) || (newValue.toString().equals(null))) {
                            return true;
                        }
                        
                        String lowerCaseFilter = newValue.toString().toLowerCase();
                        
                        if(customer.getCustomerName().toLowerCase().contains(lowerCaseFilter)) {
                            return true;
                        }
                        return false;
                    }
                };
                
                filteredCustomerByName.setPredicate(p2);
            }
        };
        
        customerSearchText.textProperty().addListener(listener);
        
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
        
        CustomerDB custDB = new CustomerDB();
        custDB.downloadCustDB();
        displayCustTable(custDB);
        
    }    
    
}
