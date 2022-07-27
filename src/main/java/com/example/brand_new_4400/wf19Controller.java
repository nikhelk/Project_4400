package com.example.brand_new_4400;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.brand_new_4400.HelloApplication.*;
import static java.lang.Integer.parseInt;

public class wf19Controller implements Initializable {


    public TableView<MBSF_tuple> tv = new TableView<>();
    public TableView<PC_tuple> tv2 = new TableView<>();
    public Button go_back;
    public Label label_area;
    public Button add_meal;
    public ComboBox<String> meal_picker;
    public TableColumn date_col = new TableColumn<>("PrepareDate");
    public TableColumn recipe_col = new TableColumn<>("Recipe Name");

    public TableColumn check_box_col = new TableColumn<>("Check box");
    public TableColumn product_col = new TableColumn<>("Product");
    public TableColumn amount_col = new TableColumn<>("Amount");
    public java.sql.Date globalSelection;
    public Integer globalRecID;
    public ArrayList<java.sql.Date> dateList;
    public ArrayList<Integer> integerList;


    private final ObservableList<MBSF_tuple> list_OL_MBSF = FXCollections.observableArrayList();
    private final ObservableList<PC_tuple> list_OL_PC = FXCollections.observableArrayList();

    public AnchorPane pain19;
    public Label error_area;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date_col.setCellValueFactory(new PropertyValueFactory<>("prep"));
        recipe_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_col.setCellValueFactory(new PropertyValueFactory<>("product"));
        check_box_col.setCellValueFactory(new PropertyValueFactory<>("check"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateList = new ArrayList<>();
        integerList = new ArrayList<>();

        tv.getColumns().addAll(recipe_col,date_col);
        tv.setLayoutX(336);
        tv.setLayoutY(139);
        tv.setPrefHeight(200);
        tv.setPrefWidth(211);


        tv2.getColumns().addAll(check_box_col,product_col,amount_col);

        tv2.setLayoutX(81);
        tv2.setLayoutY(139);
        tv2.setPrefWidth(211);
        tv2.setPrefHeight(200);
        label_area.setText("Grocery Run - " + HelloApplication.currentGRDate.toString());
        String comboBoxQuery = "SELECT PrepareDate, MEAl.Rec_ID, RECIPE.Rec_Name FROM MEAL JOIN RECIPE " +
                "ON MEAL.Rec_ID = RECIPE.Rec_ID WHERE MEAL.Email = \"" + currentUser.email +"\"";
        try {
            ResultSet rs = connection.createStatement().executeQuery(comboBoxQuery);
            ObservableList<String> toComboBox = FXCollections.observableArrayList();
            while (rs.next()) {
                toComboBox.add(rs.getString(3) + " " + rs.getDate(1).toString());
                dateList.add(rs.getDate(1));
                integerList.add(rs.getInt(2));

            }
            meal_picker.setItems(toComboBox);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String fillMBSFQuery = "SELECT PrepareDate, RECIPE.REC_Name FROM GROCERY_RUN_SOURCES_A_MEAL JOIN RECIPE ON " +
                "GROCERY_RUN_SOURCES_A_MEAL.Rec_ID = RECIPE.Rec_ID WHERE GROCERY_RUN_SOURCES_A_MEAL.Email = \"" + currentUser.email +
                "\" AND GROCERY_RUN_SOURCES_A_MEAL.Run_ID = " + currentRunID;

        try {
            ResultSet rs = connection.createStatement().executeQuery(fillMBSFQuery);
            while (rs.next()) {
                list_OL_MBSF.add(new MBSF_tuple(rs.getString(2),rs.getString(1)));
            }

            tv.setItems(list_OL_MBSF);
            tv2.setItems(list_OL_PC);
            pain19.getChildren().add(tv);
            pain19.getChildren().add(tv2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            re_query_PC_table();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void re_query_MBSF_table() {
        String fillMBSFQuery = "SELECT PrepareDate, RECIPE.REC_Name FROM GROCERY_RUN_SOURCES_A_MEAL JOIN RECIPE ON " +
                "GROCERY_RUN_SOURCES_A_MEAL.Rec_ID = RECIPE.Rec_ID WHERE GROCERY_RUN_SOURCES_A_MEAL.Email = \"" + currentUser.email +
                "\" AND GROCERY_RUN_SOURCES_A_MEAL.Run_ID = " + currentRunID;
        list_OL_MBSF.clear();
        try {
            ResultSet rs = connection.createStatement().executeQuery(fillMBSFQuery);
            while (rs.next()) {
                list_OL_MBSF.add(new MBSF_tuple(rs.getString(2), rs.getString(1)));

            }
            re_query_PC_table();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void re_query_PC_table() throws SQLException {
        String differenceTable = "CREATE VIEW DIFF AS (SELECT DISTINCT R.Pname, R.Amount - H.Amount Amount FROM GROCERY_RUN_SOURCES_A_MEAL " +
                "JOIN RECIPE_USES_PRODUCT R ON GROCERY_RUN_SOURCES_A_MEAL.Rec_ID = R.Rec_ID JOIN HC_OWNS_PRODUCT H " +
                "ON R.Pname = H.Pname WHERE H.Email = \"" + currentUser.email +
                "\" AND R.Amount > H.Amount AND R.Rec_ID IN (SELECT GROCERY_RUN_SOURCES_A_MEAL.Rec_ID " +
                "FROM GROCERY_RUN_SOURCES_A_MEAL WHERE GROCERY_RUN_SOURCES_A_MEAL.Email = \"" + currentUser.email + "\" AND GROCERY_RUN_SOURCES_A_MEAL.Run_ID = " + currentRunID + "))";
        connection.createStatement().executeUpdate(differenceTable);
        String uniqueTable = "CREATE VIEW REQ AS (SELECT DISTINCT R.Pname, R.Amount " +
                "FROM RECIPE JOIN RECIPE_USES_PRODUCT R ON RECIPE.Rec_ID = R.Rec_ID " +
                "WHERE R.Pname NOT IN (SELECT DISTINCT H.Pname FROM " +
                "HC_OWNS_PRODUCT H WHERE H.Email = \"" + currentUser.email + "\") AND R.Rec_ID IN (SELECT GROCERY_RUN_SOURCES_A_MEAL.Rec_ID " +
                "FROM GROCERY_RUN_SOURCES_A_MEAL WHERE GROCERY_RUN_SOURCES_A_MEAL.Email = \"" + currentUser.email + "\" " +
                "AND GROCERY_RUN_SOURCES_A_MEAL.Run_ID = " + currentRunID + "))";
        connection.createStatement().executeUpdate(uniqueTable);
        String createPC = "CREATE VIEW PC AS (SELECT * FROM DIFF UNION SELECT * FROM REQ)";
        connection.createStatement().executeUpdate(createPC);


        String getStuff = "SELECT * FROM PC";

        ResultSet rs = connection.createStatement().executeQuery(getStuff);

        while (rs.next()) {
            CheckBox cb = new CheckBox();
            String currentProduct = rs.getString(1);
            Integer currentAmount = rs.getInt(2);
            cb.setOnAction(e -> {
                if(cb.isSelected()) {
                    try {
                        addTo(currentProduct, currentAmount);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        removeFrom(currentProduct, currentAmount);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            list_OL_PC.add(new PC_tuple(cb, rs.getString(1), rs.getInt(2)));
        }
            String dropDiff = "DROP VIEW IF EXISTS DIFF";
            String dropReq = "DROP VIEW IF EXISTS REQ";
            String dropPC = "DROP VIEW IF EXISTS PC";
            connection.createStatement().executeUpdate(dropDiff);
            connection.createStatement().executeUpdate(dropReq);
            connection.createStatement().executeUpdate(dropPC);


    }

    public void addTo(String product, Integer amount) throws SQLException {

        String query = "SELECT Pname from HC_OWNS_PRODUCT WHERE Pname =  \"" +  product + "\"" + "AND Email = \"" + currentUser.email + "\"";
        ResultSet rs = connection.createStatement().executeQuery(query);
        ResultSet rs2 = connection.createStatement().executeQuery(query);

        if(rs2.next()) {
            String update = "UPDATE HC_OWNS_PRODUCT SET Amount = Amount + ? WHERE Pname = ? AND Email = ?";
            PreparedStatement ps = connection.prepareStatement(update);
            ps.setString(3, currentUser.email);
            ps.setString(2, product);
            ps.setInt(1, amount);
            ps.executeUpdate();
        } else {
            String insert = "INSERT INTO HC_OWNS_PRODUCT VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.setString(1, currentUser.email);
            ps.setString(2, product);
            ps.setInt(3, amount);
            ps.executeUpdate();
        }


    }
    public void removeFrom(String product, Integer amount) throws SQLException {
        String query = "SELECT Pname from HC_OWNS_PRODUCT WHERE Pname =  \"" +  product + "\"";
        ResultSet rs = connection.createStatement().executeQuery(query);
            String update = "UPDATE HC_OWNS_PRODUCT SET Amount = Amount - ? WHERE Pname = ? AND Email = ?";
            PreparedStatement ps = connection.prepareStatement(update);
            ps.setString(3, currentUser.email);
            ps.setString(2, product);
            ps.setInt(1, amount);
            ps.executeUpdate();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }

    public static class MBSF_tuple {
        private String prep;
        private String name;

        public String getPrep() {
            return prep;
        }

        public String getName() {
            return name;
        }

        public MBSF_tuple(String name, String prep) {
            this.prep = prep;
            this.name = name;
        }
    }

    public static class PC_tuple {
        private CheckBox check;
        private String product;
        private Integer amount;

        public CheckBox getCheck() {
            return check;
        }

        public String getProduct() {
            return product;
        }

        public Integer getAmount() {
            return amount;
        }

        public PC_tuple(CheckBox check, String product, Integer amount) {
            this.check = check;
            this.product = product;
            this.amount = amount;
        }
    }




    public void go_to_wf18(ActionEvent actionEvent) throws IOException, SQLException {
        String dropDiff = "DROP VIEW IF EXISTS DIFF";
        String dropReq = "DROP VIEW IF EXISTS REQ";
        String dropPC = "DROP VIEW IF EXISTS PC";
        connection.createStatement().executeUpdate(dropDiff);
        connection.createStatement().executeUpdate(dropReq);
        connection.createStatement().executeUpdate(dropPC);
        HelloApplication.currentGRDate = null;
        HelloApplication.currentRunID = -1;
        HelloApplication.loadWireFrame("wf18.fxml");
    }

    public void add_to_mbsf(ActionEvent actionEvent) throws SQLException {
        String query = "INSERT INTO GROCERY_RUN_SOURCES_A_MEAL VALUES (?, ?, ?, ?)";
        java.sql.Date selectedDate = dateList.get(meal_picker.getSelectionModel().getSelectedIndex());
        Integer selectedId = integerList.get(meal_picker.getSelectionModel().getSelectedIndex());
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setDate(1,selectedDate);
        ps.setInt(2, currentRunID);
        ps.setInt(3,selectedId);
        ps.setString(4, currentUser.email);
        try {
            ps.executeUpdate();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            error_area.setText("That meal is already being sourced!");
        }


        String fillMBSFQuery = "SELECT PrepareDate, RECIPE.REC_Name FROM GROCERY_RUN_SOURCES_A_MEAL JOIN RECIPE ON " +
                "GROCERY_RUN_SOURCES_A_MEAL.Rec_ID = RECIPE.Rec_ID WHERE GROCERY_RUN_SOURCES_A_MEAL.Email = \"" + currentUser.email +
                "\" AND GROCERY_RUN_SOURCES_A_MEAL.Run_ID = " + currentRunID;


        re_query_MBSF_table();
    }
}
