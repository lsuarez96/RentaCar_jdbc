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
public class Turista {

    private int id;
    private String idPassport;
    private String name;
    private String lastName;
    private String phone;
    private String sex;
    private String country;
    private int age;

    public Turista(int id, String idPassport, String name, String lastName, String phone, String sex, String country, int age) {
        this.id = id;
        this.idPassport = idPassport;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.sex = sex;
        this.country = country;
        this.age = age;
    }

    public Turista(String idPassport, String name, String lastName, int age, String phone, String sex, String country) {
        this.idPassport = idPassport;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.sex = sex;
        this.country = country;
        this.age = age;
    }

    /**
     * @return the idPassport
     */
    public String getIdPassport() {
        return idPassport;
    }

    /**
     * @param idPassport the idPassport to set
     */
    public void setIdPassport(String idPassport) {
        this.idPassport = idPassport;
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
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return idPassport + "/" + name;
    }

    public boolean equals(Turista t) {
        try {
            return this.idPassport.equals(t.idPassport);
        } catch (NullPointerException e) {
            return idPassport == null;
        }
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
