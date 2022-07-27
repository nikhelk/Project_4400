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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import static com.example.brand_new_4400.HelloApplication.*;
public class wf18Controller implements Initializable {
    public AnchorPane pain_18;
    public Button new_run_button;
    public Button go_back;
//    public TableView<Wf18_tuple> wf18_table;
//    public TableColumn<Wf18_tuple, Hyperlink> idCol;
//    public TableColumn<Wf18_tuple, String> dateCol;

    private final TableView<Wf18_tuple> tv = new TableView<>();
    private final ObservableList<Wf18_tuple> list_OL_18 =
            FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn firstNameCol = new TableColumn("Run ID");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("run"));

        TableColumn lastNameCol = new TableColumn("Date");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        tv.setItems(list_OL_18);
        tv.getColumns().addAll(firstNameCol, lastNameCol);

        String query = "SELECT Run_ID, GDate FROM GROCERY_RUN WHERE " + "\"" + HelloApplication.currentUser.email + "\""
                + " = GROCERY_RUN.Email";
        ResultSet rs = null;
        try {
            rs = connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Hyperlink> hyperLinkList = new ArrayList<>();
        ArrayList<java.sql.Date> dateList = new ArrayList<>();
        ArrayList<Integer> idList = new ArrayList<>();
        assert rs != null;
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Hyperlink toAdd = null;
            try {
                toAdd = new Hyperlink(String.valueOf(rs.getInt(1)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            assert toAdd != null;
            try {
                list_OL_18.add(new Wf18_tuple(toAdd, rs.getDate(2).toString()));
                hyperLinkList.add(toAdd);
                dateList.add(rs.getDate(2));
                idList.add(rs.getInt(1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < hyperLinkList.size(); i++) {
            int finalI = i;
            hyperLinkList.get(i).setOnAction(e -> {
                try {
                    HelloApplication.currentRunID = idList.get(finalI);
                    currentGRDate = dateList.get(finalI);
                    HelloApplication.loadWireFrame("wf19.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }


        // wf18_table.setItems(list_OL_18);
        pain_18.getChildren().add(tv);

/*        for(int i = 0; i < wf18_table.getItems().size(); i++) {
            Wf18_tuple bruh = wf18_table.getItems().get(i);
            System.out.println(bruh.getRunId());
            System.out.println(bruh.getDateTime());
        }*/
    }


    public void create_new_run(ActionEvent actionEvent) throws ParseException, IOException, SQLException {

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        java.util.Date parsed = format.parse(format.format(date));
        currentGRDate = new java.sql.Date(parsed.getTime());
        String query = "INSERT INTO GROCERY_RUN(GDate,Email) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setDate(1, currentGRDate);
        ps.setString(2, currentUser.email);
        ps.executeUpdate();

        HelloApplication.loadWireFrame("wf19.fxml");
    }

    public void go_to_wf12(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf12.fxml");
    }


    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
