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
