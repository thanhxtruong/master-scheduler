/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.CustomerDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class HomeController extends AbstractController implements Initializable {
    
    @FXML
    private Button custModule;

    @FXML
    private Button calendarModule;

    @FXML
    private Button reportMenu;

    @FXML
    private Button closeHome;
    
    @FXML
    void handleCalModButton(ActionEvent event) {

    }

    @FXML
    void handleCloseHome(ActionEvent event) {

    }

    @FXML
    void handleCustModButton(ActionEvent event) {
        WindowsDisplay.displayScene("CustomerList.fxml", "Customer Maintenance");
    }

    @FXML
    void handleReportMenu(ActionEvent event) {

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
