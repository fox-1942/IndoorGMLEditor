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
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            // root elements
            Element rootElement = doc.createElementNS(CoreNS, "IndoorFeatures");
            doc.appendChild(rootElement);

            Element primalSpaceFeatures = doc.createElementNS(CoreNS, "primalSpaceFeatures");
            rootElement.appendChild(primalSpaceFeatures);

            Element bigPrimalSpaceFeatures = doc.createElementNS(CoreNS, "PrimalSpaceFeatures");
            rootElement.appendChild(bigPrimalSpaceFeatures);
            bigPrimalSpaceFeatures.setTextContent(cellSpaces.get(0).getParentFloor());


            //putting cellspace coordinates into each cellspace

            for (CellSpace cp:cellSpaces) {
                Element cellSpaceMember=doc.createElementNS(CoreNS, "cellSpaceMember");
                bigPrimalSpaceFeatures.appendChild(cellSpaceMember);

                bigPrimalSpaceFeatures.appendChild(createCellSpaceMember(cp.getParentFloor(),cp.getCellSpaceName(),
                        cp.getCellSpaceFloorCoordinateArrayList(),cp.getCellSpaceCeilingCoordinatesArrayList()));

            }


            //for output to file, console
            TransformerFactory transformFactory = TransformerFactory.newInstance();
            Transformer transformer = transformFactory.newTransformer();

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

    private Node createCellSpaceMember(String parentFloor, String cellSpaceName, ArrayList<CellSpaceCoordinate> cpFloor, ArrayList<CellSpaceCoordinate> cpCeiling) {
        Element cellSpace=doc.createElementNS(CoreNS, "cellSpaceMember");

        Element metaDataProperty=doc.createElementNS(GmlNS,"metaDataProperty");
        cellSpace.appendChild(metaDataProperty);

        Element genericMetaData=doc.createElementNS(GmlNS,"GenericMetaData");
        genericMetaData.setTextContent(cellSpaceName);
        metaDataProperty.appendChild(genericMetaData);



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
        polygonPatch.appendChild(interior);

        Element linearRing=doc.createElementNS(GmlNS,"LinearRing");
        cellSpace.appendChild(linearRing);

























        return node;
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
