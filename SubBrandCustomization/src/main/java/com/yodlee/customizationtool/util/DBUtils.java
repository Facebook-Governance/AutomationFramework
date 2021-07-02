/**
* Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of
* Yodlee, Inc. Use is subject to license terms.
*
* @author Rajeev Anantharaman Iyer
*/
package com.yodlee.customizationtool.util;

import java.sql.*;

public class DBUtils {

    public static Connection connection =null;

    public static Connection createDBConnection(String serverName, String serverPort,
                                                String serviceName, String username,
                                                String password){
        try {
            // Load the Oracle JDBC driver
            String driverName = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driverName);

            // Create a connection to the database
            String url = "jdbc:oracle:thin:@" + serverName + ":" + serverPort + "/" + serviceName;
            connection = DriverManager.getConnection(url, username, password);

            if(connection!=null)
                System.out.println("Successfully Connected to the database!");
            else
                System.out.println("Connection Failed. Please Check.");

        } catch (ClassNotFoundException e) {
            System.out.println("Could not find the database driver " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Could not connect to the database " + e.getMessage());
        }

        return connection;
    }

    public static String getCobrandPassword(Connection connection, String query) throws SQLException {

        String cobrandPassword = "";
        if(connection!=null){

            Statement stat = connection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                cobrandPassword = rs.getString("COB_PASSWORD");
            }
        }
        return cobrandPassword;
    }

    public static void closeDBConnection(Connection connection){
        try {
            if(connection!=null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getDecryptedPassword(String serverName, String serverPort,
                                              String serviceName, String username,
                                              String password, String query) throws SQLException {

        createDBConnection(serverName, serverPort, serviceName, username, password);
        String cobPassword = getCobrandPassword(connection, query);
        //System.out.println("COB_PASSWORD: "+ cobPassword);
        closeDBConnection(connection);
        String decryptedPassword = GenericUtils.decryptCobrandCredentials(cobPassword);
        //System.out.println("DECRYPTED PASSWORD: "+decryptedPassword);
        return decryptedPassword;
    }

    public static void main(String[] args) throws SQLException {

        String serverName = "192.168.211.218";
        String serverPort = "1521";
        String serviceName = "iodaqa01";
        String username = "sdp_owner";
        String password = "sdp_owner";
        String query = "select cob_password from SDP_SUBBRAND where name='QAAutoSub1574236006161' and Is_Private='1'";

        createDBConnection(serverName, serverPort, serviceName, username, password);
        String cobPassword = getCobrandPassword(connection, query);
        System.out.println("COB_PASSWORD: "+ cobPassword);
        closeDBConnection(connection);
        String decryptedPassword = GenericUtils.decryptCobrandCredentials(cobPassword);
        System.out.println("DECRYPTED PASSWORD: "+decryptedPassword);
    }

}
