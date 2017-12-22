package hu.iit.uni.miskolc.gml.editor.gui;

import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;
import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceImportImpl;
import hu.iit.uni.miskolc.gml.editor.service.impl.ServiceFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.geometry.Insets;

import javafx.scene.*;

import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.*;

import javafx.util.Pair;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.input.MouseEvent;


public class MainWindowController {

    private ServiceFacade facade;
    private File outputFile;
    private String path;

    ArrayList<Circle> circlesRealTime;

    boolean cellSpaceReady;
    private CellSpaceImportImpl cellSpaceImport;
    private ArrayList<Circle> circleArrayList;
    private ArrayList<CellSpace> cellSpaces;
    private boolean drawnFromFile = false;
    private Group group;


    @FXML
    private BorderPane root;


    public MainWindowController() {
        facade = new ServiceFacade();
    }

    public File createOutputFile() {
        outputFile = new File(path);
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(outputFile.exists() + outputFile.getAbsolutePath());
        return outputFile;
    }

    public String showSaveDialog() {
        String path = "";
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            System.out.println("File selected");
            path = selectedFile.getPath();
            System.out.println(path);
        } else {
            System.out.println("Cancelled.");
        }
        return path;
    }


    //-------------------------------------------------------------------------------

//    public void showSingleFileChooser() {
//
//        FileChooser fileChooser = new FileChooser();
//        File selectedFile = fileChooser.showOpenDialog(null);
//
//        if (selectedFile != null) {
//            System.out.println("File selected");
//            path = selectedFile.getPath();
//            System.out.println(path);
//        } else {
//            System.out.println("Cancelled.");
//        }
//    }

    //--------------------------------------------------------------------------------


    public Circle createCircle(double x, double y) {
        //create a circle with desired name,  color and radius
        final Circle circle = new Circle(x, y, 0.4, Color.YELLOWGREEN);

        //add a shadow effect
        circle.setEffect(new InnerShadow(1, Color.YELLOWGREEN.brighter()));

        //change a cursor when it is over circle
        circle.setCursor(Cursor.DEFAULT);

        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {

                int[] indexes = ownerCellSpaceOfCircle(circle);
                cellSpaces.get(indexes[0]).getCellSpaceFloorCoordinateArrayList().
                        get(indexes[1]).setCoordinateXYZ(me.getX(), me.getY(), 3.3);
                circle.setCenterX(me.getX());
                circle.setCenterY(me.getY());


            }
        });

        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //change the z-coordinate of the circle
                circle.toFront();
            }
        });



        /**
         * When the specified mouse button clicked on the circle, the owner CellSpace object is removed from the floorPlanSubScene.
         */

        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent deleteCell) {

                if (deleteCell.isShiftDown()) {

                    int[] indexes = ownerCellSpaceOfCircle(circle);

                    cellSpaces.remove(cellSpaces.get(indexes[0]));

                    drawFloorPlanSubScene();


                }
            }
        });

        return circle;
    }


    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    public void drawFloorPlanSubScene() {
        group = new Group();
        if (drawnFromFile == false) {

            //Reading and creating CellSpace objects from file.
            cellSpaceImport = new CellSpaceImportImpl();
            cellSpaces = facade.cellSpaceCreator();
            drawnFromFile = true;
        }

        for (CellSpace cp : cellSpaces) {
            circleArrayList = new ArrayList<Circle>();

            for (int j = 0; j < cp.getCellSpaceFloorCoordinateArrayList().size(); j++) {
                circleArrayList.add(j, createCircle(cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateX(),
                        cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateY()));
            }

            for (int i = 0; i < circleArrayList.size() - 1; i++) {

                Line line = new Line();
                line.setStroke(Color.LIGHTSEAGREEN);
                line.setStrokeWidth(0.4);

                if (i == circleArrayList.size() - 2) {
                    line.startXProperty().bind(circleArrayList.get(i).centerXProperty());
                    line.startYProperty().bind(circleArrayList.get(i).centerYProperty());
                    line.endXProperty().bind(circleArrayList.get(0).centerXProperty());
                    line.endYProperty().bind(circleArrayList.get(0).centerYProperty());
                    group.getChildren().add(circleArrayList.get(i));
                    group.getChildren().add(line);
                    break;
                }

                line.startXProperty().bind(circleArrayList.get(i).centerXProperty());
                line.startYProperty().bind(circleArrayList.get(i).centerYProperty());
                line.endXProperty().bind(circleArrayList.get(i + 1).centerXProperty());
                line.endYProperty().bind(circleArrayList.get(i + 1).centerYProperty());


                group.getChildren().add(circleArrayList.get(i));
                group.getChildren().add(line);

            }
        }
        root.getChildren().clear();
        root.setCenter(group);
    }

    public void openDrawFile() {
        rootCreator();
        drawFloorPlanSubScene();
    }


    /**
     * rootCreator contains the things represented by the floorPlanSubScene.
     */

    public BorderPane rootCreator() {

//       root.setPrefSize(500, 500);
        root.setLayoutX(700);
        root.setLayoutY(350);
//        root.setTranslateX(0);
//        root.setTranslateY(0);
        root.setScaleX(14);
        root.setScaleY(14);
        root.toBack();

        root.setStyle("-fx-background-color: #5d5b5e");
        return root;
    }


    /**
     * This method search for the owner CellSpace object of the selected Circle. The second data of the array is the
     * owner CellSpace point of the Circle.
     *
     * @param circle
     * @return
     */

    public int[] ownerCellSpaceOfCircle(Circle circle) {
        int[] indexes = new int[2];
        outerloop:
        for (int i = cellSpaces.size() - 1; i > -1; i--) {
            for (int j = cellSpaces.get(i).getCellSpaceFloorCoordinateArrayList().size() - 1; j > -1; j--) {
                if (cellSpaces.get(i).getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateX() == circle.getCenterX() &&
                        cellSpaces.get(i).getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateY() == circle.getCenterY()) {
                    indexes[0] = i;
                    indexes[1] = j;
                    break outerloop;
                }
            }
        }

        try {
            if (indexes[0] == -1)
                throw new IllegalArgumentException();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Circle does not belongs to CellSpace object.");
        }

        return indexes;
    }


    public void cellSpaceDrawer() {
        circlesRealTime = new ArrayList<Circle>();

        EventHandler<MouseEvent> clickOnFirstCircle = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circlesRealTime.get(0).toFront();

                Line line = new Line();
                line.setStroke(Color.LIGHTSEAGREEN);
                line.setStrokeWidth(0.4);

                line.startXProperty().bind(circlesRealTime.get(circlesRealTime.size() - 1).centerXProperty());
                line.startYProperty().bind(circlesRealTime.get(circlesRealTime.size() - 1).centerYProperty());
                line.endXProperty().bind(circlesRealTime.get(0).centerXProperty());
                line.endYProperty().bind(circlesRealTime.get(0).centerYProperty());

                root.getChildren().add(line);
                cellSpaceReady = true;
                flowPane();
                root.setOnMouseClicked(null);
                return;

            }


        };


        EventHandler<MouseEvent> clickAndCreateCircle = new EventHandler<MouseEvent>() {


            @Override
            public void handle(MouseEvent createCell) {
                if (cellSpaceReady == true) {
                    root.removeEventHandler(MouseEvent.ANY, this);
                    return;
                }

                Circle point = createCircle(createCell.getX(), createCell.getY());
                circlesRealTime.add(point);
                root.getChildren().add(point);

                if (circlesRealTime.size() >= 2) {
                    for (int i = 0; i < circlesRealTime.size() - 1; i++) {


                        Line line = new Line();
                        line.setStroke(Color.LIGHTSEAGREEN);
                        line.setStrokeWidth(0.4);

                        line.startXProperty().bind(circlesRealTime.get(i).centerXProperty());
                        line.startYProperty().bind(circlesRealTime.get(i).centerYProperty());
                        line.endXProperty().bind(circlesRealTime.get(i + 1).centerXProperty());
                        line.endYProperty().bind(circlesRealTime.get(i + 1).centerYProperty());

                        root.getChildren().add(line);
                    }
                }

                if (circlesRealTime.size() == 3) {
                    circlesRealTime.get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, clickOnFirstCircle);
                }


            }
        };

        root.setOnMouseClicked(clickAndCreateCircle);

    }


    /**
     * Reads the data (parenfloor and name of cellspace) of new CellSpace in.
     *
     * @return
     */

    public Optional<Pair<String, String>> readDataOfCellSpace() {

        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Data of new cellspace");
        dialog.setHeaderText("Please fill out the data of new cellspace!");

        // Set the button types: OK and Cancel.
        ButtonType okayButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okayButtonType, ButtonType.CANCEL);

        // Create labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 140, 10, 10));


        TextField parentFloor = new TextField();
        parentFloor.setPromptText("Name of the parent floor");
        TextField cellSpaceName = new TextField();
        cellSpaceName.setPromptText("Name of the cellspace");

        TextField[] textFields = {parentFloor, cellSpaceName};


        //Adding textfields to the GridPane object, making them visible.
        grid.add(new Label("Parent Floor:"), 0, 0);
        grid.add(parentFloor, 1, 0);
        grid.add(new Label("Cellspace name::"), 0, 1);
        grid.add(cellSpaceName, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node Button = dialog.getDialogPane().lookupButton(okayButtonType);
        Button.setDisable(true);


        //Examine whether any of the textfields is empty.
        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                boolean empty = false;
                for (TextField field : textFields)
                    if (field.getText().isEmpty()) {
                        empty = true;
                        break;
                    }
                Button.setDisable(empty);
            }
        };

        cellSpaceName.textProperty().addListener(listener);
        parentFloor.textProperty().addListener(listener);

        //Adding grid to dialog.
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okayButtonType) {
                return new Pair<>(parentFloor.getText(), cellSpaceName.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        System.out.println(result.get().getKey() + " " + result.get().getValue());


        return result;
    }




    public void flowPane() {

    FlowPane pane1=new FlowPane();
    pane1.setHgap(20);
    pane1.setStyle("-fx-background-color:#c9cac0;-fx-padding:10px; -fx-text-fill: #65cd63 " );
    Label lblscene1=new Label("CellSpace is created.");
    pane1.getChildren().add(lblscene1);
    Scene  scene = new Scene(pane1, 200, 100);
    Stage newStage = new Stage();
    newStage.setScene(scene);
    newStage.initModality(Modality.APPLICATION_MODAL);
    newStage.setTitle("Information");
    newStage.showAndWait();

    }

    public void putCellSpaceIntoCellSpaces(){
        Optional<Pair<String, String>> result=readDataOfCellSpace();
        cellSpaceDrawer();
        root.setOnMouseClicked(null);



        //Creating CellSpace Coordinates

        ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArrayList=new ArrayList<CellSpaceCoordinate>();
        ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinateArrayList=new ArrayList<CellSpaceCoordinate>();
        for (Circle circle: circlesRealTime)
        {
            cellSpaceFloorCoordinateArrayList.add(new CellSpaceCoordinate(circle.getCenterX(),circle.getCenterY(),3.3));
        }

        for (Circle circle: circlesRealTime)
        {
            cellSpaceCeilingCoordinateArrayList.add(new CellSpaceCoordinate(circle.getCenterX(),circle.getCenterY(),5.5));
        }

        CellSpace newCellSpace=new CellSpace(result.get().getKey(),result.get().getValue(),cellSpaceFloorCoordinateArrayList,cellSpaceFloorCoordinateArrayList);
        cellSpaces.add(newCellSpace);

        cellSpaceReady=false;
    }

    public void exportToGML(){
        File exportedGml = new File(showSaveDialog());
        facade.domExport(exportedGml,cellSpaces);
        System.out.println("Export is ready.");
    }




}