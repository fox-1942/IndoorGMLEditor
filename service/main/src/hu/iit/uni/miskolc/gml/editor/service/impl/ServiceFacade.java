package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.*;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by fox on 2017.08.16..
 */
public class ServiceFacade {

    private Export indoorGMLexport = new ExportImpl();

    private Import indoorGMLImport = new ImportImpl();

    private CellSpaceImportImpl cellSpaceImport = new CellSpaceImportImpl();

    public ServiceFacade() {
    }

    public void domExport(File exportedGml, ArrayList<CellSpace> cellSpaces) {
        indoorGMLexport.export(exportedGml, cellSpaces);
    }

    public void domImport() throws IOException, SAXException, ParserConfigurationException {
        indoorGMLImport.domImport();
    }

    public ArrayList<CellSpace> cellSpaceCreator() {
        return cellSpaceImport.cellSpaceCreator();
    }
}


