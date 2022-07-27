package com.example.brand_new_4400;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static com.example.brand_new_4400.HelloApplication.*;

public class wf16Controller implements Initializable {
    public TableView<Wf16_tuple> wf16_table;
    public TableColumn<Wf16_tuple, Integer> id_col;
    public TableColumn<Wf16_tuple, String> user_col;
    public TableColumn<Wf16_tuple, Integer> rating_col;
    public TableColumn<Wf16_tuple, String> comment_col;
    public Button go_back;
    public Button write_button;
    public Label name_space;
    public AnchorPane pain_wf16;

    ObservableList<Wf16_tuple> list_OL = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_col.setCellValueFactory(new PropertyValueFactory<>("id_wf16"));
        user_col.setCellValueFactory(new PropertyValueFactory<>("userEmail_wf16"));
        rating_col.setCellValueFactory(new PropertyValueFactory<>("rating_wf16"));
        comment_col.setCellValueFactory(new PropertyValueFactory<>("comment_wf16"));

        TableView<Wf16_tuple> tv = new TableView<>();
        tv.getColumns().addAll(id_col, user_col, rating_col, comment_col);



        String query = "SELECT Rev_ID, UName, Rating, RComment FROM REVIEW R JOIN USERS U ON R.Email = U.Email WHERE Rec_ID =" +
                "\"" + HelloApplication.currentRecipe.recipeId + "\"";


        try {
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()) {
                list_OL.add(new Wf16_tuple(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        tv.setItems(list_OL);
        tv.setLayoutX(39.0);
        tv.setLayoutY(114.0);
        tv.setPrefHeight(200);
        tv.setPrefWidth(522);
        pain_wf16.getChildren().add(tv);
        wf16_table.setItems(list_OL);
        name_space.setText(currentRecipe.recipeName);


    }
    public void go_to_wf15(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf15.fxml");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
