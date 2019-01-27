/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong;

import javafx.stage.Stage;

/**
 * The AbstractController class is an abstract class extended by all controllers
 * in this program. Its sole purpose is to receive the instances of the mainApp
 * and Stage from main for use in several methods defined in the controller.
 * A getter is also declared in order to pass the "received" Stage to other methods.
 * 
 * @param mainApp An instance of the mainApp
 * @param dialogStage The active stage passed by mainApp
 * 
 * @author thanhtruong
 */
public abstract class AbstractController {
    private MainApp mainApp;
    private Stage dialogStage;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }    

    public Stage getDialogStage() {
        return dialogStage;
    }

    public MainApp getMainApp() {
        return mainApp;
    }  
        
}
