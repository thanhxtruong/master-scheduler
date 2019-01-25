/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class CustomerListController implements Initializable {
    
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> custIDTableCol;

    @FXML
    private TableColumn<Customer, String> custNameTableCol;

    @FXML
    private TextField customerSearchText;

    @FXML
    private Button searchButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button selectButton;

    @FXML
    void handleCancelSelect(ActionEvent event) {

    }

    @FXML
    void handleSelectCust(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        // TODO
    }    
    
}
