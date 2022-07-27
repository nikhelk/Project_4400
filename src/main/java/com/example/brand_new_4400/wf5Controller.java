package com.example.brand_new_4400;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.brand_new_4400.HelloApplication.connection;
import static java.lang.Integer.parseInt;

public class wf5Controller implements Initializable {
    public Button wf5_add_diet_tag;
    public Button wf5_add_cuisines;
    public HBox wf5_diet_tag_hbox;
    public TextField diet_tag_1;
    public TextField diet_tag_2;
    public HBox wf5_cuisine_hbox;
    public TextField cuisine_1;
    public TextField cuisine_2;
    public Button Save_button;
    public TextField name_input;
    public Label error_area;
    public TextArea instructions_input;
    public Button go_back;
    public Button log_out;
    public ArrayList<String> dietTagArray;
    public ArrayList<String> cuisineArray;
    public Button add_product_button;
    public ComboBox<String> product_selection;
    public Button add_product_not_there;
    public boolean onSelectionOption = false;
    public Label Amt_label;
    public TextField amount_input;
    public TableColumn<Product_amount_tuple, String> name;
    public TableColumn<Product_amount_tuple, Integer> amount;
    public TableView<Product_amount_tuple> recipe_product_table;
    ObservableList<Product_amount_tuple> product_amount_list_OL = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        recipe_product_table.setItems(product_amount_list_OL);


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
    }

    public void add_diet_tag(ActionEvent actionEvent) {
        TextField newInput = new TextField();

        wf5_diet_tag_hbox.getChildren().add(newInput);
    }

    public void add_cuisine(ActionEvent actionEvent) {
        TextField newInput = new TextField();

        wf5_cuisine_hbox.getChildren().add(newInput);
    }

    public void go_to_wf4(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf4.fxml");

    }

    public void log_off(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }

    public void save_recipe(ActionEvent actionEvent) throws SQLException, IOException {

        String flightInsert = "INSERT INTO RECIPE (Email, Rec_Name, Instructions) VALUES (?, ?, ?)";

        PreparedStatement flightPreparedStatement =
                connection.prepareStatement(flightInsert);

        flightPreparedStatement.setString(1,HelloApplication.currentUser.email);
        flightPreparedStatement.setString(2, name_input.getText());
        flightPreparedStatement.setString(3, instructions_input.getText());
        flightPreparedStatement.executeUpdate();
        flightPreparedStatement.close();

        /*String initial_query = "SELECT Rec_Name, AVG(Rating) AVG_Rating FROM RECIPE JOIN REVIEW ON RECIPE.Rec_ID = REVIEW.Rec_ID WHERE RECIPE.Email = " + "\"" + HelloApplication.currentUser.email
                + "\"" + " GROUP BY RECIPE.Rec_ID, Rec_Name";*/

        String initial_query = "SELECT Rec_Name FROM RECIPE WHERE RECIPE.Email = " + "\"" + HelloApplication.currentUser.email
                + "\"";

        PreparedStatement bruh2 = connection.prepareStatement(initial_query);
        ResultSet rs = bruh2.executeQuery();
        while (rs.next()) {
            System.out.println("RECIPE " + rs.getString(1));
        }

        for(Node item : wf5_diet_tag_hbox.getChildren()) {
            if(!(((TextField) item).getText()).isEmpty()) {
                String dietTagInsert = "INSERT INTO DIETTAG VALUES (LAST_INSERT_ID(), ?)";
                PreparedStatement dietTagInsertPreparedStatement = connection.prepareStatement(dietTagInsert);
                dietTagInsertPreparedStatement.setString(1, ((TextField) item).getText());
                dietTagInsertPreparedStatement.executeUpdate();
                dietTagInsertPreparedStatement.close();
                System.out.println(dietTagInsert);
            }
        }

        for(Node item : wf5_cuisine_hbox.getChildren()) {
            if(!(((TextField) item).getText()).isEmpty()) {
                String dietTagInsert = "INSERT INTO CUISINE VALUES (LAST_INSERT_ID(), ?)";
                PreparedStatement dietTagInsertPreparedStatement = connection.prepareStatement(dietTagInsert);
                dietTagInsertPreparedStatement.setString(1, ((TextField) item).getText());
                dietTagInsertPreparedStatement.executeUpdate();
                dietTagInsertPreparedStatement.close();
                System.out.println(dietTagInsert);
            }
        }

        for(Product_amount_tuple item : product_amount_list_OL) {
            String dietTagInsert = "INSERT INTO RECIPE_USES_PRODUCT VALUES (LAST_INSERT_ID(), ?, ?)";
            PreparedStatement dietTagInsertPreparedStatement = connection.prepareStatement(dietTagInsert);
            dietTagInsertPreparedStatement.setString(1, item.getName());
            dietTagInsertPreparedStatement.setInt(2, item.getAmount());
            dietTagInsertPreparedStatement.executeUpdate();
            dietTagInsertPreparedStatement.close();
        }

        String testStatement = "SELECT * FROM RECIPE_USES_PRODUCT WHERE RECIPE_USES_PRODUCT.Rec_ID = LAST_INSERT_ID()";
        Statement bruh = connection.createStatement();
        ResultSet test = bruh.executeQuery(testStatement);

        while (test.next()) {
            System.out.println("product in use " + test.getString(2));
        }
        HelloApplication.loadWireFrame("wf5.fxml");
    }

    public void change_wf(ActionEvent actionEvent) throws IOException, SQLException {
//        onSelectionOption = true;
//        ArrayList<String> product_list = new ArrayList<String>();
//        product_selection.setOpacity(1);
//        add_product_not_there.setOpacity(1);
//        String testStatement = "SELECT Pname FROM PRODUCT";
//        Statement bruh = connection.createStatement();
//        ResultSet test = bruh.executeQuery(testStatement);
//
//        List<String> listOfProducts;
//        while (test.next()) {
//            product_list.add(test.getString("Pname"));
//            System.out.println(
//                    (test.getString("Pname")));
//        }
//
//        ObservableList<String> listOfProducts_OL = FXCollections.observableArrayList(product_list);
//        System.out.println("combo box: " + product_selection);
//        System.out.println("add" + add_product_not_there);
//        product_selection.setItems(listOfProducts_OL);
//        System.out.println(product_selection.getItems());
    }

    public void go_to_wf6(ActionEvent actionEvent) throws IOException {
            HelloApplication.loadWireFrame("wf6.fxml");
    }

    public void add_to_product_table(ActionEvent actionEvent) throws SQLException {
        /*product_amount_list_OL.add(new Product_amount_tuple(product_selection.getValue().toString(), amount_input.getText()));

    for(Product_amount_tuple item : product_amount_list_OL) {
        System.out.println(item.product);
    }*/
    //recipe_product_table.getItems().add(new Product_amount_tuple(product_selection.getValue().toString(), amount_input.getText()));
        product_amount_list_OL.add(new Product_amount_tuple(product_selection.getValue(), (Integer) parseInt(amount_input.getText())));

       /* for(Product_amount_tuple item : product_amount_list_OL) {
            System.out.println(item.getName());
        }*/
        /*recipe_product_table.getItems().add(0, product_selection.getValue().toString());
        recipe_product_table.getItems().add(1, amount_input.getText());*/
        /*String flightInsert = "INSERT INTO RECIPE_USES_PRODUCT VALUES (MAX(RECIPE.Rec_ID) + 1, ?)";

        PreparedStatement flightPreparedStatement =
                connection.prepareStatement(flightInsert);
        flightPreparedStatement.setString(1,product_selection.getValue().toString());

        flightPreparedStatement.executeUpdate();
        flightPreparedStatement.close();
        System.out.println("Selected Product Name: " + product_selection.getValue().toString());*/





    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
