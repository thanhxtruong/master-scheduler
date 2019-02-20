/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

/**
 *
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
