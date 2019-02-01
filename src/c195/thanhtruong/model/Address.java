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
public class Address {
    int addressId;
    String address1;
    String address2;
    int cityId;
    String postalCode;
    String phone;

    public Address(int addressId,
            String address1,
            int cityId,
            String postalCode,
            String phone) {
        this.addressId = addressId;
        this.address1 = address1;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public int getCityId() {
        return cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    } 
        
}
