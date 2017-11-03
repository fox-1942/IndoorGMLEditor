package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.Import;
import net.opengis.gml.v_3_1_1.FeaturePropertyType;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;
import net.opengis.indoorgml.core.v_1_0.PrimalSpaceFeaturesPropertyType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

            //System.out.println(unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue().toString());


            //System.out.println(unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue().getPrimalSpaceFeatures().toString());




            //List<CodeType> obj2 = unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue().getPrimalSpaceFeatures().getPrimalSpaceFeatures().getName();

           // List<FeaturePropertyType> obj = unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue().getPrimalSpaceFeatures().getPrimalSpaceFeatures().getCellSpaceMember();
            //for(FeaturePropertyType f : obj){



            IndoorFeaturesType indoorFeaturesType = unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue();


            PrimalSpaceFeaturesPropertyType primalSpaceFeatures = indoorFeaturesType.getPrimalSpaceFeatures();
            List<FeaturePropertyType> featurePropertyTypeList =new ArrayList<FeaturePropertyType>();
            featurePropertyTypeList=primalSpaceFeatures.getPrimalSpaceFeatures().getCellSpaceMember();
              System.out.println(featurePropertyTypeList.get(3).getTitle().toString());

            FeaturePropertyType[] featurePropertyTypeArray=new FeaturePropertyType[10];
            for(FeaturePropertyType f : featurePropertyTypeArray){
                f=primalSpaceFeatures.getPrimalSpaceFeatures().getCellSpaceMember();;
            }


            return indoorFeaturesType;


        } catch (JAXBException | IllegalArgumentException e) {
            error("Haver, az adatod nem valid!");
            e.printStackTrace();
            return null;
        }

    }
}
