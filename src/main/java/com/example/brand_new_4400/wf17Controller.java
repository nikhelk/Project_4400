package com.example.brand_new_4400;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import static com.example.brand_new_4400.HelloApplication.connection;

public class wf17Controller implements Initializable {
    public Label title_area;
    public Button go_back;
    public TextField date_input;
    public Button add_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title_area.setText(HelloApplication.currentRecipe.recipeName + " - Plan Meal");
    }


        public void go_to_wf15(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf15.fxml");
    }

    public void add_to_meal(ActionEvent actionEvent) throws SQLException, ParseException, IOException {
        String query = "INSERT INTO MEAL VALUES(?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        java.util.Date parsed = format.parse(date_input.getText());
        java.sql.Date sql = new java.sql.Date(parsed.getTime());

        ps.setDate(1,sql);
        ps.setInt(2, HelloApplication.currentRecipe.recipeId);
        ps.setString(3, HelloApplication.currentUser.email);
        ps.executeUpdate();
        ps.close();

        String query2 = "SELECT * FROM MEAL";

        ResultSet rs = connection.createStatement().executeQuery(query2);


        HelloApplication.loadWireFrame("wf17.fxml");

    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
