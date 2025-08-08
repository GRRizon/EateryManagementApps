package eaterymanagementapps;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductController {

    @FXML
    private TableView<?> tvStudents; // Replace ? with your Product model
    @FXML
    private TableColumn<?, ?> colID;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colDept;
    @FXML
    private TableColumn<?, ?> colID1;
    @FXML
    private TextField tfID;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfDept;
    @FXML
    private TextField tfDept1;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @FXML
    private void handleButtonAction(javafx.event.ActionEvent event) {
        Object source = event.getSource();
        if (source == btnInsert) {
            System.out.println("Insert clicked");
        } else if (source == btnUpdate) {
            System.out.println("Update clicked");
        } else if (source == btnDelete) {
            System.out.println("Delete clicked");
        }
    }

    @FXML
    private void onMouseClick() {
        System.out.println("Table row clicked");
    }
}
