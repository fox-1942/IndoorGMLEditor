package hu.iit.uni.miskolc.gml.editor.model;

import javax.xml.bind.JAXBException;
import java.io.File;

public interface Export {

     void marshalMax (File outputFile) throws JAXBException;



}
