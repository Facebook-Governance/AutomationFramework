/**
 * Copyright (c) 2018 Yodlee Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Yodlee, Inc. Use is subject to license terms.
 *
 * @author Rajeev Anantharaman Iyer
 */
package com.yodlee.customizationtool;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sample {

    /*public static final String DBURL = "jdbc:oracle:thin:@blr-oda2-scan:1521/INDQA41";
    public static final String DBUSER = "auth_owner";
    public static final String DBPASS = "auth_owner";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Load Oracle JDBC Driver
        //DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Class.forName("oracle.jdbc.driver.OracleDriver");

        // Connect to Oracle Database
        Connection con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

        Statement statement = con.createStatement();

        // Execute a SELECT query on Oracle Dummy DUAL Table. Useful for retrieving system values
        // Enables us to retrieve values as if querying from a table
        *//*ResultSet rs = statement.executeQuery("select COBRAND_ID from COBRAND where CHANNEL_ID=10040051");


        if (rs.next()) {
            String cobrandId = rs.getString(1);
            System.out.println("Cobrand ID(s) are  : "+cobrandId);
        }
        rs.close();*//*
        statement.close();
        con.close();
    }*/
}
