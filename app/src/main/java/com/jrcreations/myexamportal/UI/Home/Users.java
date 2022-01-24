package com.jrcreations.myexamportal.UI.Home;

public class Users {
    public String username;
    public String id;
    public String email;
    public String phone;
    public String dob;

    public Users() {

    }

    public Users(String username, String id, String email, String phone,String dob) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.dob=dob;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
