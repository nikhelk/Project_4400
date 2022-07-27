package com.example.brand_new_4400;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.brand_new_4400.HelloApplication.connection;

//TODO add contributor average rating
public class wf4Controller implements Initializable {
    public Button back_button;
    public Button write_recipe_select;
    public Button browse_recipe_select;
    public Button see_recipe_select;
    public Button log_out;
    public Label average_rating_area;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query = "SELECT AVG(Rating) FROM RECIPE JOIN REVIEW ON RECIPE.Rec_ID = REVIEW.Rec_ID WHERE Recipe.Email = " +
                "\"" + HelloApplication.currentUser.email + "\"";
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(query);
            double ans = 0;
            while (rs.next()) {
                ans = rs.getDouble(1);
            }
            if(ans == 0) {
                average_rating_area.setText("Average Rating: X.X/5.0");
            } else {
                average_rating_area.setText("Average Rating: " + ans + "/5.0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        public void go_to_wf3(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf3.fxml");
    }

    public void go_to_wf5(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf5.fxml");
    }

    public void go_to_wf11(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf11.fxml");
    }

    public void go_to_wf7(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf7.fxml");
    }

    public void log_out(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
