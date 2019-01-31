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
public class User {
    private String userName;
    private String userPassword;

    public User(String userName,
            String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }
    
    public User() {
        this(null, null);
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
   
}
