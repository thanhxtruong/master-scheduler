/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author thanhtruong
 */
public class WarningPopup {
    
    /*
    This alert simply displays an ERROR type alert and returns to the alert owner
    stage when user clicks "OK" or close the alert window.
    */
    public static void showAlert(Stage dialogStage,
                                String title,
                                String headerText,
                                String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
            System.out.println("newStage from exitConfirmation is " + currentStage);
            currentStage.close();
            return true;
        } else {
            exitConfirmation.close();
            return false;
        }
    }
}
