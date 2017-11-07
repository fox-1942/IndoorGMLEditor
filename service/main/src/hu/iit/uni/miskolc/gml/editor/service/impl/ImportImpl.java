package hu.iit.uni.miskolc.gml.editor.service.impl;


import edu.pnu.project.BuildingProperty;
import edu.pnu.project.ProjectMetaData;

import hu.iit.uni.miskolc.gml.editor.model.Import;

import net.opengis.indoorgml.core.PrimalSpaceFeatures;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.validation.SchemaFactory;

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
    public void drawGmlFile() throws ParserConfigurationException, IOException, SAXException {

        File inputFile = new File("ISS1stFloor.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setValidating(false);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        File file=new File("indoorgmlcore.xsd");
        factory.setSchema(schemaFactory.newSchema(file));

        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);

        DocumentBuilder builder	= factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);
        document.getDocumentElement().normalize();

        System.out.println("Root element :" + document.getDocumentElement().getNodeName());

        NodeList nl = document.getDocumentElement().getChildNodes();
        System.out.println("Gyermekek száma:   " + nl.getLength());
        if (nl.getLength() > 0)
            System.out.println(nl.item(2).getTextContent());
    }

}


