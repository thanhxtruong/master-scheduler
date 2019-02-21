
package c195.thanhtruong.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is used by the ApptTypeByMonthReportController to calculate total
 * count of appointments by Type.
 * @author TTruong
 */
public class ApptTypeCount {
    
    private StringProperty apptType = new SimpleStringProperty();
    private IntegerProperty count = new SimpleIntegerProperty();

    public ApptTypeCount(String apptType, Integer count) {
        this.apptType.set(apptType);
        this.count.set(count);
    }

    public StringProperty apptTypeProperty() {
        return apptType;
    }

    public IntegerProperty countProperty() {
        return count;
    }
    
    public String getApptType() {
        return apptType.get();
    }
    
    public int getCount() {
        return count.get();
    }
}
