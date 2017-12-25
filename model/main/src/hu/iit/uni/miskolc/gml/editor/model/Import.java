package hu.iit.uni.miskolc.gml.editor.model;



import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public interface Import {

    Document domImport(File inputFile) throws ParserConfigurationException, IOException, SAXException;

}
