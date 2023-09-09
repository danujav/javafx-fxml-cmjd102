package lk.ijse.stockmanage102.controller;

/*
    @author DanujaV
    @created 9/2/23 - 1:27 PM   
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.stockmanage102.db.DbConnection;
import lk.ijse.stockmanage102.dto.Item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemFormController {
    public AnchorPane root;
    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQuantityOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQuantityOnHand.getText());

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO item VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, code);
            pstm.setString(2, description);
            pstm.setDouble(3, unitPrice);
            pstm.setInt(4, qtyOnHand);

            boolean isSaved = pstm.executeUpdate() > 0;
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "item saved!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void txtCodeOnAction(ActionEvent event) {
        String code = txtCode.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM item WHERE code = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, code);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()) {
                String itemCode = resultSet.getString(1);
                String itemDescription = resultSet.getString(2);
                double itemUnitPrice = resultSet.getDouble(3);
                int itemQtyOnHand = resultSet.getInt(4);

                //since JDK11
                var item = new Item(itemCode, itemDescription, itemUnitPrice, itemQtyOnHand);

                setFields(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFields(Item item) {
        txtCode.setText(item.getCode());
        txtDescription.setText(item.getDescription());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQuantityOnHand.setText(String.valueOf(item.getQtyOnHand()));

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(scene);

        stage.setTitle("Dashboard");
    }
}
