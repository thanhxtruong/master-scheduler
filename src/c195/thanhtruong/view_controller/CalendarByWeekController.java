
package c195.thanhtruong.view_controller;

import c195.thanhtruong.model.Appointment;
import c195.thanhtruong.model.AppointmentDB;
import c195.thanhtruong.model.ApptCboOptions;
import c195.thanhtruong.model.CalendarByWeek;
import c195.thanhtruong.model.CalendarPaneHeight;
import c195.thanhtruong.model.Customer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.WEEK_OF_YEAR;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author thanhtruong
 */
public class CalendarByWeekController extends AbstractController implements Initializable {
    
    @FXML
    private GridPane container;
    
    @FXML
    private AnchorPane CalByWeek;

    @FXML
    private Button previousWeekBut;

    @FXML
    private Label currentWeekLabel;

    @FXML
    private Button nextWeekBut;

    @FXML
    private Button newApptBut;

    @FXML
    private Button editApptBut;

    @FXML
    private Button deleteApptBut;

    @FXML
    private Button apptDetailBut;

    @FXML
    private Button closeCalBut;
    
    private Customer selectedCust;
    private AppointmentDB apptDB;private ObservableList<Appointment> sortedApptList;
    private Map<Integer, Map<Integer, ObservableList<Appointment>>> apptMapByWeek;
    private Map<Integer, ObservableList<Appointment>> apptInWeek = new TreeMap<>();
    private static final Calendar currentCalDate = Calendar.getInstance();
    private ObservableList<Appointment> apptByDate = FXCollections.observableArrayList();
    private static AnchorPane aPaneSize = new AnchorPane();
    
