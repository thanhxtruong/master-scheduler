
package c195.thanhtruong.service;

import c195.thanhtruong.model.AppointmentDB;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Task to check for appointments coming up in 15 minutes.
 * Task is scheduled to run every 5 minutes.
 * @author thanhtruong
 */
public class ApptAlertService extends Service<LocalDateTime> {

    @Override
    protected Task<LocalDateTime> createTask() {
        
        return new Task<LocalDateTime>() {
            
            @Override
            protected LocalDateTime call() throws Exception {
                
                if (AppointmentDB.getInstance().getApptListByUser() != null) {
                    int sleepMin = 5;
                    LocalDateTime startTime = LocalDateTime.now(ZoneId.systemDefault());
                    LocalDateTime endTime = startTime.plusMinutes(5);

                    while (endTime.isAfter(LocalDateTime.now(ZoneId.systemDefault()))) {
                        try {
                            Thread.sleep(1000 * 60 * sleepMin);
                        } catch (InterruptedException e) {

                            if (isCancelled()) {
                                updateMessage("Cancelled");
                                break;
                            }
                        }
                    }
                    System.out.println("Service ended");
                    return endTime;
                }
                
                return null;
                
            }
            
        };
    }
    
}
