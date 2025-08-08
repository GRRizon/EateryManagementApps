package eaterymanagementapps;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField si_username;
    @FXML
    private PasswordField si_password;
    @FXML
    private Button si_login;
    @FXML
    private Hyperlink si_forgetpassword;
    @FXML
    private TextField su_username;
    @FXML
    private PasswordField su_password;
    @FXML
    private ComboBox<String> su_question;
    @FXML
    private TextField su_answer;
    @FXML
    private Button side_createbtn;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    // Default credentials
    private static final String DEFAULT_USERNAME = "Rabbani";
    private static final String DEFAULT_PASSWORD = "golam1234";
    
    // Connect to MySQL
    private Connection connectDB() {
        try {
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/eaterydb",
                "root",
                "golamrabbani"
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private boolean testDatabaseConnection() {
        try {
            Connection conn = connectDB();
            if (conn != null) {
                System.out.println("Database connection successful!");
                conn.close();
                return true;
            } else {
                System.err.println("Database connection failed!");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            return false;
        }
    }
    
    private void createDefaultUserIfNotExists() {
        String checkSql = "SELECT * FROM users WHERE username = ?";
        String insertSql = "INSERT INTO users (username, password, question, answer) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = connectDB();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            
            checkStmt.setString(1, DEFAULT_USERNAME);
            ResultSet rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                insertStmt.setString(1, DEFAULT_USERNAME);
                insertStmt.setString(2, DEFAULT_PASSWORD);
                insertStmt.setString(3, "What is your favorite food?");
                insertStmt.setString(4, "Pizza");
                insertStmt.executeUpdate();
                System.out.println("Default user created successfully");
            }
        } catch (SQLException e) {
            System.err.println("Error creating default user: " + e.getMessage());
        }
    }
    
    @FXML
    private void loginAccount(ActionEvent event) {
        String username = si_username.getText().isEmpty() ? DEFAULT_USERNAME : si_username.getText();
        String password = si_password.getText().isEmpty() ? DEFAULT_PASSWORD : si_password.getText();
        
        System.out.println("Attempting login with username: " + username);
        
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        connect = connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username);
            prepare.setString(2, password);
            result = prepare.executeQuery();
            
            if (result.next()) {
                System.out.println("Login successful! Loading dashboard...");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, " + username + "!");
                alert.showAndWait();
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashbord.fxml"));
                    System.out.println("FXML resource path: " + getClass().getResource("Dashbord.fxml"));
                    
                    if (loader.getLocation() == null) {
                        System.err.println("ERROR: Could not find Dashbord.fxml");
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("FXML Loading Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Could not find the dashboard file. Please check the file name and location.");
                        errorAlert.showAndWait();
                        return;
                    }
                    
                    Scene scene = new Scene(loader.load());
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Eatery Management System - Dashboard");
                    stage.show();
                    
                    System.out.println("Dashboard loaded successfully!");
                } catch (IOException e) {
                    System.err.println("ERROR loading dashboard: " + e.getMessage());
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Loading Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Could not load the dashboard: " + e.getMessage());
                    errorAlert.showAndWait();
                }
            } else {
                System.out.println("Login failed: Invalid credentials");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Database Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Database error: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
    
    @FXML
    private void signupAccount(ActionEvent event) {
        String sql = "INSERT INTO users (username, password, question, answer) VALUES (?, ?, ?, ?)";
        connect = connectDB();
        try {
            String checkUser = "SELECT * FROM users WHERE username = ?";
            prepare = connect.prepareStatement(checkUser);
            prepare.setString(1, su_username.getText());
            result = prepare.executeQuery();
            if (result.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign Up Failed");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists.");
                alert.showAndWait();
                return;
            }
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, su_username.getText());
            prepare.setString(2, su_password.getText());
            prepare.setString(3, su_question.getValue());
            prepare.setString(4, su_answer.getText());
            int rowsInserted = prepare.executeUpdate();
            if (rowsInserted > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sign Up Successful");
                alert.setHeaderText(null);
                alert.setContentText("Account created successfully!");
                alert.showAndWait();
                si_username.setText(su_username.getText());
                si_password.setText("");
                su_username.clear();
                su_password.clear();
                su_answer.clear();
                su_question.getSelectionModel().clearSelection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        su_question.getItems().addAll(
            "What is your pet's name?",
            "What is your favorite food?",
            "What is your birth city?"
        );
        si_login.setOnAction(this::loginAccount);
        side_createbtn.setOnAction(this::signupAccount);
        
        // Test database connection on startup
        if (!testDatabaseConnection()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not connect to the database. Please check your connection settings.");
            alert.showAndWait();
        }
        
        // Create default user if not exists
        createDefaultUserIfNotExists();
    }
}