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
import java.sql.ResultSet;
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
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM customer WHERE id=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()) {
                String txtId = resultSet.getString(1);
                String txtName = resultSet.getString(2);
                String txtAddress = resultSet.getString(3);
                String txtTel = resultSet.getString(4);

                setFields(txtId, txtName, txtAddress, txtTel);
            } else {
                new Alert(Alert.AlertType.WARNING, "customer not found!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(String txtId, String txtName, String txtAddress, String txtTel) {
        this.txtId.setText(txtId);
        this.txtName.setText(txtName);
        this.txtAddress.setText(txtAddress);
        this.txtTel.setText(txtTel);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "UPDATE customer SET name = ?, address = ?, tel = ? WHERE id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, address);
            pstm.setString(3, tel);
            pstm.setString(4, id);

            boolean isUpdated = pstm.executeUpdate() > 0;
            if(isUpdated) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "DELETE FROM customer WHERE id = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, id);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();
        }
    }
}
