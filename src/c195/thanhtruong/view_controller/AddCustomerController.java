/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.AbstractController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    private TextField city;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField country;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button addCustomerCancel;

      @FXML
    private Button addCustomerButton;

    @FXML
    void handleAddCust(ActionEvent event) {

    }

    @FXML
    void handleCancelAddCust(ActionEvent event) {

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
