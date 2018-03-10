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
public class Modelo {

    private int id;
    private String model;
    private String brand;
    private String normalTarif;
    private String speciallTarif;

    public Modelo(int id, String model, String brand, String normalTarif, String speciallTarif) {
        this.id = id;
        this.model = model;
        this.brand = brand;
        this.normalTarif = normalTarif;
        this.speciallTarif = speciallTarif;
    }

    public Modelo(String idModel, String brandId, String normalTarif, String speciallTarif) {
        this.model = idModel;
        this.brand = (brandId);
        this.normalTarif = normalTarif;
        this.speciallTarif = speciallTarif;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return model + "/" + brand;
    }

    /**
     *
     * @param m
     * @return
     */
    public Boolean equals(Modelo m) {
        try {
            try {
                return this.model.equals(m.model) && this.brand.equals(m.brand);
            } catch (NullPointerException e) {
                return this.model == null;
            }
        } catch (NullPointerException e) {
            return true;
        }
    }

    /**
     * @return the normalTarif
     */
    public String getNormalTarif() {
        return normalTarif;
    }

    /**
     * @param normalTarif the normalTarif to set
     */
    public void setNormalTarif(String normalTarif) {
        this.normalTarif = normalTarif;
    }

    /**
     * @return the speciallTarif
     */
    public String getSpeciallTarif() {
        return speciallTarif;
    }

    /**
     * @param speciallTarif the speciallTarif to set
     */
    public void setSpeciallTarif(String speciallTarif) {
        this.speciallTarif = speciallTarif;
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
