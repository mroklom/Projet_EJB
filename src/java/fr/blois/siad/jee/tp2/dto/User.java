package fr.blois.siad.jee.tp2.dto;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    private Integer id;
    private String email;
    private String password;
    private String name;
    private Date registrationDate;
    private boolean locked;

    public User() {
    }

    public User(Integer id, String email, String password, String name, Date registrationDate, boolean locked) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.registrationDate = registrationDate;
        this.locked = locked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", password=" + password + ", name=" + name + ", registrationDate=" + registrationDate + ", locked=" + locked + '}';
    }
}
