package hu.iit.uni.miskolc.gml.editor.gui;

import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceImportImpl;
import hu.iit.uni.miskolc.gml.editor.service.impl.ServiceFacade;
import javafx.event.ActionEvent;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.ArrayUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by fox on 2017.08.18..
 */
public class MainWindowController {

    private ServiceFacade facade;
    private File outputFile;
    private String path;
    private CellSpaceImportImpl cellSpaceImport=new CellSpaceImportImpl();


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

    public Group createMeshView() {
        ArrayList<CellSpace> cellSpaceImportList = cellSpaceImport.cellSpaceCreator();
        Group root = new Group();

        ArrayList doubles=new ArrayList<Double>();
            for (CellSpace cp: cellSpaceImportList) {

            for(int j=0;j<cp.getCellSpaceFloorCoordinateArrayList().size();j++){
                //System.out.println("\n" + cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateX() + " " + cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateY());

                    doubles.add(cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateX());
                    doubles.add(cp.getCellSpaceFloorCoordinateArrayList().get(j).getCoordinateY());

                }
                double[] array = ArrayUtils.toPrimitive((Double[]) doubles.toArray(new Double[doubles.size()]));
                Polyline rectangle = new Polyline(array);
                rectangle.setStrokeWidth(0.5);
                rectangle.setStroke(Color.DARKRED);
                root.getChildren().add(rectangle);
            }
        return root;
        }


    public SubScene getSubScene(Point3D rotationAxis) {


//        PerspectiveCamera camera = new PerspectiveCamera(false);
//        camera.setTranslateX(100);
//        camera.setTranslateY(-50);
//        camera.setTranslateZ(300);


//        RotateTransition rt = new RotateTransition(Duration.seconds(2), camera);
//        rt.setCycleCount(Animation.INDEFINITE);
//        rt.setFromAngle(-10);
//        rt.setToAngle(10);
//        rt.setAutoReverse(true);
//        rt.setAxis(rotationAxis);
//        rt.play();
//        PointLight redLight = new PointLight(Color.RED);
//        redLight.setTranslateX(0);
//        redLight.setTranslateY(-100);
//        redLight.setTranslateZ(0);

        Group root=createMeshView();
//        root.setRotationAxis(Rotate.X_AXIS);
//        root.setRotate(30);

        root.setTranslateX(500);
        root.setTranslateY(200);
        root.setScaleX(10);
        root.setScaleY(10);
        root.prefHeight(1000);
        root.prefWidth(1000);
        SubScene ss = new SubScene(root, 1000, 1000, true, SceneAntialiasing.DISABLED);
        // ss.setCamera(camera);
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