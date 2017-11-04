package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.Import;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

import static sun.management.Agent.error;

public class ImportImpl implements Import {

    public ImportImpl(){

    }

    @Override
    public IndoorFeaturesType unmarshalmax(File inputFile) {
        //Importing method
        try {

            JAXBContext jc = JAXBContext.newInstance(IndoorFeaturesType.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            System.out.println("Ez itt az unmarshall eredménye:");

            StreamSource streamSource = new StreamSource(inputFile);  // Converting inputFile to StreamSource type


            IndoorFeaturesType indoorFeaturesType = unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue();

            // List<FeaturePropertyType> featurePropertyTypeList =new ArrayList<FeaturePropertyType>();

            //featurePropertyTypeList=primalSpaceFeatures.getPrimalSpaceFeatures().getCellSpaceMember();



            return indoorFeaturesType;


        } catch (JAXBException | IllegalArgumentException e) {
            error("Haver, az adatod nem valid!");
            e.printStackTrace();
            return null;
        }

    }
}
