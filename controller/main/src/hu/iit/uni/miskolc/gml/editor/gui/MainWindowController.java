package hu.iit.uni.miskolc.gml.editor.gui;

import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceImportImpl;
import hu.iit.uni.miskolc.gml.editor.service.impl.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.input.MouseEvent;


/**
 * Created by fox on 2017.08.18..
 */
public class MainWindowController {

    private ServiceFacade facade;
    private File outputFile;
    private String path;
    private CellSpaceImportImpl cellSpaceImport=new CellSpaceImportImpl();
    private Group root;
    private SubScene ss;

    private ArrayList<Line> lineArrayList=new ArrayList<>();

    @FXML
    private MenuItem del;



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

    public void showSaveDialog(){
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            System.out.println("File selected");
            path = selectedFile.getPath();
            System.out.println(path);
        } else {
            System.out.println("Cancelled.");
        }
    }

    public void marshal(ActionEvent event) throws JAXBException {
            showSaveDialog();   //Path is set.
            facade.marshalMax(createOutputFile());
    }


    //-------------------------------------------------------------------------------

    public void showSingleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println("File selected");
            path = selectedFile.getPath();
            System.out.println(path);
        } else {
            System.out.println("Cancelled.");
        }
    }


    //-------------------------------------------------------------------------------

    public void unmarshal(ActionEvent event) {
        try {
            showSingleFileChooser();  // Path is set.
            File inputFile = new File(path);
            facade.unmarshalmax(inputFile);
            System.out.println(inputFile.exists());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    //--------------------------------------------------------------------------------

    public void drawGmlFile() throws ParserConfigurationException, SAXException, IOException {
            //showSingleFileChooser();  // Path is set.
            //File inputFile = new File(path);
            facade.domImport();
           // System.out.println(inputFile.exists());
        }

    //--------------------------------------------------------------------------------


    public void readGMLFile() throws ParserConfigurationException, SAXException, IOException {
        //showSingleFileChooser();  // Path is set.
        //File inputFile = new File(path);
        facade.cellSpaceCreator();
        // System.out.println(inputFile.exists());
    }

    //--------------------------------------------------------------------------------


    // Creating circle -------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------


    private Circle createCircle(final Color color, double radius, double x, double y) {
        //create a circle with desired name,  color and radius
        final Circle circle = new Circle(x,y,radius);

        //add a shadow effect
        circle.setEffect(new InnerShadow(1, color.brighter()));

        //change a cursor when it is over circle
        circle.setCursor(Cursor.CROSSHAIR);



        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {

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




        return circle;
    }


    // ---------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------

    public Group draw() {


        ArrayList<CellSpace> cellSpaceImportList = cellSpaceImport.cellSpaceCreator();
        Group root = new Group();

        for (CellSpace cp : cellSpaceImportList) {
            ArrayList<Circle> circleArrayList=new ArrayList<Circle>();

            for (int j = 0; j < cp.getCellSpaceFloorCoordinateArrayList().size(); j++) {
                circleArrayList.add(j, createCircle(Color.GREENYELLOW, 0.4, cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateX(),
                        cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateY()));
            }

            for (int i=0;i<circleArrayList.size()-1;i++) {

                Line line = new Line();
                line.setStroke(Color.BLUE);
                line.setStrokeWidth(0.4);

                line.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                            line.setStroke(Color.YELLOWGREEN);
                        if(event.getClickCount() == 2){
                            line.setStroke(Color.BLUE);
                        }
                    }
                });


                if(i==circleArrayList.size()-2){
                    line.startXProperty().bind(circleArrayList.get(i).centerXProperty());
                    line.startYProperty().bind(circleArrayList.get(i).centerYProperty());
                    line.endXProperty().bind(circleArrayList.get(0).centerXProperty());
                    line.endYProperty().bind(circleArrayList.get(0).centerYProperty());
                    root.getChildren().add(circleArrayList.get(i));
                    root.getChildren().add(line);
                    lineArrayList.add(line);
                    break;
                }

                line.startXProperty().bind(circleArrayList.get(i).centerXProperty());
                line.startYProperty().bind(circleArrayList.get(i).centerYProperty());
                line.endXProperty().bind(circleArrayList.get(i+1).centerXProperty());
                line.endYProperty().bind(circleArrayList.get(i+1).centerYProperty());


                root.getChildren().add(circleArrayList.get(i));
                root.getChildren().add(line);


                
                lineArrayList.add(line);

                del.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                       if(line.getFill()==Color.YELLOWGREEN)
                           root.getChildren().
                    }
                });

            }
        }
        return root;
    }

    public SubScene getSubScene() {

        root=draw();
        root.setLayoutX(500);
        root.setLayoutY(300);
        root.setTranslateX(0);
        root.setTranslateY(0);
        root.setScaleX(14);
        root.setScaleY(14);

        ss = new SubScene(root, 1000, 900, false, SceneAntialiasing.BALANCED);

        return ss;
    }





}