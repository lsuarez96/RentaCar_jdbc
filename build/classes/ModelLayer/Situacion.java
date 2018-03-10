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
public class Situacion {

    private int id;
    private String state;

    public Situacion(int id, String state) {
        this.id = id;
        this.state = state;
    }

    public Situacion(String state) {
        super();
        this.state = state;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }

    /**
     *
     * @param s
     * @return
     */
    public boolean equals(Situacion s) {
        return this.state.equals(s.state);
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
