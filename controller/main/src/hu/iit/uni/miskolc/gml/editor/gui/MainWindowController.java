package hu.iit.uni.miskolc.gml.editor.gui;

import hu.iit.uni.miskolc.gml.editor.service.impl.ServiceFacade;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by fox on 2017.08.18..
 */
public class MainWindowController {

    @FXML
    private SubScene subScene;

    @FXML
    private MeshView meshView;

    private ServiceFacade facade;
    private File outputFile;
    private String path;



    //Nullary Contructor, needed because of java.lang.InstantiationException
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
    public MeshView createMeshView() {
        float[] points = {50, 0, 0, // v0 (iv0 = 0)
                45, 10, 0, // v1 (iv1 = 1)
                55, 10, 0 // v2 (iv2 = 2)
        };
        float[] texCoords = { 0.5f, 0.5f, // t0 (it0 = 0)
                0.0f, 1.0f, // t1 (it1 = 1)
                1.0f, 1.0f // t2 (it2 = 2)
        };
        int[] faces = {
                0, 0, 2, 2, 1, 1, // iv0, it0, iv2, it2, iv1, it1 (front face)
                0, 0, 1, 1, 2, 2 // iv0, it0, iv1, it1, iv2, it2 (back face)
        };

        // Create a TriangleMesh
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(faces);


        // Create a MeshView
        meshView.setMesh(mesh);
        return meshView;
    }


    public SubScene getSubScene(Point3D rotationAxis) {
        Box box = new Box(100, 100, 100);
        box.setCullFace(CullFace.NONE);
        box.setTranslateX(250);
        box.setTranslateY(100);
        box.setTranslateZ(400);
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(100);
        camera.setTranslateY(-50);
        camera.setTranslateZ(300);

        // Add a Rotation animation to the camera
        RotateTransition rt = new RotateTransition(Duration.seconds(2), camera);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setFromAngle(-10);
        rt.setToAngle(10);
        rt.setAutoReverse(true);
        rt.setAxis(rotationAxis);
        rt.play();
        PointLight redLight = new PointLight(Color.RED);
        redLight.setTranslateX(250);
        redLight.setTranslateY(-100);
        redLight.setTranslateZ(290);
        // If you remove the redLight from the following group,
        // a default head light will be provided by the SubScene.
        Group root = new Group(box, redLight);
        root.setRotationAxis(Rotate.X_AXIS);
        root.setRotate(30);
        SubScene ss = new SubScene(root, 200, 200, true, SceneAntialiasing.BALANCED);
        ss.setCamera(camera);
        return ss;
    }
}











//private static final String FILE_SEPARATOR = System.getProperty("file.separator");
//private String path= "C:" + FILE_SEPARATOR + "Users" + FILE_SEPARATOR + "Tamás" + FILE_SEPARATOR + "Desktop" + FILE_SEPARATOR + "output.xml";
//private String path = FILE_SEPARATOR + "outputFile" + FILE_SEPARATOR + "outputFile.xml";
//    //Constructor
//    public MainWindowController(String surfaceTitle, String surface2ID, String firstAxis, String secondAxis, String SrsName) throws JAXBException {
//        this.facade = facadeSetup(surfaceTitle, surface2ID, firstAxis, secondAxis, SrsName);
//    }
//    public ServiceFacade facadeSetup(String surfaceTitle, String surface2ID, String firstAxis, String secondAxis, String SrsName) throws JAXBException {
//        CellSpaceManagerServiceImpl param = CellSpaceManagerFactory.creatorCellSpaceManagerServiceImpl(surfaceTitle,
//                surface2ID, firstAxis, secondAxis, SrsName);
//        return new ServiceFacade(param);
//    }
// public void setInputFile(File inputFile) {this.inputFile = inputFile;}