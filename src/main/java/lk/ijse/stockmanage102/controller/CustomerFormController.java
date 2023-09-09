package lk.ijse.stockmanage102.controller;

/*
    @author DanujaV
    @created 9/2/23 - 1:08 PM   
*/

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.stockmanage102.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerFormController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtTel;

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO customer VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);
            pstm.setString(2, name);
            pstm.setString(3, address);
            pstm.setString(4, tel);

            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }
}
