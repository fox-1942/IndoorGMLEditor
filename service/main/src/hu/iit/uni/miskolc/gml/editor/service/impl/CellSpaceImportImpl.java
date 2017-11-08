package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceImport;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CellSpaceImportImpl implements CellSpaceImport {


    public CellSpaceImportImpl() {
    }

    /**
     * The method below creates CellspaceImpl object, by reading the .gml file with DOM parser. After reading the necessary
     * attributes and coordinates in, CellspaceImpl object is created. So we get a cellspace. Oh yeah, baby!
     */
    @Override
    public CellSpace cellSpaceCreator(){
        ImportImpl importImpl=new ImportImpl();
        try {
            Document doc= importImpl.domImport();

            String ParentFloor = doc.getElementsByTagNameNS
                    ("http://www.opengis.net/indoorgml/1.0/core","PrimalSpaceFeatures").item(0).
                    getAttributes().getNamedItemNS("http://www.opengis.net/gml/3.2","id").getTextContent();

            NodeList cellSpaceNodeList=doc.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/core", "CellSpace");



            //Variables used in the for cycle.
            String CellSpacename;
            ArrayList<CellSpaceCeilingCoordinateImpl> cellSpaceCeilingCoordinateArrayList = null;
            ArrayList<CellSpaceFloorCoordinateImpl>   cellSpaceFloorCoordinateArrayList;
            ArrayList<CellSpaceImpl> cellSpaceImplArrayList = null;

            for(int i=0; i<cellSpaceNodeList.getLength() ;i++){
                CellSpacename=cellSpaceNodeList.item(i).
                        getAttributes().getNamedItemNS("http://www.opengis.net/gml/3.2","id").getTextContent();

                NodeList nodeList = cellSpaceNodeList.item(i).
                        getAttributes().getNamedItemNS("http://www.opengis.net/gml/3.2","gml:LinearRing").getChildNodes();

                nodeList.item(0).getChildNodes().


                cellSpaceImplArrayList.get(i)=new CellSpaceImpl(ParentFloor,CellSpacename,        //Creating CellSpaceImpl object.
                        cellSpaceCeilingCoordinateArrayList,cellSpaceFloorCoordinateArrayList);
            }



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
