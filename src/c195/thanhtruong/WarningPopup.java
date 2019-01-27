/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author thanhtruong
 */
public class WarningPopup {
    
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
    
}
