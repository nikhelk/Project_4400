package com.example.brand_new_4400;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.example.brand_new_4400.HelloApplication.connection;

public class HelloController {


    public Hyperlink hp_create;
    @FXML
    private TextField input_login_password;

    @FXML
    private Button login_button;

    @FXML
    private TextField input_login_email;

    @FXML
    private Label login_title;

    @FXML
    void validate_login_info(ActionEvent event) throws Exception {

        //String check_email_password = "SELECT Email, Pword FROM USERS WHERE (USERS.Email = ? AND USERS.Pword = ?)";



        String check_email_password = "SELECT * FROM USERS WHERE (USERS.Email = " +
                "\"" + input_login_email.getText() + "\"" + " AND USERS.Pword = " + "\"" + input_login_password.getText() + "\"" + ")";

        PreparedStatement check_email_password_statement =
                connection.prepareStatement(check_email_password);

//        check_email_password_statement.setString(1,to_be_checked.email);
//        check_email_password_statement.setString(2,to_be_checked.password);
        ResultSet rs = check_email_password_statement.executeQuery(check_email_password);

        if(!rs.next()) {

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("wrong_login_info.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            // set up the stage
            HelloApplication.showPage(scene);
        }
        else {
            HelloApplication.currentUser = new User(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4));
            HelloApplication.loadWireFrame("wf3.fxml");
        }

    }

    public void go_to_creation_page(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("account_creation_page.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        // set up the stage
        HelloApplication.showPage(scene);

    }
}
