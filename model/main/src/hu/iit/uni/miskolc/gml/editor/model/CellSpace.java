package hu.iit.uni.miskolc.gml.editor.model;

import java.util.ArrayList;

public interface CellSpace {
    String getParentFloor();

    void setParentFloor(String parentFloor);

    ArrayList<CellSpaceCoordinate> getCellSpaceCeilingCoordinatesArrayList();

    void setCellSpaceCeilingCoordinatesArrayList(ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinatesArrayList);

    ArrayList<CellSpaceCoordinate> getCellSpaceFloorCoordinateArrayList();

    void setCellSpaceFloorCoordinateArrayList(ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArrayList);

    void cellSpacetoString();
}