    @FXML
    void handleCloseCal(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("Home.fxml")
                .setTitle("Home")
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleDeleteAppt(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("ApptList.fxml")
                .setTitle("Appointment List")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleEditAppt(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("ApptList.fxml")
                .setTitle("Appointment List")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleNewAppt(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("AddAppointment.fxml")
                .setTitle("Add Appointment")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }

    @FXML
    void handleNextWeek(ActionEvent event) {
        CalendarByWeek.getNextWeek(currentCalDate);
        clearAllDays();
        displayAllLabels(currentCalDate);
        displayAllAppt(currentCalDate);
    }

    @FXML
    void handlePreviousWeek(ActionEvent event) {
        CalendarByWeek.getPreviousWeek(currentCalDate);
        clearAllDays();
        displayAllLabels(currentCalDate);
        displayAllAppt(currentCalDate);
    }

    @FXML
    void handleSeeDetails(ActionEvent event) {
        getDialogStage().close();
        WindowsDisplay windowDisplay = new WindowsBuilder()
                .setFXMLPath("ApptList.fxml")
                .setTitle("Appointment List")
                .setCustomer(selectedCust)
                .build();
        windowDisplay.displayScene();
    }
    
    /**
     * Populate all labels for Calendar view by Week.
     * The first column is populated with time (0 - 23), and the first row is
     * populated with dates in the week. The remaining grids are filled with
     * empty AnchorPanes.
     * 
     * @param currentDate any date in the week
     */
    private void displayAllLabels(Calendar currentDate) {     
        // Set currennt date to the first date of the week        
        Calendar tempCalDate = (Calendar)currentDate.clone();
        tempCalDate.set(DAY_OF_WEEK, currentDate.getFirstDayOfWeek());
        
        currentWeekLabel.setText(CalendarByWeek.getDateAsString(currentDate));
        
        // Start with Row #1 since the first row prints the dates in week
        int rowNo = 1;
        int colNo;        
        
        Insets labelPadding = new Insets(0, 0, 0, 5);
        container.setAlignment(Pos.TOP_CENTER);
        // Populate the time column (Col #0)
        for (Integer key:ApptCboOptions.getInstance().getHourMap().keySet()) {
            AnchorPane anchorPane = new AnchorPane();
            container.add(anchorPane, 0, rowNo, 1, 1);
            Label hrLabel = new Label(key.toString());
            hrLabel.setFont(new Font(18));
            hrLabel.setPadding(labelPadding);
            hrLabel.setAlignment(Pos.TOP_CENTER);
            hrLabel.setStyle("-fx-background-color: #cce6ff");
            anchorPane.getChildren().add(hrLabel);
            AnchorPane.setTopAnchor(hrLabel, 0.0);
            AnchorPane.setBottomAnchor(hrLabel, 0.0);
            AnchorPane.setLeftAnchor(hrLabel, 0.0);
            AnchorPane.setRightAnchor(hrLabel, 0.0);
            rowNo++;
        }
        
        // Populate the dates columns
        // Set calculateSize to true to signal the need to set aPaneSize to one
        // of the emptyPane, which will be used later on after the scene is loaded
        // to calculate the size of the empty AnchorPane. The size of the empty 
        // AnchorPane is later used to calculate top and bottom AnchorPane alignment
        // for appointment display.
        boolean calculateSize = true;
        for (colNo=1; colNo<8; colNo++) {
            // Display dates in week in top row
            AnchorPane anchorPane = new AnchorPane();
            container.add(anchorPane, colNo, 0, 1, 1);
            Date currentDDate = tempCalDate.getTime();
            Label dLabel = new Label(new SimpleDateFormat("d").format(currentDDate));
            dLabel.setFont(new Font(18));
            dLabel.setPadding(labelPadding);
            dLabel.setAlignment(Pos.TOP_LEFT);
            dLabel.setStyle("-fx-background-color: #cce6ff");
            anchorPane.getChildren().add(dLabel);
            AnchorPane.setTopAnchor(dLabel, 0.0);
            AnchorPane.setBottomAnchor(dLabel, 0.0);
            AnchorPane.setLeftAnchor(dLabel, 0.0);
            AnchorPane.setRightAnchor(dLabel, 0.0);
            
            // Add empty AnchorPane to all grid cells
            for (rowNo=1; rowNo < 25; rowNo++) {
                AnchorPane emptyPane = new AnchorPane();
                emptyPane.setStyle("-fx-border-color: #c0c0c0");
                container.add(emptyPane, colNo, rowNo, 1, 1);
                while(calculateSize) {
                    aPaneSize = emptyPane;
                    calculateSize = false;
                }
            }
            
            tempCalDate.add(DAY_OF_WEEK, 1);
        }
    }
    
    /**
     * Print all appointments in the week.
     * First, the list of all appointments by Customer is downloaded from the
     * MySQL DB before sorted and mapped by week and dates in week.
     * For each appointment, a new AnchorPane is added for the purpose of setting
     * row spanning. The top and bottom anchors for the new AnchorPane is set
     * based on the Start and End minutes of the appointment.
     * @param currentDate any dates in the week
     */
    public void displayAllAppt(Calendar currentDate) {
        sortedApptList = apptDB.sortApptByWeek();
        apptMapByWeek = apptDB.getApptMapByWeek(sortedApptList);        
        apptInWeek = apptDB.checkApptByWeek(currentDate.get(WEEK_OF_YEAR), apptMapByWeek);
        
        // Start with the second row since the first row is printed with all dates in week
        int rowNo = 1;
        int colNo;
        Insets labelPadding = new Insets(0, 0, 0, 5);
        
        Calendar tempCalDate2 = (Calendar)currentDate.clone();
        tempCalDate2.set(DAY_OF_WEEK, currentDate.getFirstDayOfWeek());
        
        for (colNo=1; colNo<8; colNo++) {            
            // Add appointments            
            if(apptInWeek != null) {
                apptByDate = apptDB.matchApptDatesInWeek(apptInWeek, tempCalDate2.get(DAY_OF_MONTH));
                if (apptByDate != null) {
                        for (Appointment appt:apptByDate) {
                        AnchorPane apptPane = new AnchorPane();
                        int startHr = appt.getStartDateTime().toLocalDateTime().getHour();
                        int endHr = appt.getEndDateTime().toLocalDateTime().getHour();
                        int startMin = appt.getStartDateTime().toLocalDateTime().getMinute();
                        int endMin = appt.getEndDateTime().toLocalDateTime().getMinute();
                        double topAnchor, botAnchor;
                        int rSpan;
                        // Calculate the number of rows to span across based on 
                        // the number of hours for the appointment
                        if (endHr > startHr && endMin != 0) {
                            rSpan = endHr - startHr + 1;
                        } else if (endHr > startHr && endMin == 0) {
                            rSpan = endHr - startHr;
                        } else {
                            rSpan = 1;
                        }
                        container.add(apptPane, colNo, startHr+1, 1, rSpan);
                        String apptDetails = appt.getStartDateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH : mm")) +
                                " - " + appt.getEndDateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH : mm")) + 
                                "\n" + appt.getTitle() + "\n" + appt.getLocation();
                        Label apptLabel = new Label(apptDetails);
                        apptLabel.setWrapText(true);
                        apptLabel.setFont(new Font(16));
                        apptLabel.setPadding(labelPadding);
                        apptLabel.setAlignment(Pos.TOP_LEFT);
                        apptLabel.setStyle("-fx-background-color: #cce6ff");
                        Tooltip lbTooltip = new Tooltip("Click for more details!");
                        apptLabel.setTooltip(lbTooltip);
                        apptPane.getChildren().add(apptLabel);
                        // Show tooltip as soon as mouse entered the text area
                        apptLabel.setOnMouseEntered((MouseEvent event) -> {
                            lbTooltip.show(apptLabel, event.getScreenX(), event.getScreenY() + 15);
                        });
                        apptLabel.setOnMouseExited((MouseEvent event) -> {
                            lbTooltip.hide();
                        });
                        apptLabel.setOnMouseClicked((MouseEvent event) -> {
                            if(event.getClickCount() == 2) {
                                DialogPopup.showAlert(getDialogStage(),
                                        "Details", "Appointment Details",
                                        apptDetails, AlertType.INFORMATION);
                            }
                        });
                        // Calculate top and bottom anchor based on the start
                        // and end minutes of the appointment
                        topAnchor = CalendarPaneHeight.getInstance().getPaneHeight()*(((double)startMin)/60.0);
                        if (endMin == 0){
                            botAnchor = 0;
                        } else {
                            botAnchor = CalendarPaneHeight.getInstance().getPaneHeight() - CalendarPaneHeight.getInstance().getPaneHeight()*(((double)endMin)/60.0);
                        }
                        
                        AnchorPane.setTopAnchor(apptLabel, topAnchor);
                        AnchorPane.setBottomAnchor(apptLabel, botAnchor);
                        AnchorPane.setLeftAnchor(apptLabel, 0.0);
                        AnchorPane.setRightAnchor(apptLabel, 0.0);
                    }
                }
                
            }
            tempCalDate2.add(DAY_OF_WEEK, 1);
        }
    }
    
    private void clearAllDays() {
        container.getChildren().clear();
    }
    
    /*
    This method is called as soon as the scene is loaded in order to calculate
    the height of the AnchorPane (see displayAllAppt() method above)
    */
    public void calculateHeight() {
        CalendarPaneHeight calPaneHeight = CalendarPaneHeight.getInstance();
        calPaneHeight.setPaneHeight(aPaneSize.getHeight());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url,
            ResourceBundle rb) {
            
    }

    @Override
    public void displayData(Customer selectedCust, Appointment appointment) {
        this.selectedCust = selectedCust;
        
        apptDB = AppointmentDB.getInstance();
        apptDB.downloadAppt(selectedCust);
                
        displayAllLabels(currentCalDate);
        
    }
        
}
        

