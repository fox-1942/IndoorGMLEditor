package hu.iit.uni.miskolc.gml.editor.service.impl;


import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;
import hu.iit.uni.miskolc.gml.editor.model.Export;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;


public class ExportImpl implements Export {

    private Document doc;
    private String CoreNS = "http://www.opengis.net/indoorgml/1.0/core";
    private String GmlNS="http://www.opengis.net/gml/3.2";

    @Override
    public void export(File exportedGml, ArrayList<CellSpace> cellSpaces) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            // root elements
            Element rootElement = doc.createElementNS(CoreNS, "core:IndoorFeatures");
            rootElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:gml","http://www.opengis.net/gml/3.2");

            doc.appendChild(rootElement);



            Element primalSpaceFeatures = doc.createElementNS(CoreNS, "core:primalSpaceFeatures");
            rootElement.appendChild(primalSpaceFeatures);

            Element bigPrimalSpaceFeatures = doc.createElementNS(CoreNS, "core:PrimalSpaceFeatures");


            bigPrimalSpaceFeatures.setAttributeNS(CoreNS,"gml:id",cellSpaces.get(0).getParentFloor());
            primalSpaceFeatures.appendChild(bigPrimalSpaceFeatures);

            //putting cellspace coordinates into each cellspace

            for (int j=0;j<2;j++) {

                CellSpace cp=cellSpaces.get(j);
                bigPrimalSpaceFeatures.appendChild(createCellSpaceMember(cp.getParentFloor(),cp.getCellSpaceName(),
                        cp.getCellSpaceFloorCoordinateArrayList(),cp.getCellSpaceCeilingCoordinatesArrayList()));

            }


            //for output to file, console
            TransformerFactory transformFactory = TransformerFactory.newInstance();

            Transformer transformer = transformFactory.newTransformer();
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");  // Formatting nicely
            DOMSource source = new DOMSource(doc);

            //writing to console and to file
            StreamResult console = new StreamResult(System.out);
            StreamResult file = new StreamResult(exportedGml);

            //writing data
            transformer.transform(source, console);
            transformer.transform(source, file);
            System.out.println("DONE");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public Node createCellSpaceMember(String parentFloor, String cellSpaceName, ArrayList<CellSpaceCoordinate> cpFloor, ArrayList<CellSpaceCoordinate> cpCeiling) {
        Element cellSpaceMember = doc.createElementNS(CoreNS, "core:cellSpaceMember");
        Element cellSpace = doc.createElementNS(CoreNS, "core:cellSpace");
        cellSpaceMember.appendChild(cellSpace);

        Element metaDataProperty = doc.createElement("gml:metaDataProperty");
        cellSpace.appendChild(metaDataProperty);

        Element genericMetaData = doc.createElement("gml:GenericMetaData");
        genericMetaData.setTextContent(cellSpaceName);
        metaDataProperty.appendChild(genericMetaData);

/*

        Element cellSpaceGeometry=doc.createElementNS(CoreNS,"cellSpaceGeometry");
        cellSpace.appendChild(cellSpaceGeometry);

        Element geometry3d=doc.createElementNS(CoreNS,"Geometry3d");
        cellSpaceGeometry.appendChild(geometry3d);

        Element solid=doc.createElementNS(GmlNS,"Solid");
        geometry3d.appendChild(solid);
        Element interior=doc.createElementNS(GmlNS,"interior");
        solid.appendChild(interior);


        Element shell=doc.createElementNS(GmlNS,"Shell");
        interior.appendChild(shell);

        Element surfaceMember=doc.createElementNS(GmlNS,"surfaceMember");
        shell.appendChild(surfaceMember);


        Element polyhedralSurface=doc.createElementNS(GmlNS,"PolyhedralSurface");
        surfaceMember.appendChild(polyhedralSurface);

        Element patches=doc.createElementNS(GmlNS,"patches");
        polyhedralSurface.appendChild(patches);


        Element polygonPatch=doc.createElementNS(GmlNS,"polygonPatch");
        patches.appendChild(polygonPatch);

        Element interior2=doc.createElementNS(GmlNS,"interior2");


        if( cpFloor.size()==5){
            Element linearRing=doc.createElementNS(GmlNS,"LinearRing");

            for(int i=0;i<2;i++){
                polygonPatch.appendChild(interior2);
                interior2.appendChild(linearRing);
            }
        }

        else {

            Element arc=doc.createElementNS(GmlNS,"Arc");

            for(int i=0;i<2;i++){
                polygonPatch.appendChild(interior2);
                interior2.appendChild(arc);
            }
        }
*/
        return cellSpaceMember;
    }




//    public void marshalMax(File outputFile) throws JAXBException {
//        //Exporting method
//
//        JAXBContext jaxbContext = JAXBContext.newInstance("net.opengis.indoorgml.core.v_1_0"
//                + ":net.opengis.gml.v_3_2_1");
//
//        Marshaller marshaller = jaxbContext.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.opengis.net/indoorgml/1.0/core http://schemas.opengis.net/indoorgml/1.0/indoorgmlcore.xsd");
//        try {
//            marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new IndoorGMLNameSpaceMapper());
//        } catch (PropertyException e) {
//            e.printStackTrace();
//        }
//
//        JAXBElement<IndoorFeaturesType> je = jaxbConvertor.getJAXBElement();
//
//        marshaller.marshal(je, outputFile); //JAXBElement to XML file
//
//    }

}
