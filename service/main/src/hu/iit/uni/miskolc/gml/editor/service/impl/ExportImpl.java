package hu.iit.uni.miskolc.gml.editor.service.impl;


import hu.iit.uni.miskolc.gml.editor.model.CellSpace;
import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;
import hu.iit.uni.miskolc.gml.editor.model.Export;

import net.opengis.indoorgml.geometry.LinearRing;
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

    @Override
    public void export(File exportedGml, ArrayList<CellSpace> cellSpaces) {

        try {


            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            // root elements
            Element rootElement = doc.createElementNS(CoreNS, "core:IndoorFeatures");
            rootElement.setAttribute( "xmlns:gml","http://www.opengis.net/gml/3.2");
            rootElement.setAttribute( "xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute( "xsi:schemaLocation","http://www.opengis.net/indoorgml/1.0/core http://schemas.opengis.net/indoorgml/1.0/indoorgmlcore.xsd");
            rootElement.setAttribute( "xsi:schemaLocation","http://www.opengis.net/indoorgml/1.0/core http://schemas.opengis.net/indoorgml/1.0/indoorgmlcore.xsd");

            rootElement.setAttribute( "gml:id","ISS_University_of_Miskolc");


            doc.appendChild(rootElement);


            Element primalSpaceFeatures = doc.createElement( "core:primalSpaceFeatures");
            rootElement.appendChild(primalSpaceFeatures);

            Element bigPrimalSpaceFeatures = doc.createElement( "core:PrimalSpaceFeatures");


            bigPrimalSpaceFeatures.setAttribute("gml:id",cellSpaces.get(0).getParentFloor());
            primalSpaceFeatures.appendChild(bigPrimalSpaceFeatures);

            //putting cellspace coordinates into each cellspace

            for (int j=0;j<cellSpaces.size();j++) {

                CellSpace cp=cellSpaces.get(j);
                bigPrimalSpaceFeatures.appendChild(createCellSpaceMember(cp.getCellSpaceName(),
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
            System.out.println("DONE.");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public Node createCellSpaceMember(String cellSpaceName, ArrayList<CellSpaceCoordinate> cpFloor, ArrayList<CellSpaceCoordinate> cpCeiling) {

        Element cellSpaceMember = doc.createElementNS(CoreNS, "core:cellSpaceMember");
        Element cellSpace = doc.createElementNS(CoreNS, "core:CellSpace");
        cellSpaceMember.appendChild(cellSpace);

        Element metaDataProperty = doc.createElement("gml:metaDataProperty");
        cellSpace.appendChild(metaDataProperty);

        Element genericMetaData = doc.createElement("gml:GenericMetaData");
        genericMetaData.setTextContent(cellSpaceName);
        metaDataProperty.appendChild(genericMetaData);


        Element cellSpaceGeometry = doc.createElementNS(CoreNS, "core:cellSpaceGeometry");
        cellSpace.appendChild(cellSpaceGeometry);

        Element geometry3d = doc.createElementNS(CoreNS, "core:Geometry3D");
        cellSpaceGeometry.appendChild(geometry3d);

        Element solid = doc.createElement("gml:Solid");
        geometry3d.appendChild(solid);
        Element interior = doc.createElement("gml:interior");
        solid.appendChild(interior);


        Element shell = doc.createElement("gml:Shell");
        interior.appendChild(shell);

        Element surfaceMember = doc.createElement("gml:surfaceMember");
        shell.appendChild(surfaceMember);


        Element polyhedralSurface = doc.createElement("gml:PolyhedralSurface");
        surfaceMember.appendChild(polyhedralSurface);

        Element patches = doc.createElement("gml:patches");
        polyhedralSurface.appendChild(patches);


        Element polygonPatch = doc.createElement("gml:polygonPatch");
        patches.appendChild(polygonPatch);


        for (int i = 0; i < 2; i++) {

            if (cpFloor.size() == 4) {

                Element interior2 = (Element) interior.cloneNode(false);
                polygonPatch.appendChild(interior2);

                Element arc = doc.createElement("gml:Arc");
                interior2.appendChild(arc);

                insidePolygonPatch(cpFloor,cpCeiling,arc,i);
            }

            //LinearRing------------------------------------------------------------

            else {
                Element linearRing = doc.createElement("gml:LinearRing");
                Element interior2 = (Element) interior.cloneNode(false);
                polygonPatch.appendChild(interior2);
                interior2.appendChild(linearRing);

                insidePolygonPatch(cpFloor,cpCeiling,linearRing,i);
            }
        }
            return cellSpaceMember;
    }

        public void insidePolygonPatch(ArrayList<CellSpaceCoordinate> cpFloor, ArrayList<CellSpaceCoordinate> cpCeiling, Element e, int i){
            if (i == 0) {
                for (int j = 0; j < cpFloor.size(); j++) {
                    Element pos = doc.createElement("gml:pos");
                    e.appendChild(pos);
                    pos.setTextContent(cpFloor.get(j).toStringCoordinateXYZ());
                }
            } else {
                for (int j = 0; j < cpCeiling.size(); j++) {
                    Element pos = doc.createElement("gml:pos");
                    e.appendChild(pos);
                    pos.setTextContent(cpCeiling.get(j).toStringCoordinateXYZ());
                }
            }
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

