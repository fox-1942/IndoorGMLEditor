package hu.iit.uni.miskolc.gml.editor.model;

import net.opengis.indoorgml.core.v_1_0.CellSpaceType;

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

    public CellSpaceType unmarshal(File inputFile) throws CellSpaceException {
        return cellSpaceManagerService.unmarshal(inputFile);
    }

}
