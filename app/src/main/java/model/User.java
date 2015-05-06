package model;

import java.io.Serializable;

/**
 * Created by Emil Makovac on 15/03/2015.
 */
public class User implements Serializable{

    String id;
    String fName;
    String lName;
    String mail;
    String password;
    boolean isAdmin;

    public User() {
    }

    public User(String id, String fName, String lName, String mail, String pass, boolean isAdmin) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.mail = mail;
        this.password = pass;
        this.isAdmin = isAdmin;
    }

    public User(String fName, String lName, boolean isAdmin) {
        this.fName = fName;
        this.lName = lName;
        this.isAdmin = isAdmin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(String isAdmin) {
        if(isAdmin.equals("0")){
            this.isAdmin = false;
        } else {
            this.isAdmin = true;
        }
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getFullName() {
        return this.fName + " " + this.lName;
    }
}
