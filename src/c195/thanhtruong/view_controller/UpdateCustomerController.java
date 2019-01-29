/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class UpdateCustomerController extends AbstractController implements Initializable {

    @FXML
    private Label customerName;
    @FXML
    private Label address1;
    @FXML
    private Label address2;
    @FXML
    private Label city;
    @FXML
    private Label postalCode;
    @FXML
    private Label country;
    @FXML
    private Label phoneNumber;
    @FXML
    private Button addCustomerCancel;
    @FXML
    private Button addCustomerSave;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleCancelAddCust(ActionEvent event) {
    }

    @FXML
    private void handleSaveAddCust(ActionEvent event) {
    }
    
}
