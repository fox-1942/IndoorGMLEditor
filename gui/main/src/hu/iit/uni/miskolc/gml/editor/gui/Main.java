package hu.iit.uni.miskolc.gml.editor.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @FXML
    public void start(Stage primaryStage) {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        try {
            Parent root = (Parent)fxmlLoader.load();
            Scene scene = new Scene(root, 2000,  1000, false);
            primaryStage.setTitle("IIT-IndoorEditor");

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}