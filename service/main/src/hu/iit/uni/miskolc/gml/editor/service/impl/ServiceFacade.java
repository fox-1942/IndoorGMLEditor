package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceException;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceManagerService;
import hu.iit.uni.miskolc.gml.editor.model.ImportExport;
import net.opengis.indoorgml.core.v_1_0.CellSpaceType;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import java.io.File;

/**
 * Created by fox on 2017.08.16..
 */
public class ServiceFacade {
    private CellSpaceManagerService cellSpaceManagerService ;
    private ImportExport importExport= new ImportExportImpl();


    public ServiceFacade(CellSpaceManagerService cellSpaceManagerService) {
        this.cellSpaceManagerService = cellSpaceManagerService;
    }


    /**
     CELLSPACEMANAGER METHODS
     asdsdffgh
     */
    public void marshal(File outputfile) throws CellSpaceException {
        cellSpaceManagerService.marshal(outputfile);
    }

    public CellSpaceType unmarshal2(File inputFile) throws CellSpaceException {
        return cellSpaceManagerService.unmarshal2(inputFile);
    }

    public static IndoorFeaturesType unmarshal(File inputFile) throws CellSpaceException {
        return CellSpaceManagerService.unmarshal(inputFile);
    }

    public IndoorFeaturesType unmarshalmax(File inputFile) {
        return importExport.unmarshalmax(inputFile);
    }

}
