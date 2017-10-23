package hu.iit.uni.miskolc.gml.editor.gui;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceException;
import hu.iit.uni.miskolc.gml.editor.model.ServiceFacade;
import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceManagerFactory;

import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceManagerServiceImpl;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

/**
 * Created by fox on 2017.08.18..
 */
public class MainWindowController {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private ServiceFacade facade;

    //private File inputFile;
    private File outputFile;
    //private String path= "C:" + FILE_SEPARATOR + "Users" + FILE_SEPARATOR + "Tamás" + FILE_SEPARATOR + "Desktop" + FILE_SEPARATOR + "output.xml";
    //private String path = FILE_SEPARATOR + "outputFile" + FILE_SEPARATOR + "outputFile.xml";
    private String path;

    //Nullary Contructor, needed because of java.lang.InstantiationException
    public MainWindowController() throws JAXBException {
        this.facade = facadeSetup("surfaceTitle", "surface2ID", "firstAxis",
                "secondAxis", "SrsName");
    }

    //Constructor
    public MainWindowController(String surfaceTitle, String surface2ID, String firstAxis, String secondAxis, String SrsName) throws JAXBException {
        this.facade = facadeSetup(surfaceTitle, surface2ID, firstAxis, secondAxis, SrsName);
    }

    public ServiceFacade facadeSetup(String surfaceTitle, String surface2ID, String firstAxis, String secondAxis, String SrsName) throws JAXBException {
        CellSpaceManagerServiceImpl param = CellSpaceManagerFactory.creatorCellSpaceManagerServiceImpl(surfaceTitle,
                surface2ID, firstAxis, secondAxis, SrsName);
        return new ServiceFacade(param);
    }

    // public void setInputFile(File inputFile) {this.inputFile = inputFile;}

    public void setPath(String Path) {
        this.path = Path;
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

    private void showSingleFileChooser() {

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println("File selected");
            path =selectedFile.getPath();
            System.out.println(path);
        }
        else {
            System.out.println("Cancelled.");
        }

    }

    public void marshal(ActionEvent event) {
        try {
            showSingleFileChooser();
            facade.marshal(createOutputFile());
        } catch (CellSpaceException e) {
            e.printStackTrace();
        }
    }

    public void unmarshal(ActionEvent event) {
        try {
            showSingleFileChooser();  // path is set.
            File inputFile=new File(path);
            System.out.println(inputFile.exists());
            facade.unmarshal(inputFile);
        } catch (CellSpaceException e) {
            e.printStackTrace();
        }
    }
}