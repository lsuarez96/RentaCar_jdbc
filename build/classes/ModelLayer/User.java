/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer;

/**
 *
 * @author Luisito Suarez
 */
public class User {

    private int id;
    private String name, lastName;
    private String userName;
    private String pass;

    public User(int id, String name, String lastName, String userName, String pass) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.pass = pass;
    }

    public User(String name, String lastName, String userName, String pass) {

        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.pass = pass;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return userName;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
