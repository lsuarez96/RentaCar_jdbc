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
public class Tarifas {

    private int id;
    private String normalPrice;
    private String especialPrice;

    public Tarifas(int id, String normalPrice, String especialPrice) {
        this.id = id;
        this.normalPrice = normalPrice;
        this.especialPrice = especialPrice;
    }

    public Tarifas(String normalPrice, String especialPrice) {
        this.normalPrice = normalPrice;
        this.especialPrice = especialPrice;
    }

    /**
     * @return the normalPrice
     */
    public String getNormalPrice() {
        return normalPrice;
    }

    /**
     * @param normalPrice the normalPrice to set
     */
    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    /**
     * @return the especialPrice
     */
    public String getEspecialPrice() {
        return especialPrice;
    }

    /**
     * @param especialPrice the especialPrice to set
     */
    public void setEspecialPrice(String especialPrice) {
        this.especialPrice = especialPrice;
    }

    @Override
    public String toString() {
        return String.valueOf(normalPrice) + "/" + String.valueOf(especialPrice);
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
