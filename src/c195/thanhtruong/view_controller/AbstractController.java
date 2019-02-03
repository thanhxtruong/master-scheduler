/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.MainApp;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.CustomerDB;
import java.io.InputStream;
import java.util.function.Function;
import javafx.event.Event;
import javafx.event.EventHandler;
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
public abstract class AbstractController extends ControllerFactory {
    private MainApp mainApp;
    private Stage dialogStage;
    private Customer selectedCust;
    final InputStream logoStream = getClass().getResourceAsStream("logo.png");
    
    public abstract void displayCustData(Customer selectedCust);

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

    public InputStream getLogoStream() {
        return logoStream;
    }
    
    public void setExitConfirmation() {
        /*
        Set exit confirmation for the modal Window
        The setOnCloseRequest() method of the Window class, which take an event
        as an argument is used.
        This method is called when there is an external request to close the Window.
        The installed handler will prevent window closing by consuming the received event,
        i.e. invoke the exitConfirmation() method.
        An instance of the EventHandler functional interface is created for the event.
        */
        EventHandler event = new EventHandler() {
            @Override
            public void handle(Event event) {
                event.consume();
                DialogPopup.exitConfirmation(dialogStage);
            }
            
        };        
        
        dialogStage.setOnCloseRequest(event);
//        dialogStage.setOnCloseRequest(evt -> {
//                evt.consume();
//                DialogPopup.exitConfirmation(dialogStage);
//        });
        
    }
        
}
