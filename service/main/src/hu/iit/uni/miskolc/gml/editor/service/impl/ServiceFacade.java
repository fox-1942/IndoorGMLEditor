package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.*;



import java.io.File;

import java.util.ArrayList;

/**
 * Created by fox on 2017.08.16..
 */
public class ServiceFacade {

    private Export indoorGMLexport = new ExportImpl();

    private CellSpaceImportImpl cellSpaceImport = new CellSpaceImportImpl();

    public ServiceFacade() {
    }

    public void domExport(File exportedGml, ArrayList<CellSpace> cellSpaces) {
        indoorGMLexport.export(exportedGml, cellSpaces);
    }


    public ArrayList<CellSpace> cellSpaceCreator(File inputFile) {
        return cellSpaceImport.cellSpaceCreator(inputFile);
    }
}


