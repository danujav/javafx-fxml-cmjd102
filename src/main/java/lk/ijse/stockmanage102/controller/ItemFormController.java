package lk.ijse.stockmanage102.controller;

/*
    @author DanujaV
    @created 9/2/23 - 1:27 PM   
*/

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.stockmanage102.db.DbConnection;
import lk.ijse.stockmanage102.dto.Item;
import lk.ijse.stockmanage102.dto.SupplierDto;
import lk.ijse.stockmanage102.dto.tm.ItemTm;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemFormController {
    public AnchorPane root;
    public ComboBox cmbSupplierId;
    public TextField txtSupplierName;
    public TextField txtShop;
    public TextField txtSupplierTel;
    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQuantityOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ItemTm> tblItem;

    public void initialize() throws SQLException {
        System.out.println("Item Form Just Loaded!");

        List<SupplierDto> supplierDtos = loadAllSupplierIds();
        setSupplierIds(supplierDtos);

        setCellValueFactory();
        List<Item> itemList = loadAllItems();

        setTableData(itemList);
    }

    private void setSupplierIds(List<SupplierDto> supplierDtos) {
        ObservableList<String> obList = FXCollections.observableArrayList();

        for  (SupplierDto supplierDto : supplierDtos) {
            obList.add(supplierDto.getSupplierId());
        }

        cmbSupplierId.setItems(obList);
    }

    private List<SupplierDto> loadAllSupplierIds() {
        List<SupplierDto> supplierList = new ArrayList<>();

        try {
            Connection con = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM supplier";

            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();

            while(resultSet.next()) {
                supplierList.add(new SupplierDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return supplierList;
    }

    private void setTableData(List<Item> itemList) {
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();

        for(Item item : itemList) {
            var tm = new ItemTm(item.getCode(), item.getDescription(), item.getUnitPrice(), item.getQtyOnHand());
            obList.add(tm);
        }
        tblItem.setItems(obList);
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    private List<Item> loadAllItems() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item";
        Statement stm = con.createStatement();

        ResultSet resultSet = stm.executeQuery(sql);

        List<Item> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String description = resultSet.getString(2);
            double unitPrice = resultSet.getDouble(3);
            int qtyOnHand = resultSet.getInt(4);

            var item = new Item(code, description, unitPrice, qtyOnHand);
            itemList.add(item);
        }

        return itemList;
    }

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
            if (isSaved) {
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

            if (resultSet.next()) {
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
