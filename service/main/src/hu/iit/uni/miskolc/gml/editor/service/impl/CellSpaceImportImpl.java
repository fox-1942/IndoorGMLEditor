package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceImport;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class CellSpaceImportImpl implements CellSpaceImport {


    public CellSpaceImportImpl() {
    }


    /**
     * The method below creates cellspace object. Oh yeah, baby!
     */
    public CellSpace CellSpaceCreator(){
        ImportImpl importImpl=new ImportImpl();
        try {
            org.w3c.dom.Document doc= importImpl.domImport();
            System.out.println(doc.getElementsByTagNameNS
                    ("http://www.opengis.net/indoorgml/1.0/core","PrimalSpaceFeatures").item(0).
                    getAttributes().getNamedItemNS("http://www.opengis.net/gml/3.2","id").getTextContent());

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        CellSpace cellSpace=null;

    return cellSpace;
}



}
