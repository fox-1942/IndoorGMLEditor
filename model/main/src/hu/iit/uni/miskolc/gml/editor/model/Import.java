package hu.iit.uni.miskolc.gml.editor.model;

import net.opengis.indoorgml.core.v_1_0.IndoorFeaturesType;

import java.io.File;

public interface Import {

    IndoorFeaturesType unmarshalmax(File inputFile);

    void drawGmlFile(File inputFile);

}
