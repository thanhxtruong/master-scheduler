
package c195.thanhtruong.model;

/**
 * Singleton pattern used by CalendarByWeekController to calculate the Height
 * of the empty AnchorPanes added to the calendar grids, which is later used to
 * set top and bottom anchors for appointment display.
 * Since the height needs to be calculated once as soon as the scene is loaded,
 * Singleton Design Pattern is used for this class.
 * @author TTruong
 */
public class CalendarPaneHeight {
    private double paneHeight;
    private static final CalendarPaneHeight instance;
    static {
        instance = new CalendarPaneHeight();
    }
    private CalendarPaneHeight() {}

    public static CalendarPaneHeight getInstance() {
        return instance;
    }

    public void setPaneHeight(double paneHeight) {
        this.paneHeight = paneHeight;
    }

    public double getPaneHeight() {
        return paneHeight;
    }
    
}
