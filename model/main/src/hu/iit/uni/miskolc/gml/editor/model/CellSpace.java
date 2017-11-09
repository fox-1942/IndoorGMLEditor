package hu.iit.uni.miskolc.gml.editor.model;

import java.util.ArrayList;

public interface CellSpace {
    String getParentFloor();

    void setParentFloor(String parentFloor);

    ArrayList<CellSpaceCeilingCoordinate> getCellSpaceCeilingCoordinatesArrayList();

    void setCellSpaceCeilingCoordinatesArrayList(ArrayList<CellSpaceCeilingCoordinate> cellSpaceCeilingCoordinatesArrayList);

    ArrayList<CellSpaceFloorCoordinate> getCellSpaceFloorCoordinateArrayList();

    void setCellSpaceFloorCoordinateArrayList(ArrayList<CellSpaceFloorCoordinate> cellSpaceFloorCoordinateArrayList);
}
