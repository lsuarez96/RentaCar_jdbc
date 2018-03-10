/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author Luisito Suarez
 */
public class InsertionModificationException extends Exception {

    public InsertionModificationException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
