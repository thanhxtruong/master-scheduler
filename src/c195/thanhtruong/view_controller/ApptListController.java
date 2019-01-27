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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class ApptListController extends AbstractController implements Initializable {

    @FXML
    private TableView<?> ApptTable;
    @FXML
    private TableColumn<?, ?> apptTitleCol;
    @FXML
    private TableColumn<?, ?> apptDescCol;
    @FXML
    private TableColumn<?, ?> apptLocationCol;
    @FXML
    private TableColumn<?, ?> apptTypeCol;
    @FXML
    private TableColumn<?, ?> apptDateCol;
    @FXML
    private TableColumn<?, ?> apptTimeCol;
    @FXML
    private TextField apptSearchText;
    @FXML
    private Button searchButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Button closeButton;
    
    @FXML
    private void handleDeleteCust(ActionEvent event) {
    }

    @FXML
    private void handleModifyCust(ActionEvent event) {
    }

    @FXML
    private void handleClose(ActionEvent event) {
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
