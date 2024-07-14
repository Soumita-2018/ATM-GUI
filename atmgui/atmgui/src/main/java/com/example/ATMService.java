package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATMService {
    private String currentUserPin;

    public boolean validatePIN(String pin) {
        String query = "SELECT * FROM USERS WHERE PIN = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, pin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                currentUserPin = pin;
                return true;
            }
        } catch (SQLException e) {
            System.out.println("The problem is "+e);
        }
        return false;
    }

    public double checkBalance() {
        String query = "SELECT BALANCE FROM USERS WHERE PIN = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, currentUserPin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
        	System.out.println("The problem is "+e);
        }
        return 0.0;
    }

    public void deposit(double amount) {
        String query = "UPDATE USERS SET BALANCE = BALANCE + ? WHERE PIN = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, currentUserPin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	System.out.println("The problem is "+e);
        }
    }

    public boolean withdraw(double amount) {
        double currentBalance = checkBalance();
        if (currentBalance >= amount) {
            String query = "UPDATE USERS SET BALANCE = BALANCE - ? WHERE PIN = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setDouble(1, amount);
                pstmt.setString(2, currentUserPin);
                pstmt.executeUpdate();
                return true;
            } catch (SQLException e) {
            	System.out.println("The problem is "+e);
            }
        }
        return false;
    }
}

