package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.Export;
import hu.iit.uni.miskolc.gml.editor.model.Import;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by fox on 2017.08.16..
 */
public class ServiceFacade {

    private Export export = new ExportImpl();

    private Import indoorGMLImport=new ImportImpl();

    public ServiceFacade() {
    }


    public IndoorFeaturesType unmarshalmax(File inputFile) {

        return indoorGMLImport.unmarshalmax(inputFile);
    }

    public void marshalMax(File outputFile) throws JAXBException {
        export.marshalMax(outputFile);
    }
}
