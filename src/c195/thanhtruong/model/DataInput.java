
package c195.thanhtruong.model;

/**
 * Check for missing user input(s).
 * The caller can access the returned boolean isMissingInput via getter.
 * @author TTruong
 */
public class DataInput {
    private boolean missingInput;

    public boolean isMissingInput() {
        return missingInput;
    }
    
    /**
     * This method check for missing user inputs.
     * The NullPointerException is thrown if there is a missing user input. The
     * exception needs to be caught and resolved by the caller.
     * @param s array of inputs to check (varargs)
     */
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
