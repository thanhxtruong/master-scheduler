/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class UserLoginController implements Initializable {
    
    @FXML
    private TextField username;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;
    
    private MainApp mainApp;
    private Stage dialogStage;        
    
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
    
    /**
     * Sets the stage of the "alert.initOwner(dialogStage)". Called by MainApp
     * @param dialogStage 
     */
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    @FXML
    void handleLogin(ActionEvent event) {

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
