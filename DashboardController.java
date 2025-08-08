/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package eaterymanagementapps;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Button btnProduct;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up button actions
        btnProduct.setOnAction(this::openProductPage);
        btnOrder.setOnAction(this::openOrderPage);
        btnCustomer.setOnAction(this::openCustomerPage);
        btnLogout.setOnAction(this::logout);
    }

    // Open Product page
    private void openProductPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Product.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Product Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open Order page
    private void openOrderPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Order.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Order Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open Customer Profile page
    private void openCustomerPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Customer.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Customer Profile");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Logout and go back to Login page
    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login - Eatery Management System");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
