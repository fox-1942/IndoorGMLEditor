package hu.iit.uni.miskolc.gml.editor.gui;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

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

            MainWindowController mainWindowController=fxmlLoader.getController();

            SubScene ySwing = mainWindowController.getSubScene();

            VBox vbox = new VBox( root, ySwing);
            Scene scene = new Scene(vbox, 1000,  800, false);
            primaryStage.setTitle("IIT-IndoorEditor");

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}