package hu.iit.uni.miskolc.gml.editor.service.impl;


import com.sun.webkit.dom.NodeListImpl;
import edu.pnu.project.BuildingProperty;
import edu.pnu.project.ProjectMetaData;

import hu.iit.uni.miskolc.gml.editor.model.Import;

import net.opengis.indoorgml.core.PrimalSpaceFeatures;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;




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

        try{
        DocumentBuilderFactory factory 	= DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setExpandEntityReferences(true);
        factory.setIgnoringComments(true);
        factory.setXIncludeAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

      Document doc = builder.parse(inputFile);

        doc.getDocumentElement().normalize();


        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        Element el = doc.getDocumentElement();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList proba = doc.getChildNodes();
            NodeList nListPrimalSpaceFeatures = doc.getElementsByTagName("IndoorFeatures");
            NodeList nListChild = nListPrimalSpaceFeatures.item(0).getChildNodes();

            System.out.println("Alábbiakban a childNode-ok vannak.");
            for(int i=0; i < nListChild.getLength(); i++){
                System.out.println(nListChild.getLength());

                System.out.println("----------------------------");


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
