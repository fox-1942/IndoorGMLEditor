package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.ImportExport;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;


import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

import static sun.plugin.navig.motif.Plugin.error;

public class ImportExportImpl implements ImportExport {


    public ImportExportImpl(){

    }

    @Override
    public IndoorFeaturesType unmarshalmax(File inputFile) {
        try {

            JAXBContext jc = JAXBContext.newInstance(IndoorFeaturesType.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            System.out.println("Ez itt az unmarshall eredménye:");

            StreamSource streamSource = new StreamSource(inputFile);  // Converting inputFile to StreamSource type
            System.out.println(unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue().toString());
            return unmarshaller.unmarshal(streamSource, IndoorFeaturesType.class).getValue();


        } catch (JAXBException | IllegalArgumentException e) {
            error("Haver, az adatod nem valid!");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void marshalmax(File outputFile) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance("net.opengis.indoorgml.core.v_1_0");

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.opengis.net/indoorgml/1.0/core http://schemas.opengis.net/indoorgml/1.0/indoorgmlcore.xsd");
        try{
            marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new IndoorGMLNameSpaceMapperImpl());
        } catch(PropertyException e){
            e.printStackTrace();
        }

        JAXBElement<IndoorFeaturesType> je = jaxbConvertor.getJAXBElement();



    }
}
