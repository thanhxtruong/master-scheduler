/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.CustomerDB;
import c195.thanhtruong.service.ActivityLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import static javafx.scene.input.KeyCode.T;
import javafx.stage.Stage;

/**
 *
 * @author thanhtruong
 */
public class DialogPopup {
    
    /*
    This alert simply displays an ERROR type alert and returns to the alert owner
    stage when user clicks "OK" or close the alert window.
    */
    public static void showAlert(Stage dialogStage,
                                String title,
                                String headerText,
                                String errorMessage,
                                AlertType alertType) {
        Alert alert = null;
        switch (alertType) {
            case ERROR:
                alert = new Alert(Alert.AlertType.ERROR);
            case INFORMATION:
                alert = new Alert(Alert.AlertType.INFORMATION);
        }
        alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
    
    /*
    This alert displays a CONFIRMATIOn type alert.
    Close the alert window to return to the cuurentStage when user clicks "NO" or
    close the currentStage and return true, which can be used as a signal to display
    another Window outside of this function if the currentStage is non-modal.
    */
    public static boolean exitConfirmation(Stage currentStage) {
        Alert exitConfirmation = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure you want to exit?",
                                ButtonType.YES, ButtonType.NO);
        exitConfirmation.setHeaderText("Confirm Exit");
        if(exitConfirmation.showAndWait().orElse(ButtonType.NO) == ButtonType.YES){
            if (Thread.currentThread().getStackTrace()[2].getClassName().equals("c195.thanhtruong.view_controller.HomeController")) {
                ActivityLogger.logActivities(MainApp.getCurrentUser().getUserName() + " logged out");
            }
            currentStage.close();
            return true;
        } else {
            exitConfirmation.close();
            return false;
        }
    }
    
    public static Customer selectCustomer() {
        List<String> custList = new ArrayList<>();
        Customer selectedCust;
        
        CustomerDB.getInstance().downloadCustDB();
        custList = CustomerDB.getInstance().getCustListAsString();
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, custList);
        dialog.setTitle("Customer Selection");
        dialog.setHeaderText("Please, select a customer to view appointments!");
        dialog.setContentText("Select a customer:");
        
        Optional<String> selectedCustName = dialog.showAndWait();
        if (selectedCustName.isPresent()) {
            selectedCust = CustomerDB.getInstance().getCustByName(selectedCustName.get());
        } else {
            selectedCust = null;
        }
        return selectedCust;
    }
    
    public static String selectReport() {
        // Add all report options here
        List<String> reportList = new ArrayList<>();
        reportList.add("Total Appointment by Type and Month");
        reportList.add("All Appointments");
        reportList.add("All Customers");
        
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, reportList);
        dialog.setTitle("Report Selection");
        dialog.setHeaderText("Please, select a report from the drop-down!");
        dialog.setContentText("Select a report:");
        
        Optional<String> selectedReport = dialog.showAndWait();
        if (selectedReport.isPresent()) {
            return selectedReport.get();
        } else {
            return null;
        }
    }
}
