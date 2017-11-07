package hu.iit.uni.miskolc.gml.editor.model;

import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface Import {

    IndoorFeaturesType unmarshalmax(File inputFile);

    void drawGmlFile(File inputFile) throws ParserConfigurationException, IOException, SAXException;

}
