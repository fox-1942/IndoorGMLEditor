package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceException;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceManagerService;
import hu.iit.uni.miskolc.gml.editor.model.ImportExport;
import net.opengis.indoorgml.core.v_1_0.CellSpaceType;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by fox on 2017.08.16..
 */
public class ServiceFacade {

    private ImportExport importExport= new ImportExportImpl();


    public ServiceFacade(CellSpaceManagerService cellSpaceManagerService) {

    }
    public ServiceFacade() {

    }


//    public CellSpaceType unmarshal2(File inputFile) throws CellSpaceException {
//        return cellSpaceManagerService.unmarshal2(inputFile);
//    }

    public IndoorFeaturesType unmarshalmax(File inputFile) {

        return importExport.unmarshalmax(inputFile);
    }

    public void marshalMax(File outputFile) throws JAXBException {
        importExport.marshalMax(outputFile);
    }
}
