package com.example.brand_new_4400;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class wf3Controller {
    public Button log_out_button;
    public Button home_chef_select;
    public Button contributor_select;


    public void log_out(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }

    public void go_to_wf12(ActionEvent actionEvent) throws IOException {
        HelloApplication.isHomeChef = true;
        HelloApplication.loadWireFrame("wf12.fxml");
    }

    public void go_to_wf4(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf4.fxml");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
