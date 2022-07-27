package com.example.brand_new_4400;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class HelloApplication extends Application {
    static Connection connection;
    static User currentUser;
    static Recipe currentRecipe;
    static int currentRunID;
    static java.sql.Date currentGRDate;
    static Stage global_primary_stage;
    static boolean isHomeChef = false;
    static boolean isComingFromWf7 = false;

    @Override
    public void start(Stage primaryStage) throws IOException {
        global_primary_stage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void showPage(Scene scene) {
        global_primary_stage.setTitle("Hello!");
        global_primary_stage.setScene(scene);
        global_primary_stage.show();
    }

    public static void loadWireFrame(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
        Scene scene = new Scene(fxmlLoader.load());
        global_primary_stage.setTitle("Hello!");
        global_primary_stage.setScene(scene);
        global_primary_stage.show();

    }
    public static void logOut() throws IOException {
        currentUser = null;
        currentRecipe = null;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        global_primary_stage.setTitle("Hello!");
        global_primary_stage.setScene(scene);
        global_primary_stage.show();

    }
    public static Connection getConnection() throws Exception {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/phase2_4400";
            String username = "root";
            String password = "password123";
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        } catch(Exception e){System.out.println(e);}
        return null;
    }
    public static void main(String[] args) throws Exception {
        connection = getConnection();
        String dropDiff = "DROP VIEW IF EXISTS DIFF";
        String dropReq = "DROP VIEW IF EXISTS REQ";
        String dropPC = "DROP VIEW IF EXISTS PC";
        String bruh = "DROP SCHEMA IF EXISTS phase2_4400";
        String bruh2 = "CREATE SCHEMA phase2_4400";
        String bruh3 = "USE phase2_4400";
        connection.createStatement().executeUpdate(dropDiff);
        connection.createStatement().executeUpdate(dropReq);
        connection.createStatement().executeUpdate(dropPC);
//        connection.createStatement().executeUpdate(bruh);
//        connection.createStatement().executeUpdate(bruh2);
//        connection.createStatement().executeUpdate(bruh3);

        launch(args);
    }
}