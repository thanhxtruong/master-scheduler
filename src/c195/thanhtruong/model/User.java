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
public class User extends AbstractModel {
    private int userId;
    private String userName;
    private String userPassword;

    public User(int userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }
    
    public User() {
        this(0, null, null);
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public int getUserId() {
        return userId;
    }   
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }   
       
}
