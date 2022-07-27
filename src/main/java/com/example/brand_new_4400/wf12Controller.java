package com.example.brand_new_4400;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class wf12Controller {
    public Button go_back;

    public void go_to_wf3(ActionEvent actionEvent) throws IOException {
        HelloApplication.isHomeChef = false;
        HelloApplication.loadWireFrame("wf3.fxml");
    }

    public void go_to_wf13(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf13.fxml");
    }

    public void go_to_wf14(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf14.fxml");
    }

    public void go_to_wf18(ActionEvent actionEvent) throws IOException {
        HelloApplication.loadWireFrame("wf18.fxml");
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        HelloApplication.logOut();
    }
}
