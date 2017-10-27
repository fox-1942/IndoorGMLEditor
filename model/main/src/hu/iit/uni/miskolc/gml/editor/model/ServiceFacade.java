package hu.iit.uni.miskolc.gml.editor.model;

import net.opengis.indoorgml.core.v_1_0.CellSpaceType;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import java.io.File;

/**
 * Created by fox on 2017.08.16..
 */
public class ServiceFacade {
    private CellSpaceManagerService cellSpaceManagerService ;


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

}
