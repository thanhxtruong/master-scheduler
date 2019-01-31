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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private ComboBox<String> countryCbo;

    @FXML
    private ComboBox<String> cityCbo;

    @FXML
    private Button cancelUpdateButton;

    @FXML
    private Button saveUpdateButton;

    @FXML
    void handleCancelUpdate(ActionEvent event) {

    }

    @FXML
    void handleSaveUpdate(ActionEvent event) {
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        
    }
    
}
