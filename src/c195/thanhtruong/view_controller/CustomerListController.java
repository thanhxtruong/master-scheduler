/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.AbstractController;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.CustomerDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    }

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
