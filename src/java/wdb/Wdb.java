/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Morar
 */
public final class Wdb {

    // ovo treba prilagoditi
    private static final String USER_NAME = "root";
    private static final String USER_PWD = "JDK013";
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "wstore"; // ovo treba da bude dostupno kao izbor
    
    private Wdb() {
    }
    
    public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());// zbog ove linije koda(dok nisam pronasao resenje na stackoverflow)
        // izgubio sam pola dana. 
        
        // novi test(zbog unregister)
        Class.forName("com.mysql.jdbc.Driver"); // radi i ovako
        Connection conn = null;
        String url = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
        //System.out.println(url + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        try {
            conn = DriverManager.getConnection(url, USER_NAME, USER_PWD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            //System.out.println("Nema konekcije >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            return conn;
        }
        //System.out.println("IMA KONEKCIJU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return conn;
    }
    
    
}
