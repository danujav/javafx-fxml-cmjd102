package lk.ijse.stockmanage102.controller;

/*
    @author DanujaV
    @created 9/2/23 - 11:43 AM   
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    public AnchorPane rootNode;
    public AnchorPane node;

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        //open the Customer Manage form
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Customer Manage");
        stage.show();
    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        //open the Item Manage Form to the Dashboard stage
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Item Manage");
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));

        this.node.getChildren().clear();
        this.node.getChildren().add(root);

    }
}
