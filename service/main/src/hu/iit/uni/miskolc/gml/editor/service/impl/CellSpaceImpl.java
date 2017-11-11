package hu.iit.uni.miskolc.gml.editor.service.impl;

import hu.iit.uni.miskolc.gml.editor.model.CellSpaceCoordinate;

import java.util.ArrayList;

public class CellSpaceImpl implements hu.iit.uni.miskolc.gml.editor.model.CellSpace {

    private String ParentFloor;
    private String CellSpaceName;

    private ArrayList<CellSpaceCoordinateImpl> cellSpaceCeilingCoordinatesArrayList;
    private ArrayList<CellSpaceCoordinateImpl> cellSpaceFloorCoordinateArrayList;

    public CellSpaceImpl(String parentFloor, String cellSpaceName, ArrayList<CellSpaceCoordinateImpl> cellSpaceCeilingCoordinatesArrayList,
                         ArrayList<CellSpaceCoordinateImpl> cellSpaceFloorCoordinateArrayList) {
        ParentFloor = parentFloor;
        CellSpaceName = cellSpaceName;
        this.cellSpaceCeilingCoordinatesArrayList = cellSpaceCeilingCoordinatesArrayList;
        this.cellSpaceFloorCoordinateArrayList = cellSpaceFloorCoordinateArrayList;
    }

    @Override
    public String getParentFloor() {
        return ParentFloor;
    }

    @Override
    public void setParentFloor(String parentFloor) {

    }

    @Override
    public ArrayList<CellSpaceCoordinate> getCellSpaceCeilingCoordinatesArrayList() {
        return null;
    }

    @Override
    public void setCellSpaceCeilingCoordinatesArrayList(ArrayList<CellSpaceCoordinate> cellSpaceCeilingCoordinatesArrayList) {

    }

    @Override
    public ArrayList<CellSpaceCoordinate> getCellSpaceFloorCoordinateArrayList() {
        return null;
    }

    @Override
    public void setCellSpaceFloorCoordinateArrayList(ArrayList<CellSpaceCoordinate> cellSpaceFloorCoordinateArrayList) {

    }

}
