/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.model;

/**
 *
 * @author thanhtruong
 */
public class DataInput {
    private boolean missingInput;

    public boolean isMissingInput() {
        return missingInput;
    }
    
    public void checkMissingInput(String ...s) {
        for (String i:s) {
            if (i == null || i.length() == 0) {
                missingInput = true;
                throw new NullPointerException("Missing input!");
            } else {
                missingInput = false;
            }
        }
    }
}
