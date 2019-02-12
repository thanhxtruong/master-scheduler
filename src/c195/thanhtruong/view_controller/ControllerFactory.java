/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195.thanhtruong.view_controller;

import c195.thanhtruong.view_controller.AbstractController;
import c195.thanhtruong.view_controller.AddAppointmentController;
import c195.thanhtruong.view_controller.AddCustomerController;
import c195.thanhtruong.view_controller.ApptListController;
import c195.thanhtruong.view_controller.CalendarByCustController;
import c195.thanhtruong.view_controller.CustomerListController;
import c195.thanhtruong.view_controller.EditAppointmentController;
import c195.thanhtruong.view_controller.HomeController;
import c195.thanhtruong.view_controller.UpdateCustomerController;
import c195.thanhtruong.view_controller.UserLoginController;

/**
 *
 * @author thanhtruong
 */
public class ControllerFactory {
    public static AbstractController getControllerClass(AbstractController controller) {
        switch (controller.getClass().getSimpleName()) {
            case "UserLoginController":
                return (UserLoginController)controller;
            case "HomeController":
                return new HomeController();
            case "CustomerListController":
                return new CustomerListController();
            case "AddCustomerController":
                return new AddCustomerController();
            case "UpdateCustomerController":
                return new UpdateCustomerController();
            case "AddAppointmentController":
                return new AddAppointmentController();
            case "ApptListController":
                return new ApptListController();
            case "EditAppointmentController":
                return new EditAppointmentController();
            case "CalendarByCustController":
                return new CalendarByCustController();
        }
        throw new UnsupportedOperationException("Unsupported data type: " + controller);
    }
}
