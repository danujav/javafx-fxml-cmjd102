package lk.ijse.stockmanage102.controller;

/*
    @author DanujaV
    @created 9/2/23 - 3:22 PM   
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.stockmanage102.db.Db;

import java.io.IOException;

public class LoginFormController {
    public TextField txtUser;
    public TextField txtPw;
    public AnchorPane root;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String user = txtUser.getText();
        String pw = txtPw.getText();

        if(user.equals(Db.user) && pw.equals(Db.pw)) {
            navigate();
        } else {
            new Alert(Alert.AlertType.ERROR, "Credential Invalid").show();
        }
    }

    public  void navigate() throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(root);
        Stage stage = (Stage) this.root.getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle("Dash");
        stage.centerOnScreen();
    }
}
