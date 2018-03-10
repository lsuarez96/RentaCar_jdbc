/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsLayer;

/**
 *
 * @author Luisito Suarez
 */
public class ConnectionFailureException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConnectionFailureException() {
        super("Failure at connection's configuration");
    }
}
