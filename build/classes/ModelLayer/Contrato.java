/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelLayer;

import java.sql.Date;

/**
 *
 * @author Luisito
 */
public class Contrato {

    private int idCont;
    private String plate;
    private String idTurist;
    private String idDriver;
    private Date startDate;
    private Date endDate;
    private Date deliveryDate;
    private String payment;
    private String totalImport;

    public Contrato(int idCont, String plate, String idTurist, String idDriver, Date startDate, Date endDate, Date deliveryDate, String payment, String totalImport) {
        this.idCont = idCont;
        this.plate = plate;
        this.idTurist = idTurist;
        this.idDriver = idDriver;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDate = deliveryDate;
        this.payment = payment;
        this.totalImport = totalImport;
    }

    public Contrato(String plate, String idTurist, Date startDate, Date endDate, String idDriver, String payment) {
        this.plate = plate;
        this.idTurist = idTurist;
        this.idDriver = idDriver;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDate = null;
        this.payment = payment;

    }

    public Contrato(String plate, String idTurist, Date startDate, Date endDate, String idDriver, Date deliveryDate, String payment, String totalImport) {
        this.plate = plate;
        this.idTurist = idTurist;
        this.idDriver = idDriver;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryDate = deliveryDate;
        this.payment = payment;
        this.totalImport = totalImport;
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
     * @return the idTurist
     */
    public String getIdTurist() {
        return idTurist;
    }

    /**
     * @param idTurist the idTurist to set
     */
    public void setIdTurist(String idTurist) {
        this.idTurist = idTurist;
    }

    /**
     * @return the idDriver
     */
    public String getIdDriver() {
        if (idDriver == null) {
            idDriver = "NO";
        }
        return idDriver;

    }

    /**
     * @param idDriver the idDriver to set
     */
    public void setIdDriver(String idDriver) {
        this.idDriver = idDriver;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the deliveryDate
     */
    public Date getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * @param deliveryDate the deliveryDate to set
     */
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayment() {

        return payment;

    }

    /**
     * @return the totalImport
     */
    public String getTotalImport() {
        return totalImport;

    }

    /**
     * @param totalImport the totalImport to set
     */
    public void setTotalImport(String totalImport) {
        this.totalImport = totalImport;
    }

    public String getStartDateAsString() {

        return startDate.toLocalDate().toString();
    }

    public String getEndDateAsString() {
        return endDate.toLocalDate().toString();

    }

    public String getDeliveryDateAsString() {
        @SuppressWarnings("UnusedAssignment")
        String dat = "";
        try {
            dat = deliveryDate.toLocalDate().toString();
        } catch (Exception e) {
            dat = "-";
        }
        return dat;
    }

    /**
     * @return the idCont
     */
    public int getIdCont() {
        return idCont;
    }

    /**
     * @param idCont the idCont to set
     */
    public void setIdCont(int idCont) {
        this.idCont = idCont;
    }

}
