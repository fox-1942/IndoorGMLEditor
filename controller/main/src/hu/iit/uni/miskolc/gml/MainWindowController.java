package hu.iit.uni.miskolc.gml;

import hu.iit.uni.miskolc.gml.editor.model.service.CellSpaceException;
import hu.iit.uni.miskolc.gml.editor.model.service.CellSpaceManagerService;
import hu.iit.uni.miskolc.gml.editor.model.service.ServiceFacade;
import hu.iit.uni.miskolc.gml.editor.model.service.impl.CellSpaceManagerFactory;
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

    public void marshal(ActionEvent event) {



        try {
            facade.marshal(createOutputFile());
        } catch (CellSpaceException e) {
            e.printStackTrace();
        }
    }


    public void unmarshal(ActionEvent event) {
        try {
            facade.unmarshal(inputFile);
        } catch (CellSpaceException e) {
            e.printStackTrace();
        }
    }
}