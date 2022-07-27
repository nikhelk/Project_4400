package com.example.brand_new_4400;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.brand_new_4400.HelloApplication.connection;

public class wf11Controller implements Initializable {
    public Button go_back;
    public HBox cuisine_hbox;
    public TableView<Wf11_tuple> wf11_table;
    public HBox diet_tag_hbox;
    public TableColumn<Wf11_tuple, Integer> id_col;
    public TableColumn<Wf11_tuple, String> name_col;
    public TableColumn<Wf11_tuple, Integer> author_col;
    public TableColumn<Wf11_tuple, Integer> cuisine_col;
    public TableColumn<Wf11_tuple, Integer> diet_col;
    public TableColumn<Wf11_tuple, Integer> rating_col;
    public Button cuisines_plus;
    public Button diet_tag_plus;
    public String dietTagConcatQuery = "";
    public String dietTagConcatTable = "";
    public String cuisineConcatQuery = "";
    public String cuisineConcatTable = "";
    ObservableList<Wf11_tuple> list_OL = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_col.setCellValueFactory(new PropertyValueFactory<>("id_wf11"));
        author_col.setCellValueFactory(new PropertyValueFactory<>("author_wf11"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name_wf11"));
        rating_col.setCellValueFactory(new PropertyValueFactory<>("average_rating_wf11"));
        diet_col.setCellValueFactory(new PropertyValueFactory<>("dietTags_wf11"));
        cuisine_col.setCellValueFactory(new PropertyValueFactory<>("cuisine_wf11"));
        try {
            re_query_table();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void go_to_wf4(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf4.fxml");
    }

    public void update_filters(KeyEvent inputMethodEvent) throws SQLException {

        dietTagConcatQuery = "";
        dietTagConcatTable = "";
        cuisineConcatTable = "";
        cuisineConcatQuery = "";

        for(int i = 0; i < diet_tag_hbox.getChildren().size() - 1; i++) {
            ((TextField)diet_tag_hbox.getChildren().get(i)).setEditable(true);
        }

        for(int i = 0; i < diet_tag_hbox.getChildren().size() - 1; i++) {
            Node item = diet_tag_hbox.getChildren().get(diet_tag_hbox.getChildren().size() - 1);
            if(!(((TextField)(item)).getText().isEmpty())) {
                ((TextField)diet_tag_hbox.getChildren().get(i)).setEditable(false);
            }
        }

        int countDT = 0;
        for(Node item : diet_tag_hbox.getChildren()) {
            String currText = ((TextField) item).getText();
            if(countDT == 0 && !currText.isEmpty()) {
                dietTagConcatQuery += "(" + "\"" + currText + "\"" + ") IN (SELECT DietTag FROM DIETTAG WHERE RECIPE.Rec_ID = DIETTAG.Rec_ID)";
            }
            if(!currText.isEmpty() && countDT > 0) {
                dietTagConcatQuery += " AND (" + "\"" + currText + "\"" + ") IN (SELECT DietTag FROM DIETTAG WHERE RECIPE.Rec_ID = DIETTAG.Rec_ID)";
            }
            dietTagConcatTable += currText + ", ";
            countDT++;
        }

        for(int i = 0; i < cuisine_hbox.getChildren().size() - 1; i++) {
            ((TextField)cuisine_hbox.getChildren().get(i)).setEditable(true);
        }

        for(int i = 0; i < cuisine_hbox.getChildren().size() - 1; i++) {
            Node item = cuisine_hbox.getChildren().get(cuisine_hbox.getChildren().size() - 1);
            if(!(((TextField)(item)).getText().isEmpty())) {
                ((TextField)cuisine_hbox.getChildren().get(i)).setEditable(false);
            }
        }

        int countC = 0;
        for(Node item : cuisine_hbox.getChildren()) {
            String currText = ((TextField) item).getText();
            if(countC == 0 && !currText.isEmpty()) {
                cuisineConcatQuery += "(" + "\"" + currText + "\"" + ") IN (SELECT Cuisine FROM CUISINE WHERE RECIPE.Rec_ID = CUISINE.Rec_ID)";
            }
            if(!currText.isEmpty() && countC > 0) {
                cuisineConcatQuery += " AND (" + "\"" + currText + "\""  + ") IN (SELECT Cuisine FROM CUISINE WHERE RECIPE.Rec_ID = CUISINE.Rec_ID)";
            }
            cuisineConcatTable += currText + ", ";
            countC++;
        }
        re_query_table();

    }

    public void add_diet_tag(ActionEvent actionEvent) {
        TextField newInput = new TextField();
        diet_tag_hbox.getChildren().add(newInput);
        newInput.setOnKeyTyped( e -> {
            dietTagConcatQuery = "";
            dietTagConcatTable = "";
            cuisineConcatTable = "";
            cuisineConcatQuery = "";

            for(int i = 0; i < diet_tag_hbox.getChildren().size() - 1; i++) {
                ((TextField)diet_tag_hbox.getChildren().get(i)).setEditable(true);
            }

            for(int i = 0; i < diet_tag_hbox.getChildren().size() - 1; i++) {
                Node item = diet_tag_hbox.getChildren().get(diet_tag_hbox.getChildren().size() - 1);
                if(!(((TextField)(item)).getText().isEmpty())) {
                    ((TextField)diet_tag_hbox.getChildren().get(i)).setEditable(false);
                }
            }

            int countDT = 0;
            for(Node item : diet_tag_hbox.getChildren()) {
                String currText = ((TextField) item).getText();
                if(countDT == 0 && !currText.isEmpty()) {
                    dietTagConcatQuery += "(" + "\"" + currText + "\"" + ") IN (SELECT DietTag FROM DIETTAG WHERE RECIPE.Rec_ID = DIETTAG.Rec_ID)";
                }
                if(!currText.isEmpty() && countDT > 0) {
                    dietTagConcatQuery += " AND (" + "\"" + currText + "\"" + ") IN (SELECT DietTag FROM DIETTAG WHERE RECIPE.Rec_ID = DIETTAG.Rec_ID)";
                }
                dietTagConcatTable += currText + ", ";
                countDT++;
            }

            for(int i = 0; i < cuisine_hbox.getChildren().size() - 1; i++) {
                ((TextField)cuisine_hbox.getChildren().get(i)).setEditable(true);
            }

            for(int i = 0; i < cuisine_hbox.getChildren().size() - 1; i++) {
                Node item = cuisine_hbox.getChildren().get(cuisine_hbox.getChildren().size() - 1);
                if(!(((TextField)(item)).getText().isEmpty())) {
                    ((TextField)cuisine_hbox.getChildren().get(i)).setEditable(false);
                }
            }

            int countC = 0;
            for(Node item : cuisine_hbox.getChildren()) {
                String currText = ((TextField) item).getText();
                if(countC == 0 && !currText.isEmpty()) {
                    cuisineConcatQuery += "(" + "\"" + currText + "\"" + ") IN (SELECT Cuisine FROM CUISINE WHERE RECIPE.Rec_ID = CUISINE.Rec_ID)";
                }
                if(!currText.isEmpty() && countC > 0) {
                    cuisineConcatQuery += " AND (" + "\"" + currText + "\""  + ") IN (SELECT Cuisine FROM CUISINE WHERE RECIPE.Rec_ID = CUISINE.Rec_ID)";
                }
                cuisineConcatTable += currText + ", ";
                countC++;
            }
            try {
                re_query_table();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } );
    }

    public void add_cuisines(ActionEvent actionEvent) {
        TextField newInput = new TextField();
        cuisine_hbox.getChildren().add(newInput);
        newInput.setOnKeyTyped( e -> {
            dietTagConcatQuery = "";
            dietTagConcatTable = "";
            cuisineConcatTable = "";
            cuisineConcatQuery = "";

            for(int i = 0; i < diet_tag_hbox.getChildren().size() - 1; i++) {
                ((TextField)diet_tag_hbox.getChildren().get(i)).setEditable(true);
            }

            for(int i = 0; i < diet_tag_hbox.getChildren().size() - 1; i++) {
                Node item = diet_tag_hbox.getChildren().get(diet_tag_hbox.getChildren().size() - 1);
                if(!(((TextField)(item)).getText().isEmpty())) {
                    ((TextField)diet_tag_hbox.getChildren().get(i)).setEditable(false);
                }
            }


            int countDT = 0;
            for(Node item : diet_tag_hbox.getChildren()) {
                String currText = ((TextField) item).getText();
                if(countDT == 0 && !currText.isEmpty()) {
                    dietTagConcatQuery += "(" + "\"" + currText + "\"" + ") IN (SELECT DietTag FROM DIETTAG WHERE RECIPE.Rec_ID = DIETTAG.Rec_ID)";
                }
                if(!currText.isEmpty() && countDT > 0) {
                    dietTagConcatQuery += " AND (" + "\"" + currText + "\"" + ") IN (SELECT DietTag FROM DIETTAG WHERE RECIPE.Rec_ID = DIETTAG.Rec_ID)";
                }
                dietTagConcatTable += currText + ", ";
                countDT++;
            }

            for(int i = 0; i < cuisine_hbox.getChildren().size() - 1; i++) {
                ((TextField)cuisine_hbox.getChildren().get(i)).setEditable(true);
            }

            for(int i = 0; i < cuisine_hbox.getChildren().size() - 1; i++) {
                Node item = cuisine_hbox.getChildren().get(cuisine_hbox.getChildren().size() - 1);
                if(!(((TextField)(item)).getText().isEmpty())) {
                    ((TextField)cuisine_hbox.getChildren().get(i)).setEditable(false);
                }
            }

            int countC = 0;
            for(Node item : cuisine_hbox.getChildren()) {
                String currText = ((TextField) item).getText();
                if(countC == 0 && !currText.isEmpty()) {
                    cuisineConcatQuery += "(" + "\"" + currText + "\"" + ") IN (SELECT Cuisine FROM CUISINE WHERE RECIPE.Rec_ID = CUISINE.Rec_ID)";
                }
                if(!currText.isEmpty() && countC > 0) {
                    cuisineConcatQuery += " AND (" + "\"" + currText + "\""  + ") IN (SELECT Cuisine FROM CUISINE WHERE RECIPE.Rec_ID = CUISINE.Rec_ID)";
                }
                cuisineConcatTable += currText + ", ";
                countC++;
            }
            try {
                re_query_table();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } );

    }

    public void re_query_table() throws SQLException {
        String initialQuery = "";
        if(cuisineConcatQuery.equals("") && !dietTagConcatQuery.equals("")) {
            initialQuery = "SELECT RECIPE.Rec_ID, RECIPE.Rec_Name, UName, AVG(Rating) AVG_Rating " +
                    "FROM RECIPE JOIN USERS ON (RECIPE.Email = USERS.Email) LEFT JOIN REVIEW ON (RECIPE.Rec_ID = REVIEW.Rec_ID) WHERE " + dietTagConcatQuery +
                    " GROUP BY (RECIPE.Rec_ID) ORDER BY AVG_Rating ASC";
        }
        if(dietTagConcatQuery.equals("") &&  !cuisineConcatQuery.equals("")) {
            initialQuery = "SELECT RECIPE.Rec_ID, RECIPE.Rec_Name, UName, AVG(Rating) AVG_Rating " +
                    "FROM RECIPE JOIN USERS ON (RECIPE.Email = USERS.Email) LEFT JOIN REVIEW ON (RECIPE.Rec_ID = REVIEW.Rec_ID) WHERE " + cuisineConcatQuery
                    + " GROUP BY (RECIPE.Rec_ID) ORDER BY AVG_Rating ASC";
        }
        if(!cuisineConcatQuery.equals("") && !dietTagConcatQuery.equals("") ) {
            initialQuery = "SELECT RECIPE.Rec_ID, RECIPE.Rec_Name, UName, AVG(Rating) AVG_Rating " +
                    "FROM RECIPE JOIN USERS ON (RECIPE.Email = USERS.Email) LEFT JOIN REVIEW ON (RECIPE.Rec_ID = REVIEW.Rec_ID) WHERE " +
                    dietTagConcatQuery + " AND " + cuisineConcatQuery + " GROUP BY (RECIPE.Rec_ID) ORDER BY AVG_Rating ASC";
        }

        if(dietTagConcatQuery.equals("") && cuisineConcatQuery.equals("")) {
            initialQuery = "SELECT RECIPE.Rec_ID, RECIPE.Rec_Name, UName, AVG(Rating) AVG_Rating FROM RECIPE JOIN USERS ON (RECIPE.Email = USERS.Email) LEFT JOIN REVIEW ON (RECIPE.Rec_ID = REVIEW.Rec_ID) " +
                    "GROUP BY (RECIPE.Rec_ID) " +
            "ORDER BY AVG_Rating ASC";
        }

        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(initialQuery);
        list_OL.clear();
        ArrayList<Hyperlink> hyperLinkList = new ArrayList<>();
        ArrayList<Recipe> idList = new ArrayList<>();
        while(rs.next()) {
            Hyperlink toAdd = new Hyperlink();
            toAdd.setText(String.valueOf(rs.getInt("RECIPE.Rec_ID")));
            String dietTagToTable = "";
            String innerDTQuery = "(SELECT DietTag FROM DIETTAG WHERE " + rs.getInt("RECIPE.Rec_ID") +  " = DIETTAG.Rec_ID)";
            ResultSet rsDT = connection.createStatement().executeQuery(innerDTQuery);
            while (rsDT.next()) {
                dietTagToTable += rsDT.getString(1) + " ";
            }
            String cuisineToTable = "";
            String innerCQuery = "(SELECT Cuisine FROM CUISINE WHERE " + rs.getInt("RECIPE.Rec_ID") +  " = CUISINE.Rec_ID)";
            ResultSet rsC = connection.createStatement().executeQuery(innerCQuery);
            while (rsC.next()) {
                cuisineToTable += rsC.getString(1) + " ";
            }
            if(rs.getDouble(4) != 0) {
                list_OL.add(new Wf11_tuple(toAdd, rs.getString(2), rs.getString(3), cuisineToTable, dietTagToTable, rs.getDouble(4)));
            } else {
                list_OL.add(new Wf11_tuple(toAdd, rs.getString(2), rs.getString(3), cuisineToTable, dietTagToTable, null));
            }
            hyperLinkList.add(toAdd);
            idList.add(new Recipe(rs.getInt(1),HelloApplication.currentUser.email, rs.getString(2),"bruh",rs.getDouble(4)));
        }

        for(int i = 0; i < hyperLinkList.size(); i++) {
            int finalI = i;
            hyperLinkList.get(i).setOnAction(e -> {
                try {
                    HelloApplication.currentRecipe = idList.get(finalI);
                    HelloApplication.isComingFromWf7 = false;
                    HelloApplication.isHomeChef = false;
                    HelloApplication.loadWireFrame("wf8.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }



        wf11_table.setItems(list_OL);
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
