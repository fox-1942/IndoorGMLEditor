package hu.iit.uni.miskolc.gml.editor.service.impl;

import edu.pnu.importexport.ProjectMetaDataImporter;
import edu.pnu.project.BuildingProperty;
import edu.pnu.project.ProjectMetaData;
import edu.pnu.util.JAXBIndoorGMLConvertor;
import hu.iit.uni.miskolc.gml.editor.model.Import;
import net.opengis.indoorgml.core.IndoorFeatures;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;
import net.opengis.indoorgml.core.v_1_0.MultiLayeredGraphType;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

import static sun.management.Agent.error;

public class ImportImpl implements Import {

    private BuildingProperty buildingProperty;
    private IndoorFeaturesType indoorFeaturesType;
    private ProjectMetaData projectMetaData;


    public ImportImpl(){

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
    public void drawGmlFile(File inputFile){
        indoorFeaturesType=unmarshalmax(inputFile);
        indoorFeaturesType.setMultiLayeredGraph(new MultiLayeredGraphType());
        String path= inputFile.getPath().toString();
        System.out.println(path);

        ProjectMetaDataImporter projectMetaDataImporter = null;
        try {
            projectMetaDataImporter = new ProjectMetaDataImporter(path);
            //indoorFeaturesType.setMetaDataProperty(projectMetaDataImporter.getProjectMetaData());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JAXBIndoorGMLConvertor jaxbIndoorGMLConvertor=new JAXBIndoorGMLConvertor(indoorFeaturesType,buildingProperty);

        IndoorFeatures indoorFeatures = jaxbIndoorGMLConvertor.createIndoorFeatures(null,indoorFeaturesType);


    }
}