package service;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

public class ConnectionDemo {

    public static Connection con;
    public static Connection IntializeDatabase() {
        String userName = "root";
        String  password = "harshil";
        String url = "jdbc:mariadb://localhost:3306/registration";
        String driver = "org.mariadb.jdbc.Driver";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection is successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;

    }

}

//
