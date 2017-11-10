package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceImport;
import org.ejml.alg.block.BlockInnerTriangularSolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CellSpaceImportImpl implements CellSpaceImport {

ArrayList<CellSpaceCoordinateImpl> cellSpaceFloorCoordinateArraylist;
ArrayList<CellSpaceCoordinateImpl> cellSpaceCeilingCoordinateArraylist;


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

            String CellSpacename;
            NodeList cellSpaceMemberNodeList=doc.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/core", "cellSpaceMember");
            int i =0;
            for(i=0; i<cellSpaceMemberNodeList.getLength(); i++){
                Node currentNode = cellSpaceMemberNodeList.item(i);
               // System.out.println(currentNode.getFirstChild());
                CellSpacename=currentNode.getFirstChild().
                        getAttributes().getNamedItemNS("http://www.opengis.net/gml/3.2","id").getTextContent();

                Element currentElement = (Element) currentNode;
                Node floor,ceiling;

                    boolean linear = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "LinearRing").getLength()!=0;
                    if(linear == false){
                         floor = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "Arc").item(0);
                         ceiling = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "Arc").item(1);
                    }
                    else{
                        floor = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "LinearRing").item(0);
                         ceiling = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "LinearRing").item(1);
                    }
                    NodeList floorpos = floor.getChildNodes();
                    for (int j = 0; j < floorpos.getLength(); j++) {

                        String str=floorpos.item(j).getTextContent();
                        String[] splited = str.split("\\s+");





                        // System.out.println(floorpos.item(j).getTextContent() + "\n");

                    }






                System.out.println("-------");


//                cellSpaceImplArrayList.get(i)=new CellSpaceImpl(ParentFloor,CellSpacename,        //Creating CellSpaceImpl object.
//                        cellSpaceCeilingCoordinateArrayList,cellSpaceFloorCoordinateArrayList);
            }

            System.out.println("Max i index: "+i);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        CellSpace cellSpace=null;

    return cellSpace;
}



}
