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
public class FormaPago {

    private int idFp;
    private String payType;

    public FormaPago(int idFp, String payType) {
        this.idFp = idFp;
        this.payType = payType;
    }

    public FormaPago(String payType) {
        this.payType = payType;
    }

    /**
     * @return the payType
     */
    public String getPayType() {
        return payType;
    }

    /**
     * @param payType the payType to set
     */
    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return payType;
    }
}
