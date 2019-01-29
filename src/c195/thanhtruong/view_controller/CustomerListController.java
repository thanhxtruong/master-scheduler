/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.CustomerDB;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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
        showAddCustScreen();
    }

    @FXML
    void handleClose(ActionEvent event) {

    }

    @FXML
    void handleDeleteCust(ActionEvent event) {

    }

    @FXML
    void handleModifyCust(ActionEvent event) {

    }
    
    private void showAddCustScreen() {
        WindowsDisplay.displayScene("AddCustomer.fxml", "Add Customer");
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
    
//    private boolean isInputValid(){
//        String errorMessage = "";
//        
//        if(partID.getText() == null || partID.getText().length() == 0){
//            errorMessage += "No valid Part ID!\n";
//        } else {
//            try {
//                Integer.parseInt(partID.getText());
//            } catch(NumberFormatException e) {
//                errorMessage += "No valid Part ID (must be an integer)!\n";
//            }
//        }
//        if(partName.getText() == null || partName.getText().length() == 0){
//            errorMessage += "No valid Part Name!\n";
//        }
//        
//        if(partInvMin.getText() == null || partInvMin.getText().length() == 0){
//            errorMessage += "No valid Min Part Inventory!\n";
//        } else {
//            try {
//                minInvCheck = Integer.parseInt(partInvMin.getText());
//            } catch(NumberFormatException e) {
//                errorMessage += "No valid Min Part Inventory (must be a number)!\n";
//            }
//        }
//        if(partInvMax.getText() == null || partInvMax.getText().length() == 0){
//            errorMessage += "No valid Max Part Inventory!\n";
//        } else {
//            try {
//                maxInvCheck = Integer.parseInt(partInvMax.getText());
//            } catch(NumberFormatException e) {
//                errorMessage += "No valid Max Part Inventory (must be a number)!\n";
//            }
//        }
//        if(partInv.getText() == null || partInv.getText().length() == 0){
//            errorMessage += "No valid Part Inventory!\n";
//        } else {
//            try {
//                inputInventory = Integer.parseInt(partInv.getText());
//            } catch(NumberFormatException e) {
//                errorMessage += "No valid Part Inventory (must be a number)!\n";
//            } finally {
//                if(inputInventory < minInvCheck){
//                    errorMessage += "Inventory is less than minimum requirement! \n";
//                } else if (inputInventory > maxInvCheck) {
//                    errorMessage += "Inventory exceeds the maximum allowed! \n";
//                }
//            }                
//        }
//        if(partCost.getText() == null || partCost.getText().length() == 0){
//            errorMessage += "No valid Part Price/Cost!\n";
//        } else {
//            try {
//                Double.parseDouble(partCost.getText());
//            } catch(NumberFormatException e) {
//                errorMessage += "No valid Part Cost/Price (must be a number)!\n";
//            }
//        }
//        if(nameOrIDText.getText() == null || nameOrIDText.getText().length() == 0){
//            errorMessage += "No valid Machine ID/Customer Name!\n";
//        } else {
//            try {
//                if(inHouse.isSelected()){
//                    Integer.parseInt(nameOrIDText.getText());
//                }                
//            } catch(NumberFormatException e) {
//                errorMessage += "No valid Machine ID (must be a number)!\n";
//            }
//        }
//        
//        if(errorMessage.length() == 0){
//            return true;
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.initOwner(dialogStage);
//            alert.setTitle("Invalid Input");
//            alert.setHeaderText("Please correct invalid input");
//            alert.setContentText(errorMessage);
//            
//            alert.showAndWait();
//            
//            return false;
//        }
//    }
        

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        
        CustomerDB custDB = new CustomerDB();
        custDB.accessCustDB();
        displayCustTable(custDB);
        
    }    
    
}
