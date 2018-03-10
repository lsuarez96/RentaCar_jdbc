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
public class Pais {

    private int id;
    private String country;

    public Pais(int id, String country) {
        this.id = id;
        this.country = country;
    }

    public Pais(String country) {
        this.country = country;
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

    @Override
    public String toString() {
        return country;
    }

    public boolean equals(Pais p) {
        return this.country.equals(p.country);
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
