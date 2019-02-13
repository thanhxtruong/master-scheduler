/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.ApptTypeCount;
import c195.thanhtruong.model.Customer;
import c195.thanhtruong.model.ReportSaver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * FXML Controller class
 *
 * @author TTruong
 */
public class ApptTypeByMonthReportController extends AbstractController implements Initializable {

    @FXML
    private TableView<ApptTypeCount> apptTable;
    @FXML
    private TableColumn<ApptTypeCount, String> apptTypeCol;
    @FXML
    private TableColumn<ApptTypeCount, Integer> totalApptCol;
    @FXML
    private Button closeButton;
     @FXML
    private Button saveBut;
    
    private Map<String, Map<Integer, Map<Integer, ObservableList<Integer>>>> apptTypeByMonth = new TreeMap<>();
    private ObservableList<ApptTypeCount> apptCount = FXCollections.observableArrayList();

    @FXML
    void handleClose(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("Home.fxml")
                .setTitle("Home")
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void saveReport(ActionEvent event) {
        File file = ReportSaver.prepareFile(getDialogStage());
        if (file != null) {
            saveFile(file);
        }
    }
    
    private void saveFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for(ApptTypeCount apptType:apptCount) {
                writer.write(apptType.getApptType() + ", ");
                writer.write(apptType.getCount() + "\r\n");
            }
        } catch(IOException ex) {
            System.out.println("Unable to Save file");
            ex.printStackTrace();
        }
    }
    
    private void mapApptByType() {
        AppointmentDB apptDB = AppointmentDB.getInstance();
        apptDB.downloadAppt(null);
        
        DateTimeFormatter moYrf = DateTimeFormatter.ofPattern("MMM yyyy");
        String typeKey;
        Integer yrKey;
        Integer moKey;
        for (Appointment appt:AppointmentDB.getInstance().getAllApptList()) {
            typeKey = appt.typeProperty().get();
            yrKey = appt.dateProperty().getValue().getYear();
            moKey = appt.dateProperty().getValue().getMonthValue();
            if (!apptTypeByMonth.containsKey(typeKey)) {
                apptTypeByMonth.put(typeKey, new TreeMap<>());
            }
            if (!apptTypeByMonth.get(typeKey).containsKey(yrKey)) {
                apptTypeByMonth.get(typeKey).put(yrKey, new TreeMap<>());
            }
            if (!apptTypeByMonth.get(typeKey).get(yrKey).containsKey(moKey)) {
                apptTypeByMonth.get(typeKey).get(yrKey).put(moKey, FXCollections.observableArrayList());
            }
            apptTypeByMonth.get(typeKey).get(yrKey).get(moKey).add(appt.getAppointmentId());
        }
        
        for (String key1:apptTypeByMonth.keySet()) {
            ApptTypeCount apptTypeCount = new ApptTypeCount(key1, apptTypeByMonth.get(key1).size());
            apptCount.add(apptTypeCount);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @Override
    public void displayData(Customer selectedCust,
            Appointment appointment) {
        mapApptByType();
        apptTypeCol.setCellValueFactory(cellData -> cellData.getValue().apptTypeProperty());
        totalApptCol.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        apptTable.setItems(apptCount);
    }
    
}
