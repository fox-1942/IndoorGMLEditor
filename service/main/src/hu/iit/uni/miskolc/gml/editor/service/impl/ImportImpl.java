package hu.iit.uni.miskolc.gml.editor.service.impl;


import edu.pnu.project.BuildingProperty;
import edu.pnu.project.ProjectMetaData;

import hu.iit.uni.miskolc.gml.editor.model.Import;

import net.opengis.indoorgml.core.PrimalSpaceFeatures;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



import static sun.management.Agent.error;

public class ImportImpl implements Import {

    private BuildingProperty buildingProperty;
    private IndoorFeaturesType indoorFeaturesType;
    private ProjectMetaData projectMetaData;


    public ImportImpl() {

    }

    @Override
    public IndoorFeaturesType unmarshalmax(File inputFile) {
        //Importing method
        try {

            JAXBContext jc = JAXBContext.newInstance(IndoorFeaturesType.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            System.out.println("The results of unmarshalling:");

            StreamSource streamSource = new StreamSource(inputFile);  // Converting inputFile to StreamSource type

            indoorFeaturesType = unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue();

            return indoorFeaturesType;


        } catch (JAXBException | IllegalArgumentException e) {
            error("The data is not valid!");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void drawGmlFile(File inputFile) throws ParserConfigurationException, IOException, SAXException {


        DocumentBuilderFactory factory 	= DocumentBuilderFactory.newInstance();
        DocumentBuilder builder 		= factory.newDocumentBuilder();

        Document document = builder.parse(inputFile);

        document.getDocumentElement().normalize();

        //factory.setNamespaceAware(true);

            System.out.println("Root element :" + document.getDocumentElement().getNodeName());


        NodeList nList = document.getElementsByTagName("core:PrimalSpaceFeatures");

        System.out.println(nList.item(0).getFirstChild().getNodeName());


        System.out.println("----------------------------");


        String FloorName;
int lowerCorner;

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                     FloorName= eElement.getAttribute("id");

                        eElement
                        .getElementsByTagName("")
                        .item(0)
                        .getTextContent();
                System.out.println("Last Name : "
                        + eElement
                        .getElementsByTagName("lastname")
                        .item(0)
                        .getTextContent());
                System.out.println("Nick Name : "
                        + eElement
                        .getElementsByTagName("nickname")
                        .item(0)
                        .getTextContent());
                System.out.println("Marks : "
                        + eElement
                        .getElementsByTagName("marks")
                        .item(0)
                        .getTextContent());
            }
        }
    }

}


