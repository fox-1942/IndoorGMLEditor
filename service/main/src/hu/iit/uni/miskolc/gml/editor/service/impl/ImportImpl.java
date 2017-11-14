package hu.iit.uni.miskolc.gml.editor.service.impl;


import hu.iit.uni.miskolc.gml.editor.model.Import;

import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
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

    private IndoorFeaturesType indoorFeaturesType;

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

    public Document domImport() throws ParserConfigurationException, IOException, SAXException {

        File inputFile = new File("resources/ISS1stFloor.xml");
        System.out.println("XML helye----->  " + inputFile.getAbsolutePath());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        /**
         * factory.setIgnoringElementContentWhitespace(true) needed to eliminate whitespaces and to use this method
         * schema validation is required, but if you want to validate with XSD (and not DTD) you have to set factory.setValidating
         * false.
         */
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File xsdFile=new File("resources/indoorgmlcore.xsd");
        System.out.println("XSD helye----->  " + xsdFile.getAbsolutePath());
        System.out.println("\nValidation started");
        factory.setSchema(schemaFactory.newSchema(xsdFile));
        factory.setValidating(false);
        factory.setNamespaceAware(true);
        factory.setIgnoringElementContentWhitespace(true);
        System.out.println("\nValidation done");

        DocumentBuilder builder	= factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);
        document.getDocumentElement().normalize();

        System.out.println("Root element------->  " + document.getDocumentElement().getNodeName());

        NodeList nl = document.getDocumentElement().getChildNodes();
        System.out.println("Children of the Root------> " + nl.getLength());
        if (nl.getLength() > 0)
            System.out.println("All of my data: " + nl.item(0).getTextContent());

        return document;
    }
}


