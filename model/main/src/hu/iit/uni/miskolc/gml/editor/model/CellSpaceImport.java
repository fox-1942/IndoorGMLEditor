package hu.iit.uni.miskolc.gml.editor.model;

import java.io.File;
import java.util.ArrayList;

public interface CellSpaceImport {

    ArrayList<CellSpace> cellSpaceCreator(File inputFile);

}
