package com.example.brand_new_4400;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.brand_new_4400.HelloApplication.connection;

public class wf6Controller {
    public TextField input_name;
    public Button add_product;
    public CheckBox is_tool;
    public HBox types_hbox;
    public Button back_button;
    public TextField unit_input;
    public Button add_type;
    public Label error_area;

    //TODO fix the deletion of current state on wf5,wf13
    //TODO add amount feild in wf6

    public void go_back(ActionEvent actionEvent) throws IOException {
        if(HelloApplication.isHomeChef) {
            HelloApplication.loadWireFrame("wf13.fxml");
        } else {
            HelloApplication.loadWireFrame("wf5.fxml");
        }
    }

    public void add_to_product_table(ActionEvent actionEvent) throws SQLException {

        if(is_tool.isSelected()) {


            String flightInsert = "INSERT INTO PRODUCT VALUES (?, ?, ?)";

            PreparedStatement flightPreparedStatement =
                    connection.prepareStatement(flightInsert);

            flightPreparedStatement.setString(1,input_name.getText());
            flightPreparedStatement.setString(2, "unit");
            flightPreparedStatement.setInt(3, 1);

            flightPreparedStatement.executeUpdate();
            flightPreparedStatement.close();

        } else {
            String Product_add = "INSERT INTO PRODUCT VALUES ("+
                    "\"" + input_name.getText() + "\"" + "," + "\"" + unit_input.getText() + "\"" + ",0)";

           // String flightInsert = "INSERT INTO PRODUCT VALUES (?, ?, ?)";

            PreparedStatement flightPreparedStatement = connection.prepareStatement(Product_add);

/*            flightPreparedStatement.setString(1,input_name.getText());
            flightPreparedStatement.setString(2, unit_input.getText());
            flightPreparedStatement.setString(3, "0");*/
                try {
                    flightPreparedStatement.executeUpdate();



            flightPreparedStatement.close();

            for(Node item : types_hbox.getChildren()) {
                if(!(((TextField) item).getText()).isEmpty()) {
                    String dietTagInsert = "INSERT INTO ITYPE VALUES (?, ?)";
                    PreparedStatement dietTagInsertPreparedStatement = connection.prepareStatement(dietTagInsert);
                    dietTagInsertPreparedStatement.setString(1, input_name.getText());
                    dietTagInsertPreparedStatement.setString(2, ((TextField) item).getText());
                    dietTagInsertPreparedStatement.executeUpdate();
                    dietTagInsertPreparedStatement.close();
                }
            }
            HelloApplication.loadWireFrame("wf6.fxml");
                } catch (java.sql.SQLIntegrityConstraintViolationException e) {
                    error_area.setText("Product already exists");
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

    }

    public void add_da_type(ActionEvent actionEvent) {
        TextField newInput = new TextField();
        types_hbox.getChildren().add(newInput);
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }

    public void fun_times(ActionEvent actionEvent) {
        if(is_tool.isSelected()) {
            unit_input.clear();
            unit_input.setText("unit");
            unit_input.setEditable(false);


            for(Node item : types_hbox.getChildren()) {
                ((TextField) item).clear();
                ((TextField) item).setEditable(false);
            }
        } else {
            unit_input.clear();
            unit_input.setEditable(true);

            for(Node item : types_hbox.getChildren()) {
                ((TextField) item).clear();
                ((TextField) item).setEditable(false);
            }
        }
    }
}
