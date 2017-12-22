package hu.iit.uni.miskolc.gml.editor.model;

import java.io.File;
import java.util.ArrayList;

public interface Export {

     void export(File results, ArrayList<CellSpace> cellSpaces);
}
