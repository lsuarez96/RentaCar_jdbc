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
public class Auto {

    private int idCar;
    private String plate;
    private String situation;
    private String model;
    private String brand;
    private String color;
    private float km;

    public Auto(int idCar, String plate, String situation, String model, String brand, String color, float km) {
        this.idCar = idCar;
        this.plate = plate;
        this.situation = situation;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.km = km;
    }

    public Auto(String plate, String situation, String model, String brand, String color, float km) {
        this.plate = plate;
        this.situation = situation;
        this.model = model;
        this.brand = brand;
        this.color = color;
        this.km = km;
    }

    /**
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * @return the situation
     */
    public String getSituation() {
        return situation;
    }

    /**
     * @param situation the situation to set
     */
    public void setSituation(String situation) {
        this.situation = situation;
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

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the km
     */
    public float getKm() {
        return km;
    }

    /**
     * @param km the km to set
     */
    public void setKm(float km) {
        this.km = km;
    }

    @Override
    public String toString() {
        return "chap: " + plate + " /brand: " + brand + " /model: " + model + " /color: " + color;
    }

    public boolean equals(Auto a) {
        try {
            return this.plate.equals(a.plate);
        } catch (NullPointerException e) {
            return plate == null;
        }
    }

    /**
     * @return the idCar
     */
    public int getIdCar() {
        return idCar;
    }

    /**
     * @param idCar the idCar to set
     */
    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }
}
