package com.example.brand_new_4400;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.brand_new_4400.HelloApplication.*;


public class wf15Controller implements Initializable {
    public Label name_space;
    public Button go_back;
    public Hyperlink review_button;
    public Label email_space;
    public Text average_rating_space;
    public TableView<Wf8_tuple> product_table;
    public Text instructions_space;
    public HBox diet_tag_hbox;
    public HBox cuisine_hbox;
    public TableColumn<Wf8_tuple, String> product_col;
    public TableColumn<Wf8_tuple, String> amount_col;
    public Button pm_button;

    ObservableList<Wf8_tuple> list_OL = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        product_col.setCellValueFactory(new PropertyValueFactory<>("productName"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));

        String productQuery = "SELECT Pname, Amount FROM RECIPE_USES_PRODUCT WHERE Rec_ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(productQuery);
            ps.setInt(1,currentRecipe.recipeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String unitQuery = "SELECT Units FROM PRODUCT WHERE Pname = " + "\"" + rs.getString(1) + "\"";
                ResultSet resultSetUnits = connection.createStatement().executeQuery(unitQuery);
                String unitDisplay = null;
                while (resultSetUnits.next()) {
                    if(resultSetUnits.getString(1) != null) {
                        unitDisplay = resultSetUnits.getString(1);
                    }
                }
                if(unitDisplay == null) {
                    unitDisplay = "units";
                }
                list_OL.add(new Wf8_tuple(rs.getString(1), rs.getInt(2) + " " + unitDisplay));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        product_table.setItems(list_OL);


        name_space.setText(HelloApplication.currentRecipe.recipeName);
        email_space.setText(currentUser.name);

        String getAverage = "SELECT AVG(Rating) AVG_Rating, SUM(Rating) SUM_Rating  FROM RECIPE JOIN REVIEW ON RECIPE.Rec_ID = REVIEW.Rec_ID WHERE REVIEW.Rec_ID = " + "\"" + currentRecipe.recipeId + "\"";
        try {
            ResultSet rs = connection.createStatement().executeQuery(getAverage);
            while (rs.next()) {
                average_rating_space.setText("Average rating: " + rs.getDouble(1) + "/5.0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String instructionGet = "CREATE TABLE ChosenRecipe (SELECT * FROM RECIPE WHERE Rec_ID = ?)";
        try {
            PreparedStatement flightPreparedStatement =
                    connection.prepareStatement(instructionGet);
            flightPreparedStatement.setInt(1,currentRecipe.recipeId);
            flightPreparedStatement.executeUpdate();
            flightPreparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet dietTags = connection.createStatement().executeQuery("SELECT DietTag FROM DIETTAG JOIN ChosenRecipe ON DIETTAG.Rec_ID = ChosenRecipe.Rec_ID");

            while (dietTags.next()) {
                Label dietTagPrint = new Label();
                dietTagPrint.setText(dietTags.getString(1));
                diet_tag_hbox.getChildren().add(dietTagPrint);
            }

            ResultSet cuisines = connection.createStatement().executeQuery("SELECT Cuisine FROM CUISINE JOIN ChosenRecipe ON CUISINE.Rec_ID = ChosenRecipe.Rec_ID");


            while (cuisines.next()) {
                Label cuisinePrint = new Label();
                cuisinePrint.setText(cuisines.getString(1));
                cuisine_hbox.getChildren().add(cuisinePrint);
            }

            ResultSet instructions = connection.createStatement().executeQuery("SELECT Instructions FROM ChosenRecipe");
            while(instructions.next()) {
                instructions_space.setText(instructions.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.createStatement().executeUpdate("DROP TABLE ChosenRecipe");
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void go_to_wf14(ActionEvent actionEvent) throws IOException {
        HelloApplication.currentRecipe = null;
        HelloApplication.loadWireFrame("wf14.fxml");
    }

    public void go_to_wf16(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf16.fxml");
    }

    public void go_to_wf17(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf17.fxml");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
