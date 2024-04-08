package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

public class UserInfo {
    @Expose
    public int id;

    @Expose
    public String firstname,lastname,email;

    public String password;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstname;
    }
    public void setFirstName(String name) {
        this.firstname = name;
    }
    public String getLastName() {
        return lastname;
    }
    public void setLastName(String name) {
        this.lastname = name;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


}
