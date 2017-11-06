package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.Export;
import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import javax.xml.bind.*;
import java.io.File;


public class ExportImpl implements Export {


    private IndoorGMLJAXBConvertor jaxbConvertor;

    public ExportImpl(){

    }


    @Override
    public void marshalMax(File outputFile) throws JAXBException {
        //Exporting method

        JAXBContext jaxbContext = JAXBContext.newInstance("net.opengis.indoorgml.core.v_1_0"
                + ":net.opengis.gml.v_3_2_1");

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.opengis.net/indoorgml/1.0/core http://schemas.opengis.net/indoorgml/1.0/indoorgmlcore.xsd");
        try{
            marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new IndoorGMLNameSpaceMapperImpl());
        } catch(PropertyException e){
            e.printStackTrace();
        }

        JAXBElement<IndoorFeaturesType> je = jaxbConvertor.getJAXBElement();

        marshaller.marshal(je, outputFile); //JAXBElement to XML file

    }
}