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
public class Categorias {

    private int idCat;
    private String category;

    public Categorias(String category) {
        this.category = category;
    }

    public Categorias(int idCat, String category) {
        this.idCat = idCat;
        this.category = category;
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

    @Override
    public String toString() {
        return category;
    }

    public boolean equals(Categorias c) {
        try {
            return category.equals(c.category);
        } catch (NullPointerException e) {
            return category == null;
        }
    }

    /**
     * @return the idCat
     */
    public int getIdCat() {
        return idCat;
    }

    /**
     * @param idCat the idCat to set
     */
    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }
}
