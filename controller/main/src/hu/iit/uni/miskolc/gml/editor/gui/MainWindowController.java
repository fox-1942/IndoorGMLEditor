package hu.iit.uni.miskolc.gml.editor.gui;

import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;
import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceImportImpl;
import hu.iit.uni.miskolc.gml.editor.service.impl.ServiceFacade;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

import javafx.scene.*;

import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;

import javafx.util.Pair;

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

    @FXML
    private ScrollPane root;

    @FXML
    private Pane pane;

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



    public Circle createCircle(double x, double y) {
        //create a circle with desired name,  color and radius
        final Circle circle = new Circle(x, y, 0.5, Color.YELLOWGREEN);

        //add a shadow effect
        circle.setEffect(new InnerShadow(1, Color.GREEN.brighter()));

        //change a cursor when it is over circle
        circle.setCursor(Cursor.CLOSED_HAND);

        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                if(me.getX()<=pane.getWidth() && me.getX()>=pane.getMinWidth() &&
                        me.getY()<=pane.getHeight() && me.getY()>=pane.getMinHeight()) {
                    int[] indexes = ownerCellSpaceOfCircle(circle);
                    CellSpaceCoordinate currentCellLast = cellSpaces.get(indexes[0]).getCellSpaceFloorCoordinateArrayList().
                            get(cellSpaces.get(indexes[0]).getCellSpaceFloorCoordinateArrayList().size() - 1);

                    //If the last coordinate of the specific cellspace is moved, moved the first coordinate as well to that point,
                    //because in GML first and last coordinate is the same.
                    if (currentCellLast.getCoordinateX() == circle.getCenterX() &&
                            currentCellLast.getCoordinateY() == circle.getCenterY()) {

                        cellSpaces.get(indexes[0]).getCellSpaceFloorCoordinateArrayList().
                                get(0).setCoordinateXYZ(me.getX(), me.getY(), 3.3);
                        cellSpaces.get(indexes[0]).getCellSpaceCeilingCoordinatesArrayList().
                                get(0).setCoordinateXYZ(me.getX(), me.getY(), 5.05);
                    }

                    //Modify the coordinate of the cellSpace to the dragged point.
                    cellSpaces.get(indexes[0]).getCellSpaceFloorCoordinateArrayList().
                            get(indexes[1]).setCoordinateXYZ(me.getX(), me.getY(), 3.3);
                    cellSpaces.get(indexes[0]).getCellSpaceCeilingCoordinatesArrayList().
                            get(indexes[1]).setCoordinateXYZ(me.getX(), me.getY(), 5.05);

                    circle.setCenterX(me.getX());
                    circle.setCenterY(me.getY());
                }
            }
        });

        circle.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if(me.isControlDown()) {
                    for (CellSpace cp : cellSpaces) {
                        for (int i = 0; i < cp.getCellSpaceFloorCoordinateArrayList().size(); i++) {
                            double cpFloorCoordX = cp.getCellSpaceFloorCoordinateArrayList().get(i).getCoordinateX();
                            double cpFloorCoordY = cp.getCellSpaceFloorCoordinateArrayList().get(i).getCoordinateY();
                            double cpCeilingCoordX = cp.getCellSpaceCeilingCoordinatesArrayList().get(i).getCoordinateX();
                            double cpCeilingCoordY = cp.getCellSpaceCeilingCoordinatesArrayList().get(i).getCoordinateY();

                            cp.getCellSpaceFloorCoordinateArrayList().get(i).setCoordinateXYZ(cpFloorCoordX + (me.getX() - circle.getCenterX()),
                                    cpFloorCoordY + (me.getY() - circle.getCenterY()), 3.3);
                            cp.getCellSpaceCeilingCoordinatesArrayList().get(i).setCoordinateXYZ(cpFloorCoordX + (me.getX() - circle.getCenterX()),
                                    cpFloorCoordY + (me.getY() - circle.getCenterY()), 5.05);
                        }
                    }
                    me.consume();
                    drawFloorPlan();
                }

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
                    drawFloorPlan();
                }
            }
        });


        return circle;
    }


    public void drawFloorPlan() {

        if (drawnFromFile == false) {
            //Reading and creating CellSpace objects from file.

            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

            cellSpaceImport = new CellSpaceImportImpl();
            cellSpaces = facade.cellSpaceCreator(selectedFile);
            drawnFromFile = true;
        }
        else{
            pane.getChildren().clear();
        }

        for (CellSpace cp : cellSpaces) {
            circleArrayList = new ArrayList<Circle>();

            for (int j = 0; j < cp.getCellSpaceFloorCoordinateArrayList().size(); j++) {
                circleArrayList.add(j, createCircle(cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateX(),
                        cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateY()));
            }

            for (int i = 0; i < circleArrayList.size() - 1; i++) {

                Line line=createLine();

                if (i == circleArrayList.size() - 2) {
                    line.startXProperty().bind(circleArrayList.get(i).centerXProperty());
                    line.startYProperty().bind(circleArrayList.get(i).centerYProperty());
                    line.endXProperty().bind(circleArrayList.get(0).centerXProperty());
                    line.endYProperty().bind(circleArrayList.get(0).centerYProperty());
                    pane.getChildren().add(circleArrayList.get(i));
                    pane.getChildren().add(line);
                    break;
                }

                line.startXProperty().bind(circleArrayList.get(i).centerXProperty());
                line.startYProperty().bind(circleArrayList.get(i).centerYProperty());
                line.endXProperty().bind(circleArrayList.get(i + 1).centerXProperty());
                line.endYProperty().bind(circleArrayList.get(i + 1).centerYProperty());


                pane.getChildren().add(circleArrayList.get(i));
                pane.getChildren().add(line);
            }
        }
    }

    public void openDrawFile(){
        drawnFromFile=false;
        rootCreator();
        drawFloorPlan();
    }

    public void newFile(){
        rootCreator();
        drawnFromFile=true;
    }

    public void exit(){
        Stage stage = (Stage) root.getScene().getWindow();
        // do what you have to do
        stage.close();
    }


    public ScrollPane rootCreator() {
        cellSpaces=new ArrayList<CellSpace>();
        pane=new Pane();
        pane.setScaleY(14.0);
        pane.setScaleX(14.0);
        pane.setPrefSize(400,400);
        pane.setStyle("-fx-background-color: #a4a2a5");


        Group group=new Group();
        group.getChildren().add(pane);

        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        root.setContent(group);

        root.setStyle("-fx-background-color: #000000");
        return root;
    }

    public Line createLine(){
        Line line = new Line();
        line.setStroke(Color.DARKGREEN);
        line.setStrokeWidth(0.4);
        return line;
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
        pane.setCursor(Cursor.CROSSHAIR);
        circlesRealTime = new ArrayList<Circle>();
        cellSpaceReady = false;

        EventHandler<MouseEvent> clickOnFirstCircle = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent firstCircle) {
                if (cellSpaceReady == true) {
                    return;
                }

                circlesRealTime.get(0).toFront();

                Line line = createLine();

                line.startXProperty().bind(circlesRealTime.get(circlesRealTime.size() - 1).centerXProperty());
                line.startYProperty().bind(circlesRealTime.get(circlesRealTime.size() - 1).centerYProperty());
                line.endXProperty().bind(circlesRealTime.get(0).centerXProperty());
                line.endYProperty().bind(circlesRealTime.get(0).centerYProperty());

                pane.getChildren().add(line);
                cellSpaceReady = true;
                putCellSpaceIntoCellSpaces();

            }
        };


        EventHandler<MouseEvent> clickAndCreateCircle = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent createCell) {
                if (cellSpaceReady == true) {
                    root.removeEventHandler(MouseEvent.ANY,this::handle);
                    return;
                }

                Circle point = createCircle(createCell.getX(), createCell.getY());
                circlesRealTime.add(point);
                pane.getChildren().add(point);

                if (circlesRealTime.size() >= 2) {
                    for (int i = 0; i < circlesRealTime.size() - 1; i++) {


                        Line line = createLine();

                        line.startXProperty().bind(circlesRealTime.get(i).centerXProperty());
                        line.startYProperty().bind(circlesRealTime.get(i).centerYProperty());
                        line.endXProperty().bind(circlesRealTime.get(i + 1).centerXProperty());
                        line.endYProperty().bind(circlesRealTime.get(i + 1).centerYProperty());

                        pane.getChildren().add(line);
                    }
                }

                if (circlesRealTime.size() == 3) {
                    circlesRealTime.get(0).addEventFilter(MouseEvent.MOUSE_CLICKED, clickOnFirstCircle);
                }


            }
        };
        pane.setOnMouseClicked(clickAndCreateCircle);
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
        dialog.initStyle(StageStyle.UNDECORATED);

        // Set the button types: OK and Cancel.
        ButtonType okayButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okayButtonType);

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
        if(cellSpaceReady==true) {
            //Creating CellSpace Coordinates

            ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArrayList = new ArrayList<CellSpaceCoordinate>();
            ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinateArrayList = new ArrayList<CellSpaceCoordinate>();
            for (Circle circle : circlesRealTime) {
                cellSpaceFloorCoordinateArrayList.add(new CellSpaceCoordinate(circle.getCenterX(), circle.getCenterY(), 3.3));
            }
            cellSpaceFloorCoordinateArrayList.add(new CellSpaceCoordinate(circlesRealTime.get(0).getCenterX(),
                  circlesRealTime.get(0).getCenterY(),3.3));

            for (Circle circle : circlesRealTime) {
                cellSpaceCeilingCoordinateArrayList.add(new CellSpaceCoordinate(circle.getCenterX(), circle.getCenterY(), 5.05));
            }
            cellSpaceCeilingCoordinateArrayList.add(new CellSpaceCoordinate(circlesRealTime.get(0).getCenterX(),
                  circlesRealTime.get(0).getCenterY(),5.05));

            CellSpace newCellSpace = new CellSpace(result.get().getKey(), result.get().getValue(), cellSpaceCeilingCoordinateArrayList, cellSpaceFloorCoordinateArrayList);
            cellSpaces.add(newCellSpace);

            flowPane();
            root.setCursor(Cursor.DEFAULT);
        }
    }

    public void exportToGML(){
        File exportedGml = new File(showSaveDialog());
        facade.domExport(exportedGml,cellSpaces);
        System.out.println("Export is ready.");
    }

    public void about(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
        try {
            Parent rootAbout = (Parent) loader.load();

            Scene scene = new Scene(rootAbout, 1130, 665);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setTitle("Information");
            newStage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}