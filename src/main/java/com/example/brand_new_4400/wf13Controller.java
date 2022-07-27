package com.example.brand_new_4400;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.brand_new_4400.HelloApplication.connection;
import static com.example.brand_new_4400.HelloApplication.currentUser;
import static java.lang.Integer.parseInt;

public class wf13Controller implements Initializable {
    public TableView<Wf13_tuple> wf13_table;
    public HBox types_hbox;
    public VBox update_list;
    public TextField name_input;
    public TableColumn<Wf13_tuple, String> name_col;
    public TableColumn<Wf13_tuple, String> types_col;
    public TableColumn<Wf13_tuple, String> units_col;
    public TableColumn<Wf13_tuple, Integer> amount_owned_col;
    public Group update_group;
    public HBox update_row;
    public TableColumn<Wf13_tuple, Group> update_group_col;
    public Button go_back;
    public ComboBox<String> product_selection;
    public TextField amount_input;
    public Label error_area;
    ObservableList<Wf13_tuple> list_OL = FXCollections.observableArrayList();
    public String typesConcatQuery = "";
    public String typesConcatTable = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wf13_table.setEditable(true);
        amount_owned_col.setCellValueFactory(new PropertyValueFactory<>("amount_owned_wf13"));
        units_col.setCellValueFactory(new PropertyValueFactory<>("units_wf13"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name_wf13"));
        types_col.setCellValueFactory(new PropertyValueFactory<>("types_wf13"));
        update_group_col.setCellValueFactory(new PropertyValueFactory<>("update_group_wf13"));

        ArrayList<String> product_list = new ArrayList<String>();
        String testStatement = "SELECT Pname FROM PRODUCT";
        Statement bruh = null;
        try {
            bruh = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet test = null;
        try {
            assert bruh != null;
            test = bruh.executeQuery(testStatement);
            while (test.next()) {
                product_list.add(test.getString("Pname"));
            }
            ObservableList<String> listOfProducts_OL = FXCollections.observableArrayList(product_list);
            product_selection.setItems(listOfProducts_OL);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            re_query_table_wf13();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update_filters(KeyEvent actionEvent) throws SQLException {
        typesConcatQuery = "";
        typesConcatTable = "";


        for(int i = 0; i < types_hbox.getChildren().size() - 1; i++) {
            ((TextField)types_hbox.getChildren().get(i)).setEditable(true);
        }

        for(int i = 0; i < types_hbox.getChildren().size() - 1; i++) {
            Node item = types_hbox.getChildren().get(types_hbox.getChildren().size() - 1);
            if(!(((TextField)(item)).getText().isEmpty())) {
                ((TextField)types_hbox.getChildren().get(i)).setEditable(false);
            }
        }

        int countDT = 0;
        for(Node item : types_hbox.getChildren()) {
            String currText = ((TextField) item).getText();
            if(countDT == 0 && !currText.isEmpty()) {
                typesConcatQuery += " AND (" + "\"" + currText + "\"" + ") IN (SELECT IType FROM ITYPE WHERE P.Pname = ITYPE.Pname)";
            }
            if(!currText.isEmpty() && countDT > 0) {
                typesConcatQuery += " AND (" + "\"" + currText + "\"" + ") IN (SELECT IType FROM ITYPE WHERE P.Pname = ITYPE.Pname)";
            }
            typesConcatTable += currText + ", ";
            countDT++;
        }
        if(!name_input.getText().isEmpty()) {
            typesConcatQuery += " AND P.Pname LIKE " + "\"" + name_input.getText() + "%\"";
        }
        re_query_table_wf13();
    }

    public void add_type(ActionEvent actionEvent) {
        TextField newInput = new TextField();
        types_hbox.getChildren().add(newInput);
    }



    private void re_query_table_wf13() throws SQLException {
        list_OL.clear();
        String initialQuery = "SELECT P.Pname, Units, Amount FROM PRODUCT P JOIN HC_OWNS_PRODUCT U ON P.Pname = U.Pname JOIN USERS ON U.Email = USERS.Email " +
                "WHERE USERS.Email = " + "\"" +  HelloApplication.currentUser.email + "\"" + typesConcatQuery;
        ResultSet rs = connection.createStatement().executeQuery(initialQuery);

        while(rs.next()) {
            String typesToTable = "";
            String innerTQuery  = "(SELECT IType FROM ITYPE WHERE " + "\"" +  rs.getString(1) + "\"" + " = ITYPE.Pname)";
            ResultSet Tset = connection.createStatement().executeQuery(innerTQuery);
            while (Tset.next()) {
                typesToTable += Tset.getString(1) + " ";
            }
            HBox updateRow = new HBox();
            TextField input = new TextField();

            Button add = new Button();
            add.setText("+");
            Button remove = new Button();
            remove.setText("-");
            updateRow.getChildren().addAll(input, add,remove);
            String currProduct = rs.getString(1);

            add.setOnAction(e -> {
                try {
                    addProduct(parseInt(input.getText()), currProduct);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            remove.setOnAction(e -> {
                try {
                    removeProduct(parseInt(input.getText()), currProduct);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            Group finalRow = new Group();
            finalRow.getChildren().add(updateRow);

            list_OL.add(new Wf13_tuple(rs.getString(1),typesToTable, rs.getString(2), rs.getInt(3), finalRow));
        }
        wf13_table.setItems(list_OL);
    }

    private void removeProduct(int parseInt, String productName) throws SQLException {

        String query = "UPDATE HC_OWNS_PRODUCT SET Amount = Amount - ? WHERE Pname = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, parseInt);
        ps.setString(2,productName);

        ps.executeUpdate();

        String zeroCheck = "SELECT Amount FROM HC_OWNS_PRODUCT WHERE Pname = " + "\"" + productName + "\"";
        ResultSet rs = connection.createStatement().executeQuery(zeroCheck);
        while (rs.next()) {
            if(rs.getInt(1) == 0) {
                String delete = "DELETE FROM HC_OWNS_PRODUCT WHERE Pname = " + "\"" + productName + "\"";
                connection.createStatement().executeUpdate(delete);
            }
        }
        re_query_table_wf13();
    }

    private void addProduct(int parseInt, String productName) throws SQLException {

        String query = "UPDATE HC_OWNS_PRODUCT SET Amount = Amount + ? WHERE Pname = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, parseInt);
        ps.setString(2,productName);

        ps.executeUpdate();

        ResultSet rs = connection.createStatement().executeQuery("SELECT Pname, Amount FROM HC_OWNS_PRODUCT");

        re_query_table_wf13();
    }

    public void save_product(ActionEvent actionEvent) {
    }

    public void add_to_product_table(ActionEvent actionEvent) throws SQLException {

        String query = "INSERT INTO HC_OWNS_PRODUCT VALUES(?,?,?)";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, currentUser.email);
        ps.setString(2, product_selection.getValue());
        ps.setInt(3, parseInt(amount_input.getText()));
        try {
            ps.executeUpdate();
            amount_input.clear();
            re_query_table_wf13();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
          error_area.setText("You already own that product!");
        }



    }


    public void go_to_wf12(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf12.fxml");
    }

    public void go_to_wf6(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf6.fxml");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
