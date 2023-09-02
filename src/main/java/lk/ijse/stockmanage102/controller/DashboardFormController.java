package lk.ijse.stockmanage102.controller;

/*
    @author DanujaV
    @created 9/2/23 - 11:43 AM   
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        //open the Customer Manage form
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Customer Manage");
        stage.show();
    }
}
