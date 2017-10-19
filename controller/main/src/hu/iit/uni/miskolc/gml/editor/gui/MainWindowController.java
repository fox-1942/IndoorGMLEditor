package hu.iit.uni.miskolc.gml.editor.gui;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceException;
import hu.iit.uni.miskolc.gml.editor.model.ServiceFacade;
import hu.iit.uni.miskolc.gml.editor.service.impl.CellSpaceManagerFactory;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

/**
 * Created by fox on 2017.08.18..
 */
public class MainWindowController {


    private Text actionStatus;

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private ServiceFacade facade;

    private File inputFile;
    private File outputFile;
    //private String Path= "C:" + FILE_SEPARATOR + "Users" + FILE_SEPARATOR + "Tamás" + FILE_SEPARATOR + "Desktop" + FILE_SEPARATOR + "output.xml";
    private String Path = FILE_SEPARATOR + "outputFile" + FILE_SEPARATOR + "outputFile.xml";

    //Nullary Contructor, needed because of java.lang.InstantiationException
    public MainWindowController() {
    }

    //Constructor
    public MainWindowController(String surfaceTitle, String surface2ID, String firstAxis, String secondAxis, String SrsName) throws JAXBException {
        this.facade = new ServiceFacade(CellSpaceManagerFactory.creatorCellSpaceManagerServiceImpl(surfaceTitle, surface2ID, firstAxis, secondAxis, SrsName));
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }

    public File createOutputFile() {
        outputFile = new File(Path);
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
            actionStatus.setText("File selected");
            Path=selectedFile.getPath();
        }
        else {
            actionStatus.setText("File selection cancelled.");
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
            showSingleFileChooser();
            facade.unmarshal(inputFile);
        } catch (CellSpaceException e) {
            e.printStackTrace();
        }
    }


}