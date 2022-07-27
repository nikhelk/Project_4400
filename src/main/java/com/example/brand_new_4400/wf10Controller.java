package com.example.brand_new_4400;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.brand_new_4400.HelloApplication.*;

public class wf10Controller {
    public Button go_back;
    public Button save_button;
    public TextArea comment_input;
    public TextField rating_input;

    public void go_to_wf9(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf9.fxml");
    }

    public void add_review(ActionEvent actionEvent) throws SQLException, IOException {
        String insertReview = "INSERT INTO REVIEW (Email, RComment, Rating, Rec_ID) VALUES(?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(insertReview);

        ps.setString(1, currentUser.email);
        ps.setString(2, comment_input.getText());
        ps.setInt(3, Integer.parseInt(rating_input.getText()));
        ps.setInt(4, currentRecipe.recipeId);
        ps.executeUpdate();
        HelloApplication.loadWireFrame("wf10.fxml");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
