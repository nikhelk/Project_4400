package com.example.brand_new_4400;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.brand_new_4400.HelloApplication.connection;

public class CreateAccountController {

    public Button back_button;
    @FXML
    private TextField input_create_email;

    @FXML
    private Label login_title;

    @FXML
    private TextField input_create_password;

    @FXML
    private Button create_account_button;

    @FXML
    private TextField input_create_name;

    @FXML
    private TextField input_create_address;

    public void validate_and_load_into_db(ActionEvent actionEvent) throws SQLException, IOException {
            String flightInsert = "INSERT INTO USERS VALUES (?,?,?,?)";

            PreparedStatement flightPreparedStatement =
                    connection.prepareStatement(flightInsert);

            flightPreparedStatement.setString(1,input_create_email.getText());
            flightPreparedStatement.setString(2, input_create_password.getText());
            flightPreparedStatement.setString(3, input_create_name.getText());
            flightPreparedStatement.setString(4, input_create_address.getText());
            try {
                flightPreparedStatement.executeUpdate();
                flightPreparedStatement.close();
                HelloApplication.currentUser = new User(input_create_email.getText(),input_create_password.getText(),input_create_name.getText(),input_create_address.getText());

                Statement flightStatement = connection.createStatement();
                String flightCreate = "SELECT Email FROM USERS";
                ResultSet rs = flightStatement.executeQuery(flightCreate);

                HelloApplication.loadWireFrame("wf3.fxml");
            } catch (java.sql.SQLIntegrityConstraintViolationException DuplicateKey) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("email_already_exists.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                // set up the stage
                HelloApplication.showPage(scene);
            }
            catch (SQLException ex) {
                if(ex.getMessage().equals("Check constraint 'users_chk_1' is violated.")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("invalid_email_input.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    // set up the stage
                    HelloApplication.showPage(scene);
                } else if(ex.getMessage().equals("Check constraint 'users_chk_2' is violated.")) {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("password_too_short.fxml"));
                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    // set up the stage
                    HelloApplication.showPage(scene);
                }
                else {
                    ex.printStackTrace();
                }
            }



    }

    public void log_off(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
