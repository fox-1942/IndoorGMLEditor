package hu.iit.uni.miskolc.gml.editor.model;

import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import javax.xml.bind.JAXBException;
import java.io.File;

public interface ImportExport {

     IndoorFeaturesType unmarshalmax(File inputFile);

     void marshalMax (File outputFile) throws JAXBException;

}
