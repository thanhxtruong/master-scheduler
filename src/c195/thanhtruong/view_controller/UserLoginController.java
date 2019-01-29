/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.service.DBConnection;
import c195.thanhtruong.service.Query;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class UserLoginController extends AbstractController implements Initializable {
    
    @FXML
    private Label userNameLabel;

    @FXML
    private Label pwLabel;

    @FXML
    private Label headerLabel;
    
    @FXML
    private TextField username;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;
    
    private ResourceBundle rb;
    
    public void showHome(){
        WindowsDisplay.displayScene("Home.fxml", "Home");        
    }

    @FXML
    void handleLogin(ActionEvent event) {
        try {
            // Connect to the DB
            DBConnection.makeConnection();
            
            String sqlStatement = "SELECT userName, password FROM user "
                                + "WHERE userName = '" + username.getText() + "' "
                                + "AND password = '" + password.getText() +"'";            
            Query.makeQuery(sqlStatement);
            ResultSet result = Query.getResult();            
            
            if (!result.isBeforeFirst()) {
                WarningPopup.showAlert(getDialogStage(),
                                        "Authentication Failure",
                                        "Username and password do not match!",
                                        "Please, login again!");
            } else {
                showHome();
            }
            DBConnection.closeConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
        this.rb = rb;
        headerLabel.setText(rb.getString("header"));
        userNameLabel.setText(rb.getString("usernameLabel"));
        username.setPromptText("prompt");
        pwLabel.setText(rb.getString("pwLabel"));
        loginButton.setText(rb.getString("button"));
    }    
    
}
