
package c195.thanhtruong.model;

/**
 * Factory pattern used to support polymorphism in the downloadAppt() method 
 * (AbstractModel parameter) of the AppointmentDB class.
 * @param model
 * @return AbstractModel
 */
public class ModelFactory {
    public static AbstractModel getModelClass(AbstractModel model) {
        switch (model.getClass().getSimpleName()) {
            case "Customer":
                return (Customer)model;
            case "User":
                return (User)model;
        }
        throw new UnsupportedOperationException("Unsupported data type: " + model);
    }
}
