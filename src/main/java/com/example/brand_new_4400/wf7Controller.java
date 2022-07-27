package com.example.brand_new_4400;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.brand_new_4400.HelloApplication.connection;
import static com.example.brand_new_4400.HelloApplication.currentUser;

public class wf7Controller implements Initializable {
    public Button go_back;


    public TableView<Wf7_tuple> wf7_table;
    public TableColumn<Wf7_tuple, Hyperlink> id_col;
    public TableColumn<Wf7_tuple, String> name_col;
    public TableColumn<Wf7_tuple, String> average_rating_col;
    public AnchorPane pain;

    ObservableList<Wf7_tuple> list_OL = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableView<Wf7_tuple> tv = new TableView<>();
        tv.getColumns().add(id_col);
        tv.getColumns().add(name_col);
        tv.getColumns().add(average_rating_col);






        System.out.println(HelloApplication.currentUser.email);
        String initial_query = "SELECT RECIPE.Rec_ID, Rec_Name, AVG(Rating) AVG_Rating FROM RECIPE LEFT JOIN REVIEW ON RECIPE.Rec_ID = REVIEW.Rec_ID WHERE RECIPE.Email = " + "\"" + HelloApplication.currentUser.email
        + "\"" + " GROUP BY RECIPE.Rec_ID, Rec_Name";

        Statement s = null;
        try {
            s = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        assert s != null;
        try {
            rs = s.executeQuery(initial_query);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        average_rating_col.setCellValueFactory(new PropertyValueFactory<>("average_rating_string"));
        average_rating_col.setCellValueFactory(new PropertyValueFactory<>("average_rating_double"));

        ArrayList<Hyperlink> hyperLinkList = new ArrayList<>();
        ArrayList<Recipe> idList = new ArrayList<>();
        while(true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(rs.getInt("RECIPE.Rec_ID") + " " + rs.getString(2) + " " + rs.getInt(3));
                Hyperlink toAdd = new Hyperlink();
                toAdd.setText(String.valueOf(rs.getInt("RECIPE.Rec_ID")));
                hyperLinkList.add(toAdd);
                idList.add( new Recipe(rs.getInt("RECIPE.Rec_ID"), currentUser.email, rs.getString(2),"bruh",rs.getDouble(3)));
                if(rs.getDouble("AVG_Rating") != 0) {
                    list_OL.add(new Wf7_tuple(toAdd, rs.getString("Rec_Name"), rs.getDouble("AVG_Rating")));
                } else {
                    list_OL.add(new Wf7_tuple(toAdd, rs.getString("Rec_Name"), "N/A"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < hyperLinkList.size(); i++) {
            int finalI = i;
            hyperLinkList.get(i).setOnAction(e -> {
                try {
                    HelloApplication.currentRecipe = idList.get(finalI);
                    HelloApplication.isComingFromWf7 = true;
                    HelloApplication.isHomeChef = false;
                    HelloApplication.loadWireFrame("wf8.fxml");

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        tv.setItems(list_OL);
        pain.getChildren().add(tv);

        wf7_table.setItems(list_OL);
        System.out.println("AFTERRR");

        for(Wf7_tuple item : list_OL) {
           // wf7_table.getItems().add(item);
            // System.out.println(item.getAverage_rating());
        }


    }
    public void go_to_wf4(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf4.fxml");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
