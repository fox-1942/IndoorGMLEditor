package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;
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

ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArraylist = new ArrayList<CellSpaceCoordinate>();
ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinateArraylist = new ArrayList<CellSpaceCoordinate>();
CellSpaceCoordinateImpl cellSpaceCoordinate;

CellSpaceImpl cellSpace=null;
double x,y,z;

    public CellSpaceImportImpl() {
    }

    /**
     * The method below creates CellspaceImpl object, by reading the .gml file with DOM parser. After reading the necessary
     * attributes and coordinates in, CellspaceImpl object is created. So we get a cellspace. Oh yeah, baby!
     */

    @Override
    public ArrayList<CellSpace> cellSpaceCreator(){
        ImportImpl importImpl=new ImportImpl();






            Document doc= null;
            try {
                doc = importImpl.domImport();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

            String ParentFloor = doc.getElementsByTagNameNS
                    ("http://www.opengis.net/indoorgml/1.0/core","PrimalSpaceFeatures").item(0).
                    getAttributes().getNamedItemNS("http://www.opengis.net/gml/3.2","id").getTextContent();

            String CellSpaceName;
            NodeList cellSpaceMemberNodeList=doc.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/core", "cellSpaceMember");
            int i =0;
            for(i=0; i<cellSpaceMemberNodeList.getLength(); i++) {
                Node currentNode = cellSpaceMemberNodeList.item(i);
                // System.out.println(currentNode.getFirstChild());
                CellSpaceName = currentNode.getFirstChild().
                        getAttributes().getNamedItemNS("http://www.opengis.net/gml/3.2", "id").getTextContent();

                Element currentElement = (Element) currentNode;
                Node floor, ceiling;

                boolean linear = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "LinearRing").getLength() != 0;
                if (linear == false) {
                    floor = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "Arc").item(0);
                    ceiling = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "Arc").item(1);


                } else {
                    floor = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "LinearRing").item(0);
                    ceiling = currentElement.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "LinearRing").item(1);
                }

                cellSpaceFloorCoordinateArraylist.clear();
                NodeList floorPos = floor.getChildNodes();
                for (int j = 0; j < floorPos.getLength(); j++) {

                    String str = floorPos.item(j).getTextContent();
                    String[] splited = str.split("\\s+");

                    x = Double.parseDouble(splited[0]);
                    y = Double.parseDouble(splited[1]);
                    z = Double.parseDouble(splited[2]);
                    //System.out.println(x + " " + y + " " + z);

                    /*
                    Making CellSpacecoordinateImpl objects from x, y, z and after adding to an Arraylist object
                     */
                    cellSpaceCoordinate=new CellSpaceCoordinateImpl(x,y,z);
                    cellSpaceFloorCoordinateArraylist.add(cellSpaceCoordinate);   //Adding coordinates to Arraylist.
                }


                cellSpaceCeilingCoordinateArraylist.clear();
                NodeList ceilingPos = ceiling.getChildNodes();
                for (int j = 0; j <ceilingPos.getLength() ; j++) {

                    String str = ceilingPos.item(j).getTextContent();
                    String[] splited = str.split("\\s+");

                    x = Double.parseDouble(splited[0]);
                    y = Double.parseDouble(splited[1]);
                    z = Double.parseDouble(splited[2]);
                    //System.out.println(x + " " + y + " " + z);

                    /*
                    Making CellSpacecoordinateImpl objects from x, y, z and after adding to an Arraylist object
                     */
                    cellSpaceCoordinate = new CellSpaceCoordinateImpl(x,y,z);
                    //cellSpaceCoordinate.setCoordinateXYZ(x,y,z);
                    cellSpaceCeilingCoordinateArraylist.add(cellSpaceCoordinate);   //Adding coordinates to Arraylist.
                }

                CellSpaceImpl cellSpace = new CellSpaceImpl(ParentFloor, CellSpaceName, cellSpaceCeilingCoordinateArraylist, cellSpaceFloorCoordinateArraylist);
                cellSpace.CellSpacetoString();
            }
            ArrayList<CellSpace> cellSpaceArrayList=new ArrayList<CellSpace>();
            cellSpaceArrayList.add(cellSpace);
        return cellSpaceArrayList;
    }
}
//cellSpace=new CellSpaceImpl(ParentFloor,CellSpaceName,);