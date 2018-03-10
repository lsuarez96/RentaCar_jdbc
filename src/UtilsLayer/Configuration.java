/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsLayer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

/**
 *
 * @author Luisito Suarez
 */
public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dataBaseAddress;
    private String user;
    private String password;
    private static Configuration configurationInstance;

    public Configuration(String dataBaseAddress, String user, String password) {
        this.dataBaseAddress = dataBaseAddress;
        this.user = user;
        this.password = password;
    }

    /**
     * @return the configurationInstance
     * @throws UtilsLayer.ConnectionFailureException
     * @throws UtilsLayer.ConfigurationFileException
     * @throws java.lang.Exception
     */
    public static Configuration getConfigurationInstance() throws ConnectionFailureException, ConfigurationFileException {
        if (configurationInstance == null) {
            configurationInstance = readConfiguration();

        }
        return configurationInstance;
    }

    private static Configuration readConfiguration() throws ConfigurationFileException {
        File file = new File("ClientDataBaseInformation\\config_conection.cfg");
        if (!file.exists()) {
            throw new ConfigurationFileException();
        }
        Configuration read;
        try {
            try (RandomAccessFile fileReader = new RandomAccessFile(file, "rw")) {
                while (fileReader.getFilePointer() < fileReader.length()) {
                    Long cantBytes = fileReader.readLong();
                    byte[] byteArray = new byte[cantBytes.intValue()];
                    fileReader.read(byteArray);
                    read = (Configuration) DataConvert.toObject(byteArray);
                    if (read != null) {
                        return read;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("At reading file " + ex.getMessage());
        }

        return null;
    }

    /**
     *
     * @param c
     */
    public static void writeConfiguration(Configuration c) {
        try {
            File file = new File("ClientDataBaseInformation\\config_conection.cfg");
            if (!file.exists()) {
                new File("ClientDataBaseInformation").mkdirs();
            } else {
                System.out.println("deleting file");
                System.out.println(file.delete());
                new File("ClientDataBaseInformation").mkdirs();
                file = new File("ClientDataBaseInformation\\config_conection.cfg");
            }

            try (RandomAccessFile fileWriter = new RandomAccessFile(file, "rw")) {
                byte[] wr = DataConvert.toBytes(c);
                fileWriter.writeLong(wr.length);
                fileWriter.write(wr);
                fileWriter.close();
            }

        } catch (IOException ex) {
            System.out.println("At saving" + ex.getMessage());

        }

    }

    /**
     * @return the dataBaseAddress
     */
    public String getDataBaseAddress() {
        return dataBaseAddress;
    }

    /**
     * @param dataBaseAddress the dataBaseAddress to set
     */
    public void setDataBaseAddress(String dataBaseAddress) {
        this.dataBaseAddress = dataBaseAddress;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
