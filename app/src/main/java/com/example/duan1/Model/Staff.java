package com.example.duan1.Model;

public class Staff {
    private String nameStaff;
    private String idStaff;
    private Integer phone;
    private String address;

    public Staff(String nameStaff, String idStaff, Integer phone, String address) {
        this.nameStaff = nameStaff;
        this.idStaff = idStaff;
        this.phone = phone;
        this.address = address;
    }

    public String getNameStaff() {
        return nameStaff;
    }

    public void setNameStaff(String nameStaff) {
        this.nameStaff = nameStaff;
    }

    public String getIDStaff() {
        return idStaff;
    }

    public void setIDStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
