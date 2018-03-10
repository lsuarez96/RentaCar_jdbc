/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsLayer;

/**
 *
 * @author Wiwi
 */
public class HourConverter {

    public static long toLong(String arg) {
        int hours, minutes, seconds;
        char[] s1 = arg.toCharArray();
        if (s1[1] == ':') {
            hours = Integer.parseInt(String.valueOf(s1[0]));
            minutes = Integer.parseInt(String.valueOf(s1[3])) + Integer.parseInt(String.valueOf(s1[2])) * 10;
            seconds = Integer.parseInt(String.valueOf(s1[6])) + Integer.parseInt(String.valueOf(s1[5])) * 10;
        } else {
            hours = Integer.parseInt(String.valueOf(s1[1])) + Integer.parseInt(String.valueOf(s1[0])) * 10;
            minutes = Integer.parseInt(String.valueOf(s1[4])) + Integer.parseInt(String.valueOf(s1[3])) * 10;
            seconds = Integer.parseInt(String.valueOf(s1[7])) + Integer.parseInt(String.valueOf(s1[6])) * 10;
        }
        return seconds * 1000 + minutes * 60000 + hours * 3600000 - 7200000;
    }

    public static String longToString(long arg) {
        long hours, minutes, seconds;
        arg += 7200000;
        hours = arg / 3600000;
        arg %= 3600000;
        minutes = arg / 60000;
        arg %= 60000;
        seconds = arg / 1000;
        String answ = "";
        if (hours < 10) {
            answ += "0" + String.valueOf(hours);
        } else {
            answ += String.valueOf(hours);
        }
        if (minutes < 10) {
            answ += ":0" + String.valueOf(minutes);
        } else {
            answ += ":" + String.valueOf(minutes);
        }
        if (seconds < 10) {
            answ += ":0" + String.valueOf(seconds);
        } else {
            answ += ":" + String.valueOf(seconds);
        }
        return answ;
    }
}
