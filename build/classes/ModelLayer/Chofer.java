/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer;

/**
 *
 * @author Luisito
 */
public class Chofer {

    private int idChof;
    private String id;
    private String name;
    private String lastName;
    private String category;
    private String address;

    public Chofer(int idChof, String id, String name, String lastName, String category, String address) {
        this.idChof = idChof;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.category = category;
        this.address = address;
    }

    public Chofer(String id, String name, String lastName, String category, String address) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.category = category;
        this.address = address;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        return id + "/" + name;
    }

    public boolean equals(Chofer c) {
        try {
            try {
                return id.equals(c.id);
            } catch (NullPointerException e) {
                return id == null;
            }
        } catch (NullPointerException ex) {
            return this == null && c == null;
        }
    }

    /**
     * @return the idChof
     */
    public int getIdChof() {
        return idChof;
    }

    /**
     * @param idChof the idChof to set
     */
    public void setIdChof(int idChof) {
        this.idChof = idChof;
    }
}
